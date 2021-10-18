package com.collibra.dataquality.template;

enum DataQualityMetricTemplatePlaceholder {

	EXTERNAL_ENTITY_ID,
	DATA_QUALITY_METRIC_ASSET_NAME,
	PASSING_FRACTION_ATTRIBUTE,
	THRESHOLD_ATTRIBUTE,
	ROWS_PASSED_ATTRIBUTE,
	ROWS_FAILED_ATTRIBUTE,
	RESULT_ATTRIBUTE,
	LOADED_ROWS_ATTRIBUTE,
	RELATED_COLUMN_FULL_NAME,
	RELATED_COLUMN_DOMAIN,
	RELATED_COLUMN_COMMUNITY;

	private final String placeholder;

	DataQualityMetricTemplatePlaceholder() {
		int placeholderIndex = ordinal() + 1;
		placeholder = "${" + placeholderIndex + "}";
	}

	public String getPlaceholder() {
		return placeholder;
	}
}