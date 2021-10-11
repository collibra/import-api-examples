package com.collibra.importer.client;

import com.collibra.core.rest.client.api.AssetsApi;
import com.collibra.core.rest.client.api.JobsApi;
import com.collibra.importer.rest.client.api.ImportApi;

/**
 * Contains Collibra Apis needed to execute import request.
 */
public final class CollibraApi {

	private final com.collibra.importer.rest.client.invoker.ApiClient importerApiClient;
	private final com.collibra.core.rest.client.invoker.ApiClient coreApiClient;

	public CollibraApi(ApiClientProvider apiClientProvider) {
		this.importerApiClient = apiClientProvider.createImporterApiClient();
		this.coreApiClient = apiClientProvider.createCoreApiClient();
	}

	/**
	 * Gets importer API client with specified Authentication and URL to specific Collibra instance.
	 *
	 * @return importer API
	 */
	public ImporterApi importerApi() {
		return new ImporterApi(importerApiClient.buildClient(ImportApi.class));
	}

	/**
	 * Gets Jobs API client with specified Authentication and URL to specific Collibra instance.
	 *
	 * @return Jobs API
	 */
	public JobsApi jobsApi() {
		return coreApiClient.buildClient(JobsApi.class);
	}

	/**
	 * Gets Assets API Client with specified Authentication and URL to specific Collibra instance.
	 * @return Assets API
	 */
	public AssetsApi assetsApi() {
		return coreApiClient.buildClient(AssetsApi.class);
	}

}
