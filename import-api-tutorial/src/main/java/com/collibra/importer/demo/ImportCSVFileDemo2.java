package com.collibra.importer.demo;

import com.collibra.importer.client.ApiClientProvider;
import com.collibra.importer.client.CollibraApi;
import com.collibra.importer.client.ImporterApi;
import com.collibra.importer.rest.client.model.ImportCsvInJobRequest;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * This class demonstrates how to import domains and assets into Collibra from a CSV file using a template.
 * The Demo2_ImportCSV.csv file has one asset of 'Business Term' type with a 'Definition' attribute.
 * The other asset is an 'Acronym' with a relation 'has acronym/is acronym' to the 'Business Term'. The
 * template file is a JSON file that describes how to read the resources from the CSV file.
 */

public class ImportCSVFileDemo2 {
    private final ImporterApi importerApi;

    /**
     * The constructor sets the value of importerApi, a wrapper class of the API class generated from
     * the OpenAPI spec generator
     */
    public ImportCSVFileDemo2() {
        ApiClientProvider apiClientProvider = new ApiClientProvider();
        CollibraApi collibraApi = new CollibraApi(apiClientProvider);
        this.importerApi = collibraApi.importerApi();
    }

    public static void main(String[] args) throws IOException {
        ImportCSVFileDemo2 importCSVFileDemo2 = new ImportCSVFileDemo2();
        File csvFileToImport = importCSVFileDemo2.getCSVFile();
        String templateFile = importCSVFileDemo2.getTemplate();
        importCSVFileDemo2.importCSVFile(csvFileToImport, templateFile);
    }

    /**
     * Invokes the importCsvInJob method of ImporterAPI
     * and passes the CSV and the template files.
     *
     * @param csvFile  CSV file to import
     * @param template JSON file that describes how to read information from CSV file
     */
    private void importCSVFile(File csvFile, String template) {
        ImportCsvInJobRequest importCsvInJobRequest = new ImportCsvInJobRequest();
        importCsvInJobRequest.setFile(csvFile);
        importCsvInJobRequest.setTemplate(template);
        importCsvInJobRequest.setHeaderRow(Boolean.TRUE);
        importerApi.importCsvInJob(importCsvInJobRequest);
    }

    /**
     * Get the CSV file Demo2_ImportCSV.csv from the resources folder
     *
     * @return CSV file
     */
    private File getCSVFile() {
        return FileUtils.getFile("import-api-tutorial", "src", "main", "resources", "Demo2_ImportCSV.csv");
    }

    /**
     * Get the template file Demo_Template from the resources folder
     *
     * @return string of the JSON template file
     * @throws IOException thrown when reading JSON file
     */
    private String getTemplate() throws IOException {
        File csvTemplateFile = FileUtils.getFile("import-api-tutorial", "src", "main", "resources", "Demo_Template.json");
        return FileUtils.readFileToString(csvTemplateFile, StandardCharsets.UTF_8);
    }

}
