package com.collibra.importer.app;

import com.collibra.core.rest.client.model.Attribute;
import com.collibra.importer.client.ApiClientProvider;
import com.collibra.importer.client.CollibraApi;
import com.collibra.importer.client.ImporterApi;
import com.collibra.importer.jobs.JobsPoller;

import com.collibra.importer.rest.client.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.io.File;
import java.util.*;

import static com.collibra.core.rest.client.model.Job.StateEnum;

class DataImporterTest {

	private static DataImporter dataImporter;
	private static JobsPoller jobsPoller;
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config");

	private static final String CSV_FOLDER = RESOURCE_BUNDLE.getString("collibra.import-client-csv-folder");
	private static final String CSV_FILE = RESOURCE_BUNDLE.getString("collibra.import-client-csv-file");
	private static final String CSV_FILE_TEMPLATE = RESOURCE_BUNDLE.getString("collibra.import-client-csv-template-file");
	private static final String EXCEL_FOLDER = RESOURCE_BUNDLE.getString("collibra.import-client-excel-folder");
	private static final String EXCEL_FILE = RESOURCE_BUNDLE.getString("collibra.import-client-excel-file");
	private static final String EXCEL_FILE_TEMPLATE = RESOURCE_BUNDLE.getString("collibra.import-client-excel-template-file");
	private static final String JSON_FOLDER = RESOURCE_BUNDLE.getString("collibra.import-client-json-folder");
	private static final String JSON_FILE = RESOURCE_BUNDLE.getString("collibra.import-client-json-file");

	protected static final String DEFAULT_COMMUNITY = "Data Governance Council";
	protected static final String DEFAULT_DOMAIN = "New Data Assets";

	@BeforeAll
	static void setupClient() {
		ApiClientProvider apiClientProvider = new ApiClientProvider();
		CollibraApi collibraApi = new CollibraApi(apiClientProvider);
		ImporterApi importerApi = collibraApi.importerApi();
		dataImporter = new DataImporter(importerApi);
		jobsPoller = new JobsPoller(collibraApi.jobsApi());
	}

	@Test
	void importCSVFile() throws IOException {
		String template = getCSVTemplate();
		File csvFile = getCsvFile();

		Job job = dataImporter.importCSVFile(csvFile, template);
		com.collibra.core.rest.client.model.Job jobFromJobsApi = jobsPoller.waitUntilJobIsCompleted(job.getId());

		Assertions.assertThat(jobFromJobsApi.getState()).isEqualTo(StateEnum.COMPLETED);
	}


	@Test
	void importExcelFile() throws IOException {
		String template = getExcelTemplate();
		File excelFile = getExcelFile();

		Job job = dataImporter.importExcelFile(excelFile, template);
		com.collibra.core.rest.client.model.Job jobFromJobsApi = jobsPoller.waitUntilJobIsCompleted(job.getId());

		Assertions.assertThat(jobFromJobsApi.getState()).isEqualTo(StateEnum.COMPLETED);
	}

	@Test
	void importJsonFile() {
		File jsonFile = getJsonFile();

		Job job = dataImporter.importJsonFile(jsonFile);
		com.collibra.core.rest.client.model.Job jobFromJobsApi = jobsPoller.waitUntilJobIsCompleted(job.getId());

		Assertions.assertThat(jobFromJobsApi.getState()).isEqualTo(StateEnum.COMPLETED);
	}

	private String getCSVTemplate() throws IOException {
		File csvTemplateFile = FileUtils.getFile("src","test","resources",CSV_FOLDER,CSV_FILE_TEMPLATE);
		InputStream is = getInputStreamFrom(csvTemplateFile);
		JsonNode jsonNode = OBJECT_MAPPER.readValue(is,JsonNode.class);
		return OBJECT_MAPPER.writeValueAsString(jsonNode);
	}

	private File getCsvFile() {
		return FileUtils.getFile("src","test","resources", CSV_FOLDER,CSV_FILE);
	}

	private File getExcelFile() {
		return FileUtils.getFile("src","test","resources",EXCEL_FOLDER,EXCEL_FILE);
	}

	private String getExcelTemplate() throws IOException {
		File excelTemplateFile = FileUtils.getFile("src","test","resources",EXCEL_FOLDER,EXCEL_FILE_TEMPLATE);
		InputStream is = getInputStreamFrom(excelTemplateFile);
		JsonNode jsonNode = OBJECT_MAPPER.readValue(is,JsonNode.class);
		return OBJECT_MAPPER.writeValueAsString(jsonNode);
	}

	private File getJsonFile() {
		return FileUtils.getFile("src","test","resources", JSON_FOLDER,JSON_FILE);
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

	@Test
	public void importJobAssets() throws Exception {
		CommunityIdentifier communityIdentifier = new CommunityIdentifier();
		communityIdentifier.setName(DEFAULT_COMMUNITY);

		DomainIdentifier domainIdentifier = new DomainIdentifier();
		domainIdentifier.setCommunity(communityIdentifier);
		domainIdentifier.setName(DEFAULT_DOMAIN);

		AssetTypeIdentifier assetType = new AssetTypeIdentifier();
		assetType.setName("Data Element");

		AssetIdentifier assetIdentifier = new AssetIdentifier();
		assetIdentifier.setName("Import-DataElement-SpecUpdate");
		assetIdentifier.setDomain(domainIdentifier);

		AssetImportCommand assetCommand = new AssetImportCommand();
		assetCommand.setResourceType("Asset");
		assetCommand.setIdentifier(assetIdentifier);
		assetCommand.setDisplayName("Import-DataElement-SpecUpdate");
		assetCommand.setType(assetType);
		//assetCommand.setDomain(domainIdentifier);

		Map<String,List<AttributeValue>> attributes = new HashMap<>();

		Attribute attribute = new Attribute();
		List<AttributeValue> attributeValues = new ArrayList<>();
		AttributeValue attributeValue = new AttributeValue();
		attributeValue.setValue("This is a new description");
		attributeValues.add(attributeValue);
		attributes.put("Description",attributeValues);
		assetCommand.setAttributes(attributes);

		dataImporter.submit(assetCommand);
	}

}
