package com.collibra.dataquality.properties;

import static lombok.AccessLevel.PRIVATE;

import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.NoArgsConstructor;
import com.collibra.importer.properties.ConfigProperties;


@NoArgsConstructor(access = PRIVATE)
public class ExternalSystemMetricsInputStreamRetriever {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalSystemMetricsInputStreamRetriever.class);

	public static InputStream getExternalSystemMetricsFileAsInputStream() {
		String externalSystemMetricFile = ConfigProperties.getCollibraExternalSystemMetricsFile();
		InputStream inputStream = ExternalSystemMetricsInputStreamRetriever.class
				.getResourceAsStream(externalSystemMetricFile);
		LOGGER.info("File {} will be used as an input to transformation", externalSystemMetricFile);
		return inputStream;
	}
}
