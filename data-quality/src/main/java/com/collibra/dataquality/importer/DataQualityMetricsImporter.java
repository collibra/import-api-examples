package com.collibra.dataquality.importer;

import java.io.File;

import com.collibra.importer.client.ImporterApi;
import com.collibra.importer.rest.client.model.ImportCsvInJobRequest;
import com.collibra.importer.rest.client.model.Job;


/**
 * Contains import operation for data quality metrics with csv file using Import API.
 */
public class DataQualityMetricsImporter {

	private final ImporterApi importerApi;

	public DataQualityMetricsImporter(ImporterApi importerApi) {
		this.importerApi = importerApi;
	}

	/**
	 * Starts import csv job with Data Quality Metrics.
	 *
	 * @param collibraMetricsCsvFile The transformed csv file to upload
	 * @param template               the template that should be used for parsing and importing the contents of the csv file
	 * @return the import job
	 */
	public Job importDataQualityMetrics(File collibraMetricsCsvFile, String template) {
		ImportCsvInJobRequest importCsvInJobRequest = new ImportCsvInJobRequest();
		importCsvInJobRequest.setFile(collibraMetricsCsvFile);
		importCsvInJobRequest.setTemplate(template);
		importCsvInJobRequest.setHeaderRow(Boolean.TRUE);
		return importerApi.importCsvInJob(importCsvInJobRequest);
	}
}
