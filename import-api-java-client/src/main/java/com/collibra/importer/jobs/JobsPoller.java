package com.collibra.importer.jobs;

import static com.collibra.core.rest.client.model.Job.StateEnum.CANCELED;
import static com.collibra.core.rest.client.model.Job.StateEnum.COMPLETED;
import static com.collibra.core.rest.client.model.Job.StateEnum.ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.collibra.core.rest.client.api.JobsApi;
import com.collibra.core.rest.client.model.Job;
import com.collibra.importer.jobs.exception.JobNotCompletedException;
import com.collibra.importer.jobs.exception.JobNotFinishedException;
import com.collibra.importer.jobs.exception.JobNotFoundException;

import feign.FeignException;

/**
 * Contains polling mechanism for jobs.
 */
public class JobsPoller {

	private static final int POLLING_VALUE_FOR_FIRST_GET_JOB_SECONDS = 10;
	private static final int WAIT_TIMEOUT_60_SECONDS = 60;
	private static final int POLLING_INTERVAL_SECONDS = 2;

	private static final Logger LOGGER = LoggerFactory.getLogger(JobsPoller.class);

	private final JobsApi jobsApi;

	public JobsPoller(JobsApi jobsApi) {
		this.jobsApi = jobsApi;
	}


	/**
	 * Waits until job is completed in a {@link #WAIT_TIMEOUT_60_SECONDS} timeout.
	 * Polling interval is set to {@link #POLLING_INTERVAL_SECONDS}.
	 *
	 * @param jobId The job id for which polling mechanism will be executed.
	 * @return the Job with completed state.
	 * @throws com.collibra.importer.jobs.exception.exception.JobNotFoundException     if a job was not found using import API
	 * @throws com.collibra.importer.jobs.exception.exception.JobNotFinishedException  if a job was not finished in a given timeout
	 * @throws com.collibra.importer.jobs.exception.exception.JobNotCompletedException if a job was not completed in a given timeout
	 */
	public Job waitUntilJobIsCompleted(UUID jobId) {
		Instant start = Instant.now();
		Job job = getJobWithNotFoundExceptionHandled(jobId);
		while (!isJobFinished(job) && !isTimeoutExceeded(start)) {
			LOGGER.debug("Job with id {} is not finished. Delay for {}s and call getJob again.",
					jobId, POLLING_INTERVAL_SECONDS);
			delay(POLLING_INTERVAL_SECONDS);
			job = jobsApi.getJob(jobId);
		}
		if (!isJobFinished(job)) {
			throw new JobNotFinishedException(job);
		}
		if (!isJobCompleted(job)) {
			throw new JobNotCompletedException(job);
		}
		return job;
	}

	private boolean isTimeoutExceeded(Instant startTime) {
		return Duration.between(startTime, Instant.now()).getSeconds() > WAIT_TIMEOUT_60_SECONDS;
	}

	private boolean isJobFinished(Job job) {
		Job.StateEnum state = job.getState();
		return state == COMPLETED || state == ERROR || state == CANCELED;
	}

	private boolean isJobCompleted(Job job) {
		return job.getState() == COMPLETED;
	}

	private void delay(long seconds) {
		try {
			Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private Job getJobWithNotFoundExceptionHandled(UUID jobId) {
		Optional<Job> job = Optional.empty();
		Instant start = Instant.now();
		while (!job.isPresent() && (Duration.between(start, Instant.now()).getSeconds() <
				POLLING_VALUE_FOR_FIRST_GET_JOB_SECONDS)) {
			try {
				job = Optional.of(jobsApi.getJob(jobId));
			} catch (FeignException e) {
				//TODO bug for Jobs API - returns 500 instead of 404 when job does not exists
				if (HTTP_NOT_FOUND == e.status() || 500 == e.status()) {
					LOGGER.debug("Job with id {} not found. Delay for {}s and call getJob again.",
							jobId, POLLING_INTERVAL_SECONDS);
					delay(POLLING_INTERVAL_SECONDS);
				} else {
					throw e;
				}
			}
		}
		return job.orElseThrow(() -> new JobNotFoundException(jobId));
	}

}
