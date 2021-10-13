package com.collibra.importer.demo;

import com.collibra.importer.client.ApiClientProvider;
import com.collibra.importer.client.CollibraApi;
import com.collibra.importer.client.ImporterApi;
import com.collibra.importer.rest.client.model.ImportExcelInJobRequest;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * This class demonstrates how to import domains and assets into Collibra from an Excel file using a template.
 * The Demo3_ImportExcel.xlsx file has one asset of 'Business Term' type with a 'Definition' attribute.
 * The other asset is an 'Acronym' with a relation 'has acronym/is acronym' to the 'Business Term'. The
 * template file is a JSON file that describes how to read the resources from the Excel file.
 */
public class ImportExcelFileDemo3 {
    private final ImporterApi importerApi;

    /**
     * The constructor sets the value of importerApi, a wrapper class of the API class generated from
     * the OpenAPI spec generator
     */
    public ImportExcelFileDemo3() {
        ApiClientProvider apiClientProvider = new ApiClientProvider();
        CollibraApi collibraApi = new CollibraApi(apiClientProvider);
        this.importerApi = collibraApi.importerApi();
    }

    public static void main(String[] args) throws IOException {
        ImportExcelFileDemo3 importExcelFileDemo3 = new ImportExcelFileDemo3();
        File excelFileToImport = importExcelFileDemo3.getExcelFile();
        String templateFile = importExcelFileDemo3.getTemplate();
        importExcelFileDemo3.importExcelFile(excelFileToImport, templateFile);
    }

    /**
     * Invokes the importExcelInJob method of ImporterAPI
     * and passes the JSON file.
     *
     * @param excelFile Excel file to import
     * @param template  JSON file that describes how to read information from the Excel file
     */
    private void importExcelFile(File excelFile, String template) {
        ImportExcelInJobRequest importExcelInJobRequest = new ImportExcelInJobRequest();
        importExcelInJobRequest.setFile(excelFile);
        importExcelInJobRequest.setTemplate(template);
        importExcelInJobRequest.setHeaderRow(Boolean.TRUE);
        importerApi.importExcelInJob(importExcelInJobRequest);
    }

    /**
     * Get the Excel file Demo3_ImportExcel.xlsx from the resources folder
     *
     * @return Excel file
     */
    private File getExcelFile() {
        return FileUtils.getFile("import-api-tutorial", "src", "main", "resources", "Demo3_ImportExcel.xlsx");
    }

    /**
     * Get the template file Demo_Template from the resources folder
     *
     * @return string of the JSON template file
     * @throws IOException thrown when reading JSON file
     */
    private String getTemplate() throws IOException {
        File excelTemplateFile = FileUtils.getFile("import-api-tutorial", "src", "main", "resources", "Demo_Template.json");
        return FileUtils.readFileToString(excelTemplateFile, StandardCharsets.UTF_8);
    }

}
