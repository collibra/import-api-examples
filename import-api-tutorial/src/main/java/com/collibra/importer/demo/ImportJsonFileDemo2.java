package com.collibra.importer.demo;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.collibra.importer.client.ApiClientProvider;
import com.collibra.importer.client.CollibraApi;
import com.collibra.importer.client.ImporterApi;
import com.collibra.importer.rest.client.model.ImportJsonInJobRequest;

/**
 * This class demonstrates how to import partial data into Collibra.
 * The Demo2_ImportJsonError.json file has one asset of 'Business Term' type and another of incorrect type 'Invalid Asset Type'.
 * When 'continueOnError' mode is activated, only the first asset is imported and the second one is ignored.
 */
public class ImportJsonFileDemo2 {

    private final ImporterApi importerApi;

    /**
     * The constructor sets the value of importerApi, a wrapper class of the API class generated from
     * the OpenAPI spec generator
     */
    public ImportJsonFileDemo2() {
        ApiClientProvider apiClientProvider = new ApiClientProvider();
        CollibraApi collibraApi = new CollibraApi(apiClientProvider);
        this.importerApi = collibraApi.importerApi();
    }

    public static void main(String[] args) {
        ImportJsonFileDemo2 importJsonFileDemo1 = new ImportJsonFileDemo2();
        File jsonFileToImport = importJsonFileDemo1.getJsonFile();
        importJsonFileDemo1.importJsonFile(jsonFileToImport);
    }

    /**
     * Get the JSON file Demo1_ImportJson from the resources folder.
     *
     * @return JSON file
     */
    private File getJsonFile() {
        return FileUtils.getFile("import-api-tutorial", "src", "main", "resources", "Demo2_ImportJsonError.json");
    }

    /**
     * Invokes the importJsonInJob method of ImporterAPI
     * and passes the JSON file.
     *
     * @param jsonFile JSON file to import
     */
    private void importJsonFile(File jsonFile) {
        ImportJsonInJobRequest importJsonInJobRequest = new ImportJsonInJobRequest();
        importJsonInJobRequest.setFile(jsonFile);
        importJsonInJobRequest.setContinueOnError(true);
        importerApi.importJsonInJob(importJsonInJobRequest);
    }
}
