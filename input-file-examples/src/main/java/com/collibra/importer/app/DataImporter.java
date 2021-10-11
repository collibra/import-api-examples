package com.collibra.importer.app;

import com.collibra.importer.client.ImporterApi;
import com.collibra.importer.rest.client.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Contains the import operation for CSV or Excel files using the Import API.
 */
public class DataImporter {

	private final ImporterApi importerApi;

	public DataImporter(ImporterApi importerApi) {
		this.importerApi = importerApi;
	}

	/**
	 * Starts an import CSV job
	 *
	 * @param csvFile The CSV file to upload
	 * @param template The template that should be used for parsing and importing the contents of the CSV file
	 * @return the import job
	 */
	public Job importCSVFile(File csvFile, String template) {
		ImportCsvInJobRequest importCsvInJobRequest = new ImportCsvInJobRequest();
		importCsvInJobRequest.setFile(csvFile);
		importCsvInJobRequest.setTemplate(template);
		importCsvInJobRequest.setHeaderRow(Boolean.TRUE);
		return importerApi.importCsvInJob(importCsvInJobRequest);
	}

	/**
	 * Starts an import Excel job
	 *
	 * @param excelFile The Excel file to upload
	 * @param template The template that should be used for parsing and importing the contents of the Excel file
	 * @return the import job
	 */
	public Job importExcelFile(File excelFile, String template) {
		ImportExcelInJobRequest importExcelInJobRequest = new ImportExcelInJobRequest();
		importExcelInJobRequest.setFile(excelFile);
		importExcelInJobRequest.setTemplate(template);
		importExcelInJobRequest.setHeaderRow(Boolean.TRUE);
		return importerApi.importExcelInJob(importExcelInJobRequest);
	}

	/**
	 * Starts an import JSON job
	 *
	 * @param jsonFile The JSON file to upload
	 * @return the import job
	 */
	public Job importJsonFile(File jsonFile) {
		ImportJsonInJobRequest importJsonInJobRequest = new ImportJsonInJobRequest();
		importJsonInJobRequest.setFile(jsonFile);
		return importerApi.importJsonInJob(importJsonInJobRequest);
	}

	/**
	 * Serializes the model object 'AssetImportCommand' to a JSON file and invokes the importJsonInJob API
	 * @param commandsRequest The model object AssetImportCommand to import
	 * @return the import job
	 * @throws Exception
	 */
	public Job submit(AssetImportCommand commandsRequest) throws IOException {
		ImportJsonInJobRequest importJsonInJobRequest = new ImportJsonInJobRequest();

		ObjectMapper messageMapper = new ObjectMapper();

		messageMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String json = messageMapper.writer().writeValueAsString(commandsRequest);
		File jsonFile = File.createTempFile("importAssets", ".json");
		FileWriter writer = new FileWriter(jsonFile);
		writer.write(json);
		writer.close();
		importJsonInJobRequest.setFile(jsonFile);

		return importerApi.importJsonInJob(importJsonInJobRequest);
	}

}
