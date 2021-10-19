package com.collibra.dataquality.csv;

import static com.collibra.dataquality.csv.CsvConstants.DEFAULT_IMPORT_API_ESCAPE_CHAR;
import static com.collibra.dataquality.csv.CsvConstants.DEFAULT_IMPORT_API_IGNORE_LEADING_WHITESPACE;
import static com.collibra.dataquality.csv.CsvConstants.DEFAULT_IMPORT_API_QUOTE;
import static com.collibra.dataquality.csv.CsvConstants.DEFAULT_IMPORT_API_SEPARATOR;
import static com.collibra.dataquality.csv.CsvConstants.DEFAULT_IMPORT_API_STRICT_QUOTES;
import static lombok.AccessLevel.PRIVATE;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
final class CsvReaderFactory {


	static CSVReader buildCsvReader(InputStream fileStream) {
		CSVParser parser = new CSVParserBuilder()
				.withSeparator(DEFAULT_IMPORT_API_SEPARATOR)
				.withQuoteChar(DEFAULT_IMPORT_API_QUOTE)
				.withEscapeChar(DEFAULT_IMPORT_API_ESCAPE_CHAR)
				.withStrictQuotes(DEFAULT_IMPORT_API_STRICT_QUOTES)
				.withIgnoreLeadingWhiteSpace(DEFAULT_IMPORT_API_IGNORE_LEADING_WHITESPACE)
				.build();

		final InputStreamReader inputStreamReader = new InputStreamReader(fileStream);
		int linesToSkip = 1; // skipping header row
		return new CSVReaderBuilder(inputStreamReader)
				.withCSVParser(parser)
				.withSkipLines(linesToSkip)
				.withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
				.build();
	}
}
