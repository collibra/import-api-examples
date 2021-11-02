package com.collibra.importer.properties;

import static lombok.AccessLevel.PRIVATE;

import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class ConfigProperties {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config");

	private static final String COLLIBRA_URL_PROPERTY = "collibra.url";
	private static final String COLLIBRA_USERNAME_PROPERTY = "collibra.username";
	private static final String COLLIBRA_PASSWORD_PROPERTY = "collibra.password";

	private static final String COLLIBRA_EXTERNAL_SYSTEM_METRIC_FILE = "collibra.external-system-metric-file";
	private static final String COLLIBRA_DQ_METRICS_COMMUNITY_NAME = "collibra.dq-metrics-community-name";
	private static final String COLLIBRA_DQ_METRICS_DOMAIN_NAME = "collibra.dq-metrics-domain-name";
	private static final String COLLIBRA_EXTERNAL_SYSTEM_ID = "collibra.external-system-id";

	static {
		checkIfPropertyIsBlank(COLLIBRA_URL_PROPERTY);
		checkIfPropertyIsBlank(COLLIBRA_USERNAME_PROPERTY);
		checkIfPropertyIsBlank(COLLIBRA_PASSWORD_PROPERTY);
		checkIfPropertyIsBlank(COLLIBRA_EXTERNAL_SYSTEM_METRIC_FILE);
		checkIfPropertyIsBlank(COLLIBRA_DQ_METRICS_COMMUNITY_NAME);
		checkIfPropertyIsBlank(COLLIBRA_DQ_METRICS_DOMAIN_NAME);
		checkIfPropertyIsBlank(COLLIBRA_EXTERNAL_SYSTEM_ID);
	}

	public static String getCollibraUrl() {
		return RESOURCE_BUNDLE.getString(COLLIBRA_URL_PROPERTY);
	}

	public static String getCollibraUsername() {
		return RESOURCE_BUNDLE.getString(COLLIBRA_USERNAME_PROPERTY);
	}

	public static String getCollibraPassword() {
		return RESOURCE_BUNDLE.getString(COLLIBRA_PASSWORD_PROPERTY);
	}

	public static String getCollibraExternalSystemMetricsFile() {
		return RESOURCE_BUNDLE.getString(COLLIBRA_EXTERNAL_SYSTEM_METRIC_FILE);
	}

	public static String getDataQualityMetricsCommunityName() {
		return RESOURCE_BUNDLE.getString(COLLIBRA_DQ_METRICS_COMMUNITY_NAME);
	}

	public static String getDataQualityMetricsDomainName() {
		return RESOURCE_BUNDLE.getString(COLLIBRA_DQ_METRICS_DOMAIN_NAME);
	}

	public static String getExternalSystemId() {
		return RESOURCE_BUNDLE.getString(COLLIBRA_EXTERNAL_SYSTEM_ID);
	}

	private static void checkIfPropertyIsBlank(String propertyKey) {
		if (StringUtils.isBlank(RESOURCE_BUNDLE.getString(propertyKey))) {
			throw new BlankPropertyException(propertyKey);
		}
	}

}