package com.collibra.dataquality.transform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.collibra.dataquality.csv.CsvFileCreator;
import com.collibra.dataquality.fixtures.TestInputStreamProvider;

@ExtendWith(MockitoExtension.class)
class ExternalSystemMetricsTransformerTest {

	@Mock
	private CsvFileCreator csvFileCreator;

	@InjectMocks
	private ExternalSystemMetricsTransformer externalSystemMetricsTransformer;

	@Captor
	private ArgumentCaptor<List<String[]>> collibraMetricRowsCaptor;

	@Test
	void handlesTransform() {
		// given
		InputStream resourceAsStream = TestInputStreamProvider.getInputStreamFromTestScores();

		File expectedTransformedCsvFile = new File(
				getClass().getResource("test-transformed-to-collibra-dq-metrics.csv").getFile());
		when(csvFileCreator.create(anyString(), any())).thenReturn(expectedTransformedCsvFile);

		// when
		File transformedFile = externalSystemMetricsTransformer.transform(resourceAsStream);

		// then
		assertThat(transformedFile).isEqualTo(expectedTransformedCsvFile);
		verify(csvFileCreator).create(any(), collibraMetricRowsCaptor.capture());
		List<String[]> capturedParams = collibraMetricRowsCaptor.getValue();
		assertThat(capturedParams).hasSize(2);
		assertThat(capturedParams.get(0))
				.containsExactly(ExternalSystemMetricMapper.getHeaderRow().toArray(new String[0]));
		assertThat(capturedParams.get(1)).hasSize(11)
				.containsExactly(
						"Test1 Metric Id",
						"Test1 Project Name / Test1 Score Name / Test1 Scorecard Name",
						"50.0",
						"40.0",
						"5",
						"5",
						"true",
						"10",
						"TAB_1_COLUMN_1",
						"Physical Domain",
						"DBs Community");
	}
}
