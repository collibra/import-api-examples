package com.collibra.importer.demo;

import com.collibra.importer.client.ApiClientProvider;
import com.collibra.importer.client.CollibraApi;
import com.collibra.importer.client.ImporterApi;
import com.collibra.importer.rest.client.model.ImportJsonInJobRequest;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * This class demonstrates how to import domains and assets from a JSON file into Collibra.
 * The Demo1_ImportJson.json file has one asset of 'Business Term' type with a 'Definition' attribute.
 * The other asset is an 'Acronym' with a relation 'has acronym/is acronym' to the 'Business Term'.
 */
public class ImportJsonFileDemo1 {

    private final ImporterApi importerApi;

    /**
     * The constructor sets the value of importerApi, a wrapper class of the API class generated from
     * the OpenAPI spec generator
     */
    public ImportJsonFileDemo1() {
        ApiClientProvider apiClientProvider = new ApiClientProvider();
        CollibraApi collibraApi = new CollibraApi(apiClientProvider);
        this.importerApi = collibraApi.importerApi();
    }

    public static void main(String[] args) {
        ImportJsonFileDemo1 importJsonFileDemo1 = new ImportJsonFileDemo1();
        File jsonFileToImport = importJsonFileDemo1.getJsonFile();
        importJsonFileDemo1.importJsonFile(jsonFileToImport);
    }

    /**
     * Get the JSON file Demo1_ImportJson from the resources folder.
     *
     * @return JSON file
     */
    private File getJsonFile() {
        return FileUtils.getFile("import-api-tutorial", "src", "main", "resources", "Demo1_ImportJson.json");
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
        importerApi.importJsonInJob(importJsonInJobRequest);
    }
}
