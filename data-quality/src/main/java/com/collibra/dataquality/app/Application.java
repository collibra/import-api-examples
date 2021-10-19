package com.collibra.dataquality.app;

import java.io.File;
import java.io.InputStream;

import com.collibra.importer.client.ApiClientProvider;
import com.collibra.importer.client.CollibraApi;
import com.collibra.dataquality.csv.CsvFileCreator;
import com.collibra.dataquality.importer.DataQualityMetricsImporter;
import com.collibra.importer.jobs.JobsPoller;
import com.collibra.dataquality.properties.ExternalSystemMetricsInputStreamRetriever;
import com.collibra.dataquality.template.AssetsTemplateCreator;
import com.collibra.dataquality.template.DataQualityMetricTemplateCreator;
import com.collibra.dataquality.transform.ExternalSystemMetricsTransformer;
import com.collibra.importer.rest.client.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	private static DataQualityMetricsImporter dataQualityMetricsImporter;
	private static JobsPoller jobsPoller;

	static {
		initializeApiClients();
	}

	private static void initializeApiClients() {
		ApiClientProvider apiClientProvider = new ApiClientProvider();
		CollibraApi collibraApi = new CollibraApi(apiClientProvider);
		dataQualityMetricsImporter = new DataQualityMetricsImporter(collibraApi.importerApi());
		jobsPoller = new JobsPoller(collibraApi.jobsApi());
	}

	public static void main(String[] args) {
		//Retrieving Input Stream from csv file which contains external system metrics with column identifiers
		InputStream externalSystemMetrics = ExternalSystemMetricsInputStreamRetriever
				.getExternalSystemMetricsFileAsInputStream();

		//Transforming external system metrics csv file into csv file which will be used in Import REST API request
		File transformedCsvFile = prepareTransformedCsvFile(externalSystemMetrics);

		//Template which will be used in Import REST API request, contains mappings to specific attributes and relation between dq metric and column
		String dataQualityMetricTemplate = prepareDataQualityMetricTemplate();

		//Here is called Import REST API
		LOGGER.info("Start request process to Import API with transformed csv file and data quality metric template");
		Job job = dataQualityMetricsImporter.importDataQualityMetrics(transformedCsvFile, dataQualityMetricTemplate);

		//Wait until import job with transformed external metrics is completed
		LOGGER.info("Waiting process until import job with id `{}` is completed", job.getId());
		jobsPoller.waitUntilJobIsCompleted(job.getId());
		LOGGER.info("Waiting process is finished. Job `{}` is completed", job.getId());
	}

	private static String prepareDataQualityMetricTemplate() {
		LOGGER.info("Start creation process of Data Quality Metric Template");
		AssetsTemplateCreator assetsTemplateCreator = new AssetsTemplateCreator();
		DataQualityMetricTemplateCreator dqMetricTemplateCreator = new DataQualityMetricTemplateCreator(
				assetsTemplateCreator);
		return dqMetricTemplateCreator.getTemplate();
	}

	private static File prepareTransformedCsvFile(InputStream externalSystemMetrics) {
		LOGGER.info("Start transformation process based on external system metrics csv file");
		CsvFileCreator csvFileCreator = new CsvFileCreator();
		ExternalSystemMetricsTransformer externalSystemMetricsTransformer = new ExternalSystemMetricsTransformer(
				csvFileCreator);
		return externalSystemMetricsTransformer.transform(externalSystemMetrics);
	}

}
