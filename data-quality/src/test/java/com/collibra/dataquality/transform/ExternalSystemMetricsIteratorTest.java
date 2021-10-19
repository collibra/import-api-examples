package com.collibra.dataquality.transform;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.collibra.dataquality.csv.CsvInputStreamIterator;
import com.collibra.dataquality.fixtures.TestInputStreamProvider;

class ExternalSystemMetricsIteratorTest {

	@Test
	void convertsFirstRow() {
		//given
		InputStream resourceAsStream = TestInputStreamProvider.getInputStreamFromTestScores();
		CsvInputStreamIterator csvInputStreamIterator = new CsvInputStreamIterator(resourceAsStream);
		ExternalSystemMetricsIterator externalSystemMetricsIterator = new ExternalSystemMetricsIterator(
				csvInputStreamIterator);

		ExternalSystemMetric expectedExternalSystemMetric = ExternalSystemMetric.builder()
				.metricId("Test1 Metric Id")
				.projectName("Test1 Project Name")
				.scorecardName("Test1 Scorecard Name")
				.scoreName("Test1 Score Name")
				.totalRows(10)
				.validPercentage(50.0)
				.invalidRows(5)
				.threshold(40.0)
				.maxTimeCreated("Test Max Time Created")
				.columnName("TAB_1_COLUMN_1")
				.columnDomainName("Physical Domain")
				.columnCommunityName("DBs Community")
				.build();
		//when
		List<ExternalSystemMetric> externalSystemMetrics = externalSystemMetricsIterator.toList();

		//then
		assertThat(externalSystemMetrics).hasSize(1);

		assertThat(externalSystemMetrics.get(0))
				.isEqualTo(expectedExternalSystemMetric);

	}
}
