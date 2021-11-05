package com.collibra.importer.properties;

import static lombok.AccessLevel.PRIVATE;

import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class ConfigProperties {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config");

	private static final String COLLIBRA_URL_PROPERTY = "DGC_URL";
	private static final String COLLIBRA_USERNAME_PROPERTY = "DGC_USERNAME";
	private static final String COLLIBRA_PASSWORD_PROPERTY = "DGC_PASSWORD";

	private static final String COLLIBRA_EXTERNAL_SYSTEM_METRIC_FILE = "collibra.external-system-metric-file";
	private static final String COLLIBRA_DQ_METRICS_COMMUNITY_NAME = "collibra.dq-metrics-community-name";
	private static final String COLLIBRA_DQ_METRICS_DOMAIN_NAME = "collibra.dq-metrics-domain-name";
	private static final String COLLIBRA_EXTERNAL_SYSTEM_ID = "collibra.external-system-id";

	static {
		getCollibraUrl();
		getCollibraUsername();
		getCollibraPassword();
		checkIfPropertyIsBlank(COLLIBRA_EXTERNAL_SYSTEM_METRIC_FILE);
		checkIfPropertyIsBlank(COLLIBRA_DQ_METRICS_COMMUNITY_NAME);
		checkIfPropertyIsBlank(COLLIBRA_DQ_METRICS_DOMAIN_NAME);
		checkIfPropertyIsBlank(COLLIBRA_EXTERNAL_SYSTEM_ID);
	}

	public static String getCollibraUrl() {
		return getSystemProperty(COLLIBRA_URL_PROPERTY);
	}

	public static String getCollibraUsername() {
		return getSystemProperty(COLLIBRA_USERNAME_PROPERTY);
	}

	public static String getCollibraPassword() {
		return getSystemProperty(COLLIBRA_PASSWORD_PROPERTY);
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

	private static String getSystemProperty(String property) {
		String value = System.getProperty(property);
		if (StringUtils.isBlank(value)) {
			value = System.getenv(property);
		}
		if (StringUtils.isBlank(value)) {
			throw new BlankPropertyException(property);
		}
		return value;
	}

}
