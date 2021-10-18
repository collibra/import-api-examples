package com.collibra.dataquality.transform;

import com.collibra.dataquality.csv.CsvInputStreamIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ExternalSystemMetricsIterator implements Iterator<ExternalSystemMetric> {

	private static final int INDEX_OF_METRIC_ID = 1;
	private static final int INDEX_OF_PROJECT_NAME = 2;
	private static final int INDEX_OF_SCORECARD_NAME = 3;
	private static final int INDEX_OF_SCORE_NAME = 4;
	private static final int INDEX_OF_TOTAL_ROWS = 5;
	private static final int INDEX_OF_VALID_PERCENTAGE = 6;
	private static final int INDEX_OF_INVALID_ROWS = 7;
	private static final int INDEX_OF_THRESHOLD = 8;
	private static final int INDEX_OF_MAX_TIME_CREATED = 9;
	private static final int INDEX_OF_RELATED_COLUMN_NAME = 10;
	private static final int INDEX_OF_RELATED_COLUMN_DOMAIN_NAME = 11;
	private static final int INDEX_OF_RELATED_COLUMN_COMMUNITY_NAME = 12;

	private final CsvInputStreamIterator csvInputStreamIterator;

	ExternalSystemMetricsIterator(CsvInputStreamIterator csvInputStreamIterator) {
		this.csvInputStreamIterator = csvInputStreamIterator;
	}

	@Override
	public boolean hasNext() {
		return csvInputStreamIterator.hasNext();
	}

	@Override
	public ExternalSystemMetric next() {
		List<String> row = csvInputStreamIterator.next();
		return ExternalSystemMetric.builder()
				.metricId(getValueFromCsvRow(row, INDEX_OF_METRIC_ID))
				.projectName(getValueFromCsvRow(row, INDEX_OF_PROJECT_NAME))
				.scorecardName(getValueFromCsvRow(row, INDEX_OF_SCORECARD_NAME))
				.scoreName(getValueFromCsvRow(row, INDEX_OF_SCORE_NAME))
				.totalRows(Integer.parseInt(getValueFromCsvRow(row, INDEX_OF_TOTAL_ROWS)))
				.validPercentage(Double.parseDouble(getValueFromCsvRow(row, INDEX_OF_VALID_PERCENTAGE)))
				.invalidRows(Integer.parseInt(getValueFromCsvRow(row, INDEX_OF_INVALID_ROWS)))
				.threshold(Double.parseDouble(getValueFromCsvRow(row, INDEX_OF_THRESHOLD)))
				.maxTimeCreated(getValueFromCsvRow(row, INDEX_OF_MAX_TIME_CREATED))
				.columnName(getValueFromCsvRow(row, INDEX_OF_RELATED_COLUMN_NAME))
				.columnDomainName(getValueFromCsvRow(row, INDEX_OF_RELATED_COLUMN_DOMAIN_NAME))
				.columnCommunityName(getValueFromCsvRow(row, INDEX_OF_RELATED_COLUMN_COMMUNITY_NAME))
				.build();
	}

	public List<ExternalSystemMetric> toList() {
		final List<ExternalSystemMetric> list = new ArrayList<>();
		while (this.hasNext()) {
			list.add(this.next());
		}
		return list;
	}

	private String getValueFromCsvRow(List<String> row, int indexOfValue) {
		return row.get(indexOfValue - 1);
	}

}
