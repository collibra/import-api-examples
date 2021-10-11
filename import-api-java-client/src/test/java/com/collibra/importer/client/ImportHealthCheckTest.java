package com.collibra.importer.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class ImportHealthCheckTest {

	private static ImporterApi importApi;

	@BeforeAll
	static void setupClient() {
		ApiClientProvider apiClientProvider = new ApiClientProvider();
		CollibraApi collibraApi = new CollibraApi(apiClientProvider);
		importApi = collibraApi.importerApi();
	}

	@Test
	void checkSynchronizationDoesNotExist() {
		// given
		String syncId = UUID.randomUUID().toString();

		// when
		Boolean exists = importApi.exists(syncId);

		// then
		assertThat(exists)
				.as("check synchronization ID does not exist")
				.isFalse();
	}
}
