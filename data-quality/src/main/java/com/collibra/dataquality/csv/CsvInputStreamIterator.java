package com.collibra.dataquality.csv;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.iterators.FilterIterator;
import org.apache.commons.lang3.StringUtils;

public final class CsvInputStreamIterator implements Iterator<List<String>> {

	private final Iterator<String[]> rowIterator;

	public CsvInputStreamIterator(InputStream fileStream) {
		Iterator<String[]> csvIterator = CsvReaderFactory.buildCsvReader(fileStream).iterator();
		this.rowIterator = new FilterIterator<>(csvIterator,
				row -> Arrays.stream(row).anyMatch(StringUtils::isNotBlank));
	}

	@Override
	public boolean hasNext() {
		return rowIterator.hasNext();
	}

	@Override
	public List<String> next() {
		return Arrays.asList(rowIterator.next());
	}
}
