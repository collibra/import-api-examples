package com.collibra.importer.demo;

import com.collibra.importer.client.ApiClientProvider;
import com.collibra.importer.client.CollibraApi;
import com.collibra.importer.client.ImporterApi;
import com.collibra.importer.jobs.JobsPoller;
import com.collibra.importer.rest.client.model.Job;
import com.collibra.importer.rest.client.model.SynchronizationBatchJsonInJobRequest;
import com.collibra.importer.rest.client.model.SynchronizationFinalizationRequest;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.UUID;

/**
 * This class demonstrates how to use the Import API to synchronize resources in Collibra.
 * The demo sends 2 batches of resources to synchronize and then sends a request
 * to finalize the synchronization. The first batch has 3 assets to import and
 * the second batch has 2 assets to import. After each request to the Import API,
 * the job poller waits for the job to complete.
 */
public class SynchronizeJsonFileDemo4 {

    private final ImporterApi importerApi;
    private final JobsPoller jobsPoller;

    /**
     * The constructor sets the value of importerApi and jobsAPI, wrapper classes
     * of the API classes generated from the OpenAPI spec generator
     */
    public SynchronizeJsonFileDemo4() {
        ApiClientProvider apiClientProvider = new ApiClientProvider();
        CollibraApi collibraApi = new CollibraApi(apiClientProvider);
        this.importerApi = collibraApi.importerApi();
        jobsPoller = new JobsPoller(collibraApi.jobsApi());
    }

    public static void main(String[] args) {
        SynchronizeJsonFileDemo4 synchronizeJsonFileDemo4 = new SynchronizeJsonFileDemo4();
        UUID synchronizationId = UUID.randomUUID();

        //Sends a request to import resources (3 assets) in the first batch of synchronization
        File jsonFileBatch1 = synchronizeJsonFileDemo4.getJsonFile("Demo4_SynchronizeBatch1.json");
        synchronizeJsonFileDemo4.synchronizeBatchJson(synchronizationId, jsonFileBatch1);

        //Sends a request to import resources (2 assets) in the second batch of synchronization
        File jsonFileBatch2 = synchronizeJsonFileDemo4.getJsonFile("Demo4_SynchronizeBatch2.json");
        synchronizeJsonFileDemo4.synchronizeBatchJson(synchronizationId, jsonFileBatch2);

        //Sends a request to finalize the synchronization
        synchronizeJsonFileDemo4.synchronizeFinalize(synchronizationId);
    }

    /**
     * Gets the JSON file to synchronize
     *
     * @param fileName name of the file to import
     * @return the JSON file to synchronize
     */
    private File getJsonFile(String fileName) {
        return FileUtils.getFile("import-api-tutorial", "src", "main", "resources", fileName);
    }

    /**
     * Imports resources in batches from a JSON file
     *
     * @param synchronizationId synchronization ID to group different batches
     * @param jsonFile          JSON file to import
     */
    private void synchronizeBatchJson(UUID synchronizationId, File jsonFile) {
        SynchronizationBatchJsonInJobRequest synchronizationBatchJsonInJobRequest = new SynchronizationBatchJsonInJobRequest();
        synchronizationBatchJsonInJobRequest.setSynchronizationId(synchronizationId.toString());
        synchronizationBatchJsonInJobRequest.setFile(jsonFile);
        Job job = importerApi.synchronizeBatchJsonInJob(synchronizationBatchJsonInJobRequest);
        jobsPoller.waitUntilJobIsCompleted(job.getId());
    }

    /**
     * Finalizes a synchronization
     *
     * @param synchronizationId synchronization ID to group different batches
     */
    private void synchronizeFinalize(UUID synchronizationId) {
        SynchronizationFinalizationRequest synchronizationFinalizationRequest = new SynchronizationFinalizationRequest();
        synchronizationFinalizationRequest.setSynchronizationId(synchronizationId.toString());
        importerApi.synchronizeFinalizeInJob(synchronizationFinalizationRequest);
    }
}
