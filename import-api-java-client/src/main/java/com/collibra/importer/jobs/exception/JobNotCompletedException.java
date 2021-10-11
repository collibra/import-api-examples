package com.collibra.importer.jobs.exception;

import com.collibra.core.rest.client.model.Job;

public class JobNotCompletedException extends RuntimeException {

	public JobNotCompletedException(Job job){
		super(String.format("Job with id %s is finished but it state is not completed but: %s", job.getId(), job.getState()));
	}
}
