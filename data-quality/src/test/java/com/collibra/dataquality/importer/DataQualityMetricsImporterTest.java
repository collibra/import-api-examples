package com.collibra.dataquality.importer;

import static com.collibra.core.rest.client.model.Job.StateEnum.COMPLETED;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.collibra.importer.client.ApiClientProvider;
import com.collibra.importer.client.CollibraApi;
import com.collibra.importer.client.ImporterApi;
import com.collibra.dataquality.csv.CsvFileCreator;
import com.collibra.dataquality.fixtures.TestInputStreamProvider;
import com.collibra.importer.jobs.JobsPoller;
import com.collibra.dataquality.template.AssetsTemplateCreator;
import com.collibra.dataquality.template.DataQualityMetricTemplateCreator;
import com.collibra.dataquality.transform.ExternalSystemMetricsTransformer;
import com.collibra.importer.rest.client.model.ImportJsonInJobRequest;
import com.collibra.importer.rest.client.model.Job;

class DataQualityMetricsImporterTest {

	private static DataQualityMetricsImporter dataQualityMetricsImporter;
	private static ImporterApi importerApi;
	private static JobsPoller jobsPoller;

	@BeforeAll
	static void setupClient() {
		ApiClientProvider apiClientProvider = new ApiClientProvider();
		CollibraApi collibraApi = new CollibraApi(apiClientProvider);
		importerApi = collibraApi.importerApi();
		dataQualityMetricsImporter = new DataQualityMetricsImporter(importerApi);
		jobsPoller = new JobsPoller(collibraApi.jobsApi());
	}

	@Test
	void importsDataQualityMetrics() {
		// given
		prepareTestColumnsInCollibra();
		String template = prepareTemplate();
		File collibraMetricsFile = prepareTransformedCsvFile();

		// when
		Job job = dataQualityMetricsImporter.importDataQualityMetrics(collibraMetricsFile, template);
		com.collibra.core.rest.client.model.Job jobFromJobsApi = jobsPoller.waitUntilJobIsCompleted(job.getId());

		// then
		assertThat(jobFromJobsApi.getState()).isEqualTo(COMPLETED);
	}

	private String prepareTemplate() {
		AssetsTemplateCreator assetsTemplateCreator = new AssetsTemplateCreator();
		DataQualityMetricTemplateCreator dataQualityMetricTemplateCreator = new DataQualityMetricTemplateCreator(
				assetsTemplateCreator);
		return dataQualityMetricTemplateCreator.getTemplate();
	}

	private File prepareTransformedCsvFile() {
		InputStream testScores = TestInputStreamProvider.getInputStreamFromTestScores();
		CsvFileCreator csvFileCreator = new CsvFileCreator();
		ExternalSystemMetricsTransformer externalSystemMetricsTransformer = new ExternalSystemMetricsTransformer(
				csvFileCreator);
		return externalSystemMetricsTransformer.transform(testScores);
	}

	private void prepareTestColumnsInCollibra() {
		File columnsFile = new File(getClass().getResource("schema-tables-columns.json").getFile());
		ImportJsonInJobRequest importJsonInJobRequest = new ImportJsonInJobRequest();
		importJsonInJobRequest.setFile(columnsFile);

		Job job = importerApi.importJsonInJob(importJsonInJobRequest);
		com.collibra.core.rest.client.model.Job jobFromJobsApi = jobsPoller.waitUntilJobIsCompleted(job.getId());
		assertThat(jobFromJobsApi.getState()).isEqualTo(COMPLETED);
	}

}
