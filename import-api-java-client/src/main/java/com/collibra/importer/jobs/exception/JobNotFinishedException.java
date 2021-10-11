package com.collibra.importer.jobs.exception;

import com.collibra.core.rest.client.model.Job;

public class JobNotFinishedException extends RuntimeException {

	public JobNotFinishedException(Job job){
		super(String.format("Job with id %s does not finished after timeout, has state %s", job.getId(), job.getState()));
	}
}
