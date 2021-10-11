package com.collibra.importer.jobs.exception;

import java.util.UUID;

public class JobNotFoundException extends RuntimeException {

	public JobNotFoundException(UUID jobId) {
		super(String.format("job with id %s does not exist", jobId));
	}
}
