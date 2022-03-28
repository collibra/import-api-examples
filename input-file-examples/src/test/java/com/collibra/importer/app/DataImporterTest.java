package com.collibra.importer.app;

import com.collibra.core.rest.client.api.AttributeTypesApi;
import com.collibra.core.rest.client.api.CommunitiesApi;
import com.collibra.core.rest.client.model.AddAttributeTypeRequest;
import com.collibra.core.rest.client.model.AttributeType;
import com.collibra.core.rest.client.model.Community;
import com.collibra.importer.client.ApiClientProvider;
import com.collibra.importer.client.CollibraApi;
import com.collibra.importer.client.ImporterApi;
import com.collibra.importer.jobs.JobsPoller;

import com.collibra.importer.rest.client.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.io.File;
import java.util.*;

import static com.collibra.core.rest.client.model.Job.StateEnum;

class DataImporterTest {

	private static DataImporter dataImporter;
	private static JobsPoller jobsPoller;
	private static CommunitiesApi communitiesApi;
	private static AttributeTypesApi attributeTypesApi;

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	protected static final String DEFAULT_COMMUNITY = "Data Governance Council";
	protected static final String DEFAULT_DOMAIN = "New Data Assets";

	@BeforeAll
	static void setupClient() {
		ApiClientProvider apiClientProvider = new ApiClientProvider();
		CollibraApi collibraApi = new CollibraApi(apiClientProvider);
		ImporterApi importerApi = collibraApi.importerApi();
		communitiesApi = collibraApi.communitiesApi();
		attributeTypesApi = collibraApi.attributeTypesApi();
		dataImporter = new DataImporter(importerApi);
		jobsPoller = new JobsPoller(collibraApi.jobsApi());
	}

	@AfterEach
	public void tearDown() {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("name", "DBs Community");
		List<Community> communityList = communitiesApi.findCommunities(queryParams).getResults();
		if (communityList!=null && !communityList.isEmpty()) {
			Community community = communityList.get(0);
			com.collibra.core.rest.client.model.Job removalJob = communitiesApi.removeCommunitiesInJob(Collections.singletonList(community.getId()));
			jobsPoller.waitUntilJobIsCompleted(removalJob.getId());
		}
	}

	@Test
	void importJobAssets() throws Exception {
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

		Map<String,List<AttributeValue>> attributes = new HashMap<>();

		List<AttributeValue> attributeValues = new ArrayList<>();
		AttributeValue attributeValue = new AttributeValue();
		attributeValue.setValue("This is a new description");
		attributeValues.add(attributeValue);
		attributes.put("Description",attributeValues);
		assetCommand.setAttributes(attributes);

		Job importerJob = dataImporter.submit(assetCommand);

		com.collibra.core.rest.client.model.Job jobFromJobsApi = jobsPoller.waitUntilJobIsCompleted(importerJob.getId());
		Assertions.assertThat(jobFromJobsApi.getState()).isEqualTo(StateEnum.COMPLETED);
	}

	@Test
	void testImportAssetsWithImplicitMappings() {
		String folderName = "add-assets-with-implicit-mappings";
		String jsonFileName = "assets-with-implicit-mappings.json";
		String csvFileName = "assets-with-implicit-mappings.csv";
		String excelFileName = "assets-with-implicit-mappings.xlsx";
		String templateFileName = "assets-with-implicit-mappings-template.json";

		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
	}

	@Test
	void testImportResourcesWithAttributes() {
		String folderName = "add-community-domain-asset-with-attributes";
		String jsonFileName = "multiple-attributes.json";
		String csvFileName = "multiple-attributes.csv";
		String excelFileName = "multiple-attributes.xlsx";
		String templateFileName = "multiple-attributes-template.json";

		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
	}

	@Test
	void testImportResourcesWithMultiValueAttributes() {
		String folderName = "add-community-domain-asset-with-multivalue-attributes";
		String jsonFileName = "multivalue-attributes.json";
		String csvFileName = "multivalue-attributes.csv";
		String excelFileName = "multivalue-attributes.xlsx";
		String templateFileName = "multivalue-attributes-template.json";
		//precondition
		AddAttributeTypeRequest attributeTypeRequest = new AddAttributeTypeRequest();
		attributeTypeRequest.setName("indexes");
		attributeTypeRequest.setKind(AddAttributeTypeRequest.KindEnum.MULTI_VALUE_LIST);
		attributeTypeRequest.setAllowedValues(Arrays.asList("surname", "name", "id"));
		AttributeType attributeType = attributeTypesApi.addAttributeType(attributeTypeRequest);

		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
		if (attributeType!=null) {
			attributeTypesApi.removeAttributeType(attributeType.getId());
		}
	}

	@Test
	void testImportAssetsWithRelations() {
		String folderName = "add-community-domain-assets-relations";
		String jsonFileName = "relations.json";
		String csvFileName = "relations.csv";
		String excelFileName = "relations.xlsx";
		String templateFileName = "relations-template.json";

		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
	}

	@Test
	void testImportTablesAndColumns() {
		String folderName = "add-community-domain-with-tables-columns";
		String jsonFileName = "schema-tables-columns.json";
		String csvFileName = "schema-tables-columns.csv";
		String excelFileName = "schema-tables-columns.xlsx";
		String templateFileName = "schema-tables-columns-template.json";

		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
	}

	@Test
	void testImportAssetsWithResponsibilities() {
		String folderName = "add-community-domain-assets-with-responsibilities";
		String jsonFileName = "responsibilities.json";
		String csvFileName = "responsibilities.csv";
		String excelFileName = "responsibilities.xlsx";
		String templateFileName = "responsibilities-template.json";

		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
	}

	@Test
	void testImportAssetsWithTags() {
		String folderName = "add-community-domain-assets-with-tags";
		String jsonFileName = "tags.json";
		String csvFileName = "tags.csv";
		String excelFileName = "tags.xlsx";
		String templateFileName = "tags-template.json";

		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);

	}

	@Test
	void testImportComplexRelations() {
		String folderName = "add-complex-relations";
		String jsonFileName = "complex-relations.json";
		String csvFileName = "complex-relations.csv";
		String excelFileName = "complex-relations.xlsx";
		String templateFileName = "complex-relations-template.json";
		//precondition
		importJsonFile(folderName,"precondition_for_complex_relations_creation.json");

		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
	}

	@Test
	void testImportMappingsForAssets() {
		String folderName = "add-mappings-for-assets";
		String jsonFileName = "mappings-for-assets.json";
		String csvFileName = "mappings-for-assets.csv";
		String excelFileName = "mappings-for-assets.xlsx";
		String templateFileName = "mappings-for-assets-template.json";
		importJsonFile("add-assets-with-implicit-mappings","assets-with-implicit-mappings.json");
		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
	}

	@Test
	void testUpdateAssets() {
		String folderName = "change-assets";
		String jsonFileName = "schema-tables-columns-updated.json";
		String csvFileName = "schema-tables-columns-updated.csv";
		String excelFileName = "schema-tables-columns-updated.xlsx";
		String templateFileName = "schema-tables-columns-updated-template.json";
		importJsonFile("add-community-domain-with-tables-columns","schema-tables-columns.json");
		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
	}

	@Test
	void testChangeAssetsIdentifiedByMappings() {
		String folderName = "change-assets-identified-by-mappings";
		String jsonFileName = "modify-asset-using-mapping.json";
		String csvFileName = "modify-asset-using-mapping.csv";
		String excelFileName = "modify-asset-using-mapping.xlsx";
		String templateFileName = "modify-asset-using-mapping-template.json";
		//precondition
		importJsonFile("add-assets-with-implicit-mappings","assets-with-implicit-mappings.json");
		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
	}

	@Test
	void testChangeAssetsIdentifiedByExternalMappings() {
		String folderName = "change-assets-identified-by-mappings-external";
		String jsonFileName = "assets-with-implicit-mappings-updated.json";
		String csvFileName = "assets-with-implicit-mappings-updated.csv";
		String excelFileName = "assets-with-implicit-mappings-updated.xlsx";
		String templateFileName = "assets-with-implicit-mappings-updated-template.json";

		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
	}

	@Test
	void testRemoveAssets() {
		String folderName = "remove-assets";
		String jsonFileName = "schema-tables-columns-removal.json";
		String csvFileName = "schema-tables-columns-removal.csv";
		String excelFileName = "schema-tables-columns-removal.xlsx";
		String templateFileName = "schema-tables-columns-removal-template.json";

		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
	}

	@Test
	void removeAssetsWithMappings() {
		String folderName = "remove-assets-with-mappings";
		String jsonFileName = "assets-with-implicit-mappings-removal.json";
		String csvFileName = "assets-with-implicit-mappings-removal.csv";
		String excelFileName = "assets-with-implicit-mappings-removal.xlsx";
		String templateFileName = "assets-with-implicit-mappings-removal-template.json";

		importFiles(folderName,jsonFileName,csvFileName,excelFileName,templateFileName);
	}

	private void importFiles(String folderName,String jsonFileName,String csvFileName,String excelFileName,String templateFileName) {
		try {
			importJsonFile(folderName, jsonFileName);
			importCSVFile(folderName, csvFileName, templateFileName);
			importExcelFile(folderName, excelFileName, templateFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void importCSVFile(String folderName, String csvFileName, String templateFileName) throws IOException {
		File csvFile = getFile(folderName,csvFileName);
		String template = getTemplate(folderName,templateFileName);

		Job job = dataImporter.importCSVFile(csvFile, template);
		com.collibra.core.rest.client.model.Job jobFromJobsApi = jobsPoller.waitUntilJobIsCompleted(job.getId());

		Assertions.assertThat(jobFromJobsApi.getState()).isEqualTo(StateEnum.COMPLETED);
	}

	private void importExcelFile(String folderName, String excelFileName, String templateFileName) throws IOException {
		File excelFile = getFile(folderName,excelFileName);
		String template = getTemplate(folderName,templateFileName);

		Job job = dataImporter.importExcelFile(excelFile, template);
		com.collibra.core.rest.client.model.Job jobFromJobsApi = jobsPoller.waitUntilJobIsCompleted(job.getId());

		Assertions.assertThat(jobFromJobsApi.getState()).isEqualTo(StateEnum.COMPLETED);
	}

	private void importJsonFile(String folder,String jsonFileName) {
		File jsonFile = getFile(folder,jsonFileName);

		Job job = dataImporter.importJsonFile(jsonFile);
		com.collibra.core.rest.client.model.Job jobFromJobsApi = jobsPoller.waitUntilJobIsCompleted(job.getId());

		Assertions.assertThat(jobFromJobsApi.getState()).isEqualTo(StateEnum.COMPLETED);
	}

	private String getTemplate(String folder,String templateFileName) throws IOException {
		File templateFile = FileUtils.getFile("src","test","resources",folder,templateFileName);
		InputStream is = getInputStreamFrom(templateFile);
		JsonNode jsonNode = OBJECT_MAPPER.readValue(is,JsonNode.class);
		return OBJECT_MAPPER.writeValueAsString(jsonNode);
	}

	private File getFile(String folder, String fileName) {
		return FileUtils.getFile("src","test","resources", folder, fileName);
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
