package com.collibra.importer.demo;

import com.collibra.core.rest.client.api.AssetsApi;
import com.collibra.core.rest.client.model.Asset;
import com.collibra.core.rest.client.model.AssetPagedResponse;
import com.collibra.importer.client.ApiClientProvider;
import com.collibra.importer.client.CollibraApi;
import com.collibra.importer.client.ImporterApi;
import com.collibra.importer.jobs.JobsPoller;
import com.collibra.importer.rest.client.model.Job;
import com.collibra.importer.rest.client.model.SynchronizationBatchJsonInJobRequest;
import com.collibra.importer.rest.client.model.SynchronizationFinalizationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This class demonstrates how to use the Import API to refresh a synchronization
 * that has already been finalized in Collibra.
 * The demo sends 2 batches of resources to synchronize and then finalizes
 * the synchronization. The first batch has 3 assets and the second
 * batch has 2 assets to import.
 * It then sends another request to synchronize, with the first batch having just
 * 2 assets from the earlier request and the second batch having the same 2 assets
 * and then finalizes the synchronization, passing 'REMOVE_RESOURCES' as the
 * finalizationStrategy.
 * After each request to the Import API, the job poller waits for the job to complete.
 * The demo finally verifies that the third asset from the first batch is deleted.
 * Note: If the finalizationStrategy had the value "CHANGE_STATUS", the asset would NOT
 * have been deleted from Collibra, but just the status would have been updated as per the value in
 * finalizationParameters.
 */

public class SynchronizeRefreshDemo5 {
    private static final Logger logger = LoggerFactory.getLogger(SynchronizeRefreshDemo5.class);
    private final ImporterApi importerApi;
    private final JobsPoller jobsPoller;
    private final AssetsApi assetsApi;

    /**
     * The constructor sets the value of importerApi and jobsAPI, wrapper classes
     * of the API classes generated from the OpenAPI spec generator
     */
    public SynchronizeRefreshDemo5() {
        ApiClientProvider apiClientProvider = new ApiClientProvider();
        CollibraApi collibraApi = new CollibraApi(apiClientProvider);
        this.importerApi = collibraApi.importerApi();
        this.assetsApi = collibraApi.assetsApi();
        jobsPoller = new JobsPoller(collibraApi.jobsApi());
    }

    public static void main(String[] args) {
        SynchronizeRefreshDemo5 synchronizeRefreshDemo5 = new SynchronizeRefreshDemo5();
        UUID synchronizationId = UUID.randomUUID();
        //Sends first request to import resources (3 assets) in the first batch of synchronization
        File jsonFileBatch1 = synchronizeRefreshDemo5.getJsonFile("Demo4_SynchronizeBatch1.json");
        synchronizeRefreshDemo5.synchronizeBatchJson(synchronizationId, jsonFileBatch1);

        //Sends first request to import resources (2 assets) in the second batch of synchronization
        File jsonFileBatch2 = synchronizeRefreshDemo5.getJsonFile("Demo4_SynchronizeBatch2.json");
        synchronizeRefreshDemo5.synchronizeBatchJson(synchronizationId, jsonFileBatch2);

        //Sends a request to finalize the synchronization
        synchronizeRefreshDemo5.synchronizeFinalize(synchronizationId);

        //Sends second request to import resources (2 assets from the first request and 1 removed)
        // in the first batch of synchronization
        File jsonFileBatch3 = synchronizeRefreshDemo5.getJsonFile("Demo5_SynchronizeBatch1.json");
        synchronizeRefreshDemo5.synchronizeBatchJson(synchronizationId, jsonFileBatch3);

        //Sends a second request to import resources (same as the first request)
        // in the first batch of synchronization
        File jsonFileBatch4 = synchronizeRefreshDemo5.getJsonFile("Demo5_SynchronizeBatch2.json");
        synchronizeRefreshDemo5.synchronizeBatchJson(synchronizationId, jsonFileBatch4);

        //Sends a request to finalize the synchronization
        synchronizeRefreshDemo5.synchronizeFinalize(synchronizationId);

        //Since the third asset was removed from the first batch of the second synchronization request,
        // and we sent "finalizationStrategy" as "REMOVE_RESOURCES", it means that the third asset should
        // be removed from Collibra. Verify the asset is deleted.
        synchronizeRefreshDemo5.verifyAssetNotExists("SynchronizeDemoAsset3");
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
        synchronizationFinalizationRequest.finalizationStrategy("REMOVE_RESOURCES");
        Job job = importerApi.synchronizeFinalizeInJob(synchronizationFinalizationRequest);
        jobsPoller.waitUntilJobIsCompleted(job.getId());
    }

    /**
     * Verifies that the asset does not exist in Collibra when you try to get the asset by name.
     *
     * @param assetName the assetName to get
     */
    private void verifyAssetNotExists(String assetName) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", assetName);
        queryParams.put("nameMatchMode", "EXACT");
        AssetPagedResponse response = assetsApi.findAssets(queryParams);
        List<Asset> assets = response.getResults();
        if (assets != null && !assets.isEmpty()) {
            logger.error("Asset {} is not deleted as expected.", assetName);
        }
    }
}
