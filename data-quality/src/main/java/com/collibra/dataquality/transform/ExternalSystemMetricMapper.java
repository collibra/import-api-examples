package com.collibra.dataquality.transform;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.NoArgsConstructor;

/**
 * Contains mapping operation from single external system metric into single collibra data quality rule metric
 */
@NoArgsConstructor(access = PRIVATE)
class ExternalSystemMetricMapper {

	private static final String EXTERNAL_METRIC_ID = "External Metric Id";
	private static final String FULL_NAME = "Full Name";
	private static final String PASSING_FRACTION = "Passing Fraction";
	private static final String THRESHOLD = "Threshold";
	private static final String ROWS_PASSED = "Rows Passed";
	private static final String ROWS_FAILED = "Rows Failed";
	private static final String RESULT = "Result";
	private static final String LOADED_ROWS = "Loaded Rows";
	private static final String RELATED_COLUMN_ASSET_NAME = "Column Name";
	private static final String RELATED_COLUMN_DOMAIN_NAME = "Column Domain Name";
	private static final String RELATED_COLUMN_COMMUNITY_NAME = "Column Community Name";

	static List<String> getHeaderRow() {
		return Stream.of(
				EXTERNAL_METRIC_ID,
				FULL_NAME,
				PASSING_FRACTION,
				THRESHOLD,
				ROWS_PASSED,
				ROWS_FAILED,
				RESULT,
				LOADED_ROWS,
				RELATED_COLUMN_ASSET_NAME,
				RELATED_COLUMN_DOMAIN_NAME,
				RELATED_COLUMN_COMMUNITY_NAME
		).collect(Collectors.toList());
	}

	static Function<ExternalSystemMetric,List<String>> collibraMetricMapper() {
		return externalSystemMetric -> Stream.of(
				externalSystemMetric.getMetricId(),
				getFullName(externalSystemMetric),
				String.valueOf(externalSystemMetric.getValidPercentage()),
				String.valueOf(externalSystemMetric.getThreshold()),
				getPassedRows(externalSystemMetric),
				String.valueOf(externalSystemMetric.getInvalidRows()),
				getResult(externalSystemMetric),
				String.valueOf(externalSystemMetric.getTotalRows()),
				externalSystemMetric.getColumnName(),
				externalSystemMetric.getColumnDomainName(),
				externalSystemMetric.getColumnCommunityName()
		).collect(Collectors.toList());
	}

	private static String getFullName(ExternalSystemMetric externalSystemMetric) {
		String projectName = externalSystemMetric.getProjectName();
		String scorecardName = externalSystemMetric.getScorecardName();
		String scoreName = externalSystemMetric.getScoreName();
		return String.join(" / ", projectName, scoreName, scorecardName);
	}

	private static String getResult(ExternalSystemMetric externalSystemMetric) {
		return String.valueOf(externalSystemMetric.getValidPercentage() >= externalSystemMetric.getThreshold());
	}

	private static String getPassedRows(ExternalSystemMetric externalSystemMetric) {
		return String.valueOf(externalSystemMetric.getTotalRows() - externalSystemMetric.getInvalidRows());
	}

}
