package com.collibra.importer.client;

import java.util.Optional;

import com.collibra.importer.rest.client.api.ImportApi;
import com.collibra.importer.rest.client.model.FindSynchronizationRequest;
import com.collibra.importer.rest.client.model.ImportCsvInJobRequest;
import com.collibra.importer.rest.client.model.ImportExcelInJobRequest;
import com.collibra.importer.rest.client.model.ImportJsonInJobRequest;
import com.collibra.importer.rest.client.model.Job;
import com.collibra.importer.rest.client.model.PagedResponseSynchronizationInfo;
import com.collibra.importer.rest.client.model.SynchronizationBatchCsvInJobRequest;
import com.collibra.importer.rest.client.model.SynchronizationBatchExcelInJobRequest;
import com.collibra.importer.rest.client.model.SynchronizationBatchJsonInJobRequest;
import com.collibra.importer.rest.client.model.SynchronizationCsvInJobRequest;
import com.collibra.importer.rest.client.model.SynchronizationExcelInJobRequest;
import com.collibra.importer.rest.client.model.SynchronizationFinalizationRequest;
import com.collibra.importer.rest.client.model.SynchronizationJsonInJobRequest;

/**
 * This is wrapper for generated ImportApi based on oas spec file, wrapper is created for better user experience.
 * OAS generated client does not have methods with dedicated Request objects. All properties from that request
 * objects are moved to the api client methods signatures. The issue is on the external library side,
 * see: https://github.com/swagger-api/swagger-core/issues/3092
 */
public final class ImporterApi {

	private final ImportApi importApi;

	ImporterApi(ImportApi importApi) {
		this.importApi = importApi;
	}

	public void evictSynchronizationCache(String synchronizationId) {
		importApi.evictSynchronizationCache(synchronizationId);
	}

	public boolean exists(String synchronizationId) {
		return importApi.exists(synchronizationId);
	}

	public PagedResponseSynchronizationInfo findSynchronizationInfos(FindSynchronizationRequest request) {
		return importApi.findSynchronizationInfos(request.getOffset(), request.getLimit());
	}

	public Job importCsvInJob(ImportCsvInJobRequest request) {
		return importApi.importCsvInJob(
				request.getSeparator(),
				request.getQuote(),
				request.getEscape(),
				request.getTemplate(),
				request.getSendNotification(),
				Optional.ofNullable(request.getBatchSize()).map(Long::intValue).orElse(null),
				request.getSimulation(),
				request.getSaveResult(),
				request.getFileId(),
				request.getFile(),
				request.getFileName(),
				request.getDeleteFile(),
				request.getStrictQuotes(),
				request.getIgnoreLeadingWhitespace(),
				request.getHeaderRow());
	}

	public Job importJsonInJob(ImportJsonInJobRequest request) {
		return importApi.importJsonInJob(
				request.getSendNotification(),
				Optional.ofNullable(request.getBatchSize()).map(Long::intValue).orElse(null),
				request.getSimulation(),
				request.getSaveResult(),
				request.getFileId(),
				request.getFile(),
				request.getFileName(),
				request.getDeleteFile());
	}

	public Job importExcelInJob(ImportExcelInJobRequest request) {
		return importApi.importExcelInJob(
				request.getTemplate(),
				request.getSendNotification(),
				Optional.ofNullable(request.getBatchSize()).map(Long::intValue).orElse(null),
				request.getSimulation(),
				request.getSaveResult(),
				request.getFileId(),
				request.getFile(),
				request.getFileName(),
				request.getDeleteFile(),
				request.getSheetName(),
				request.getSheetIndex(),
				request.getHeaderRow());
	}

	public void removeSynchronization(String synchronizationId) {
		importApi.removeSynchronization(synchronizationId);
	}

	public Job synchronizeBatchCsvInJob(SynchronizationBatchCsvInJobRequest request) {
		return importApi.synchronizeBatchCsvInJob(
				request.getSynchronizationId(),
				request.getSeparator(),
				request.getQuote(),
				request.getEscape(),
				request.getTemplate(),
				request.getSendNotification(),
				Optional.ofNullable(request.getBatchSize()).map(Long::intValue).orElse(null),
				request.getSimulation(),
				request.getSaveResult(),
				request.getFileId(),
				request.getFile(),
				request.getFileName(),
				request.getDeleteFile(),
				request.getStrictQuotes(),
				request.getIgnoreLeadingWhitespace(),
				request.getHeaderRow());
	}

	public Job synchronizeBatchExcelInJob(SynchronizationBatchExcelInJobRequest request) {
		return importApi.synchronizeBatchExcelInJob(
				request.getSynchronizationId(),
				request.getTemplate(),
				request.getSendNotification(),
				Optional.ofNullable(request.getBatchSize()).map(Long::intValue).orElse(null),
				request.getSimulation(),
				request.getSaveResult(),
				request.getFileId(),
				request.getFile(),
				request.getFileName(),
				request.getDeleteFile(),
				request.getSheetName(),
				request.getSheetIndex(),
				request.getHeaderRow());
	}

	public Job synchronizeBatchJsonInJob(SynchronizationBatchJsonInJobRequest request) {
		return importApi.synchronizeBatchJsonInJob(
				request.getSynchronizationId(),
				request.getSendNotification(),
				Optional.ofNullable(request.getBatchSize()).map(Long::intValue).orElse(null),
				request.getSimulation(),
				request.getSaveResult(),
				request.getFileId(),
				request.getFile(),
				request.getFileName(),
				request.getDeleteFile());
	}

	public Job synchronizeCsvInJob(SynchronizationCsvInJobRequest request) {
		return importApi.synchronizeCsvInJob(
				request.getSynchronizationId(),
				request.getSeparator(),
				request.getQuote(),
				request.getEscape(),
				request.getTemplate(),
				request.getSendNotification(),
				Optional.ofNullable(request.getBatchSize()).map(Long::intValue).orElse(null),
				request.getSimulation(),
				request.getSaveResult(),
				request.getFileId(),
				request.getFile(),
				request.getFileName(),
				request.getDeleteFile(),
				request.getStrictQuotes(),
				request.getIgnoreLeadingWhitespace(),
				request.getHeaderRow());
	}

	public Job synchronizeExcelInJob(SynchronizationExcelInJobRequest request) {
		return importApi.synchronizeExcelInJob(
				request.getSynchronizationId(),
				request.getTemplate(),
				request.getSendNotification(),
				Optional.ofNullable(request.getBatchSize()).map(Long::intValue).orElse(null),
				request.getSimulation(),
				request.getSaveResult(),
				request.getFileId(),
				request.getFile(),
				request.getFileName(),
				request.getDeleteFile(),
				request.getSheetName(),
				request.getSheetIndex(),
				request.getHeaderRow());
	}

	public Job synchronizeFinalizeInJob(SynchronizationFinalizationRequest request) {
		return importApi.synchronizeFinalizeInJob(
				request.getSynchronizationId(),
				request.getSendNotification(),
				Optional.ofNullable(request.getBatchSize()).map(Long::intValue).orElse(null),
				request.getSimulation(),
				request.getSaveResult(),
				request.getFinalizationStrategy());
	}

	public Job synchronizeJsonInJob(SynchronizationJsonInJobRequest request) {
		return importApi.synchronizeJsonInJob(
				request.getSynchronizationId(),
				request.getSendNotification(),
				Optional.ofNullable(request.getBatchSize()).map(Long::intValue).orElse(null),
				request.getSimulation(),
				request.getSaveResult(),
				request.getFileId(),
				request.getFile(),
				request.getFileName(),
				request.getDeleteFile());
	}
}