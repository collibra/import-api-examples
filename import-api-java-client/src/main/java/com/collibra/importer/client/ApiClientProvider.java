package com.collibra.importer.client;


import static com.collibra.importer.properties.ConfigProperties.getCollibraPassword;
import static com.collibra.importer.properties.ConfigProperties.getCollibraUrl;
import static com.collibra.importer.properties.ConfigProperties.getCollibraUsername;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides API clients needed to execute import request
 */
public final class ApiClientProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiClientProvider.class);
	private static final String REST_API_PATH = "/rest/2.0";
	private static final String AUTH_METHOD = "basicAuth";

	private final String collibraUrl;
	private final String username;
	private final String password;

	public ApiClientProvider() {
		this(getCollibraUrl(), getCollibraUsername(), getCollibraPassword());
	}

	private ApiClientProvider(String collibraUrl, String username, String password) {
		validateCredentials(collibraUrl, username, password);
		this.collibraUrl = collibraUrl;
		this.username = username;
		this.password = password;
	}

	private void validateCredentials(String collibraUrl, String username, String password) {
		if (StringUtils.isBlank(collibraUrl)) {
			throw new IllegalArgumentException("Expected Collibra instance URL to be not blank.");
		}

		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("Expected Collibra user name to be not blank.");
		}

		if (StringUtils.isBlank(password)) {
			throw new IllegalArgumentException("Expected Collibra password to be not blank.");
		}
	}

	com.collibra.importer.rest.client.invoker.ApiClient createImporterApiClient() {
		com.collibra.importer.rest.client.invoker.ApiClient apiClient =
				new com.collibra.importer.rest.client.invoker.ApiClient(AUTH_METHOD, username, password);
		apiClient.setBasePath(collibraUrl + REST_API_PATH);
		logImportApiClientConfig(apiClient);
		return apiClient;
	}

	com.collibra.core.rest.client.invoker.ApiClient createCoreApiClient() {
		com.collibra.core.rest.client.invoker.ApiClient apiClient =
				new com.collibra.core.rest.client.invoker.ApiClient(AUTH_METHOD, username, password);
		apiClient.setBasePath(collibraUrl + REST_API_PATH);
		return apiClient;
	}

	private void logImportApiClientConfig(com.collibra.importer.rest.client.invoker.ApiClient apiClient) {
		LOGGER.info("Creating Import API client...");
		LOGGER.info("Collibra API path: {}.", apiClient.getBasePath());
		LOGGER.info("User name: {}.", username);
		LOGGER.info("Password: {}.", password);
	}

}