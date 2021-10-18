package com.collibra.dataquality.transform;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

class ExternalSystemMetricMapperTest {

	@Test
	void checkTransformationValues() {
		// given
		int totalRows = 10;
		double validPercentage = 50.0;
		int invalidRows = 5;
		double threshold = 40.0;
		ExternalSystemMetric externalSystemMetric = ExternalSystemMetric.builder()
				.metricId("Test1 Metric Id")
				.projectName("Test1 Project Name")
				.scorecardName("Test1 Scorecard Name")
				.scoreName("Test1 Score Name")
				.totalRows(totalRows)
				.validPercentage(validPercentage)
				.invalidRows(invalidRows)
				.threshold(threshold)
				.maxTimeCreated("Test Max Time Created")
				.columnName("Test1 Column")
				.columnDomainName("Test1 Domain")
				.columnCommunityName("Test1 Community")
				.build();
		String expectedFullName = String
				.join(" / ", externalSystemMetric.getProjectName(), externalSystemMetric.getScoreName(),
						externalSystemMetric.getScorecardName());
		String expectedTotalRows = String.valueOf(totalRows);
		String expectedInvalidRows = String.valueOf(invalidRows);
		String expectedPassedRows = String.valueOf(totalRows - invalidRows); // 10 - 5
		String expectedPassingFraction = String.valueOf(validPercentage);
		String expectedThreshold = String.valueOf(threshold);
		String expectedResult = String.valueOf(validPercentage >= threshold); // 50.0 >= 40.0? -> true

		Function<ExternalSystemMetric,List<String>> externalSystemMetricListFunction = ExternalSystemMetricMapper
				.collibraMetricMapper();
		// when
		List<String> expectedTransformationValues = externalSystemMetricListFunction.apply(externalSystemMetric);

		// then
		assertThat(expectedTransformationValues).hasSize(11)
				.containsExactly(
						externalSystemMetric.getMetricId(),
						expectedFullName,
						expectedPassingFraction,
						expectedThreshold,
						expectedPassedRows,
						expectedInvalidRows,
						expectedResult,
						expectedTotalRows,
						externalSystemMetric.getColumnName(),
						externalSystemMetric.getColumnDomainName(),
						externalSystemMetric.getColumnCommunityName()
				);
	}

	@Test
	void checkTransformationValues_whenResultIsFalse() {
		int totalRows = 20;
		double validPercentage = 20.0;
		int invalidRows = 5;
		double threshold = 40.0;
		ExternalSystemMetric externalSystemMetric = ExternalSystemMetric.builder()
				.metricId("Test2 Metric Id")
				.projectName("Test2 Project Name")
				.scorecardName("Test2 Scorecard Name")
				.scoreName("Test2 Score Name")
				.totalRows(totalRows)
				.validPercentage(validPercentage)
				.invalidRows(invalidRows)
				.threshold(threshold)
				.maxTimeCreated("Test Max Time Created")
				.columnName("Test1 Column")
				.columnDomainName("Test1 Domain")
				.columnCommunityName("Test1 Community")
				.build();
		String expectedFullName = String
				.join(" / ", externalSystemMetric.getProjectName(), externalSystemMetric.getScoreName(),
						externalSystemMetric.getScorecardName());
		String expectedTotalRows = String.valueOf(totalRows);
		String expectedInvalidRows = String.valueOf(invalidRows);
		String expectedPassedRows = String.valueOf(totalRows - invalidRows); // 20 - 5 = 15
		String expectedPassingFraction = String.valueOf(validPercentage);
		String expectedThreshold = String.valueOf(threshold);
		String expectedResult = String.valueOf(validPercentage >= threshold); // 20.0 >= 40.0? -> false


		Function<ExternalSystemMetric,List<String>> externalSystemMetricListFunction = ExternalSystemMetricMapper
				.collibraMetricMapper();
		// when
		List<String> expectedTransformationValues = externalSystemMetricListFunction.apply(externalSystemMetric);

		// then
		assertThat(expectedTransformationValues).hasSize(11)
				.containsExactly(
						externalSystemMetric.getMetricId(),
						expectedFullName,
						expectedPassingFraction,
						expectedThreshold,
						expectedPassedRows,
						expectedInvalidRows,
						expectedResult,
						expectedTotalRows,
						externalSystemMetric.getColumnName(),
						externalSystemMetric.getColumnDomainName(),
						externalSystemMetric.getColumnCommunityName()
				);
	}

}
