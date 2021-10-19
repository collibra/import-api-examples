package com.collibra.dataquality.csv;

import static com.collibra.dataquality.csv.CsvConstants.DEFAULT_IMPORT_API_ESCAPE_CHAR;
import static com.collibra.dataquality.csv.CsvConstants.DEFAULT_IMPORT_API_LINE_END;
import static com.collibra.dataquality.csv.CsvConstants.DEFAULT_IMPORT_API_QUOTE;
import static com.collibra.dataquality.csv.CsvConstants.DEFAULT_IMPORT_API_SEPARATOR;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriter;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CsvFileCreator {

	private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileCreator.class);

	public File create(String prefixFileName, List<String[]> rows) {
		File file;
		try {
			Path path = Files.createTempFile(prefixFileName, ".csv");
			file = path.toFile();
			CSVWriter writer = new CSVWriter(new FileWriter(path.toString()),
					DEFAULT_IMPORT_API_SEPARATOR,
					DEFAULT_IMPORT_API_QUOTE,
					DEFAULT_IMPORT_API_ESCAPE_CHAR,
					DEFAULT_IMPORT_API_LINE_END);
			writer.writeAll(rows);
			writer.close();
			LOGGER.debug("Csv file created with absolute path: {}", file.getAbsolutePath());
			file.deleteOnExit();
		} catch (IOException e) {
			throw new RuntimeException(
					String.format("Temporary file with %s prefix name does not created properly", prefixFileName));
		}
		return file;
	}


}
