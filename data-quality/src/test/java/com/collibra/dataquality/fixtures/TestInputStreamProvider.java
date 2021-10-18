package com.collibra.dataquality.fixtures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

public class TestInputStreamProvider {

	private static final String TEST_SCORES_FILE_NAME = "test-scores.csv";

	public static InputStream getInputStreamFromTestScores() {
		File file = getTestScoresCsvFile();
		return getInputStreamFrom(file);

	}

	private static File getTestScoresCsvFile() {
		return FileUtils.getFile("src", "test", "resources", "examples", "csv", TEST_SCORES_FILE_NAME);
	}

	private static InputStream getInputStreamFrom(File file) {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(String.format("File %s cannot be found", file));
		}
		return inputStream;
	}
}
