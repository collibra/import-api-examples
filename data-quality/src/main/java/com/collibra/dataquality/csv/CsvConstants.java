package com.collibra.dataquality.csv;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class CsvConstants {

	static final char DEFAULT_IMPORT_API_SEPARATOR = ';';
	static final char DEFAULT_IMPORT_API_QUOTE = '"';
	static final char DEFAULT_IMPORT_API_ESCAPE_CHAR = '\\';
	static final boolean DEFAULT_IMPORT_API_STRICT_QUOTES = false;
	static final boolean DEFAULT_IMPORT_API_IGNORE_LEADING_WHITESPACE = false;
	static final String DEFAULT_IMPORT_API_LINE_END = "\n";
}
