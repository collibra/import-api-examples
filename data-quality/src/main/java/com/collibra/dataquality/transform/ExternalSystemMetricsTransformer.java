package com.collibra.dataquality.transform;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.collibra.dataquality.csv.CsvFileCreator;
import com.collibra.dataquality.csv.CsvInputStreamIterator;

/**
 * Contains transformation mechanism from External System Metrics into Collibra Data Quality Metrics.
 */
public class ExternalSystemMetricsTransformer {

	private static final String COLLIBRA_DQ_METRIC_CSV_FILE_PREFIX = "collibra-metrics";

	private final CsvFileCreator csvFileCreator;

	public ExternalSystemMetricsTransformer(CsvFileCreator csvFileCreator) {
		this.csvFileCreator = csvFileCreator;
	}

	/**
	 * Transforms External System Metrics into Collibra Data Quality Metrics.
	 *
	 * @param inputStream of a csv file which contains external system metrics
	 * @return csv file which contains transformed metrics which are ready to send using import API
	 */
	public File transform(InputStream inputStream) {
		CsvInputStreamIterator csvInputStreamIterator = new CsvInputStreamIterator(
				inputStream);
		ExternalSystemMetricsIterator externalSystemMetricsIterator = new ExternalSystemMetricsIterator(
				csvInputStreamIterator);

		List<List<String>> headerRow = Collections.singletonList(ExternalSystemMetricMapper.getHeaderRow());
		List<List<String>> collibraMetricCsvRows = externalSystemMetricsIterator.toList().stream()
				.map(ExternalSystemMetricMapper.collibraMetricMapper())
				.collect(Collectors.toList());
		List<String[]> collibraMetricCsvRowsWithHeader = Stream.of(headerRow, collibraMetricCsvRows)
				.flatMap(Collection::stream)
				.map(strings -> strings.toArray(new String[0]))
				.collect(Collectors.toList());

		return csvFileCreator.create(COLLIBRA_DQ_METRIC_CSV_FILE_PREFIX, collibraMetricCsvRowsWithHeader);
	}
}
