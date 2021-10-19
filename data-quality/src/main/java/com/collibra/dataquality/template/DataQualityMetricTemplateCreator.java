package com.collibra.dataquality.template;

import static com.collibra.dataquality.template.DataQualityMetricTemplatePlaceholder.DATA_QUALITY_METRIC_ASSET_NAME;
import static com.collibra.dataquality.template.DataQualityMetricTemplatePlaceholder.EXTERNAL_ENTITY_ID;
import static com.collibra.dataquality.template.DataQualityMetricTemplatePlaceholder.LOADED_ROWS_ATTRIBUTE;
import static com.collibra.dataquality.template.DataQualityMetricTemplatePlaceholder.PASSING_FRACTION_ATTRIBUTE;
import static com.collibra.dataquality.template.DataQualityMetricTemplatePlaceholder.RELATED_COLUMN_COMMUNITY;
import static com.collibra.dataquality.template.DataQualityMetricTemplatePlaceholder.RELATED_COLUMN_DOMAIN;
import static com.collibra.dataquality.template.DataQualityMetricTemplatePlaceholder.RELATED_COLUMN_FULL_NAME;
import static com.collibra.dataquality.template.DataQualityMetricTemplatePlaceholder.RESULT_ATTRIBUTE;
import static com.collibra.dataquality.template.DataQualityMetricTemplatePlaceholder.ROWS_FAILED_ATTRIBUTE;
import static com.collibra.dataquality.template.DataQualityMetricTemplatePlaceholder.ROWS_PASSED_ATTRIBUTE;
import static com.collibra.dataquality.template.DataQualityMetricTemplatePlaceholder.THRESHOLD_ATTRIBUTE;

import static java.util.Collections.singletonList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.collibra.importer.properties.ConfigProperties;
import com.collibra.importer.parameters.AssetIdentifier;
import com.collibra.importer.parameters.AssetImportCommand;
import com.collibra.importer.parameters.AssetTypeIdentifier;
import com.collibra.importer.parameters.AssetsTemplateParameter;
import com.collibra.importer.parameters.AttributeValue;
import com.collibra.importer.parameters.Attributes;
import com.collibra.importer.parameters.CommunityIdentifier;
import com.collibra.importer.parameters.CommunityImportCommand;
import com.collibra.importer.parameters.DomainIdentifier;
import com.collibra.importer.parameters.DomainImportCommand;
import com.collibra.importer.parameters.DomainTypeIdentifier;
import com.collibra.importer.parameters.Relations;

/**
 * Data Quality Metric Template Creator which creates template for import data quality metrics using import api with csv file.
 */
public class DataQualityMetricTemplateCreator {

	private static final String RULEBOOK_DOMAIN_TYPE = "Rulebook";

	private static final String DATA_QUALITY_METRIC_ASSET_TYPE = "Data Quality Metric";

	//Attributes
	private static final String PASSING_FRACTION_ATTRIBUTE_TYPE = "Passing Fraction";
	private static final String THRESHOLD_ATTRIBUTE_TYPE = "Threshold";
	private static final String ROWS_PASSED_ATTRIBUTE_TYPE = "Rows Passed";
	private static final String ROWS_FAILED_ATTRIBUTE_TYPE = "Rows Failed";
	private static final String RESULT_ATTRIBUTE_TYPE = "Result";
	private static final String LOADED_ROWS_ATTRIBUTE_TYPE = "Loaded Rows";

	//Relation: DQ Metric applies to Column, DQ metric is target	
	private static final String DQ_METRIC_APPLIES_TO_COLUMN_RELATION = "00000000-0000-0000-0000-000000007018:SOURCE";

	private static final Logger LOGGER = LoggerFactory.getLogger(DataQualityMetricTemplateCreator.class);

	private final AssetsTemplateCreator assetsTemplateCreator;

	public DataQualityMetricTemplateCreator(AssetsTemplateCreator assetsTemplateCreator) {
		this.assetsTemplateCreator = assetsTemplateCreator;
	}

	/**
	 * Provides a template which is needed to import data quality metrics using import api with csv file.
	 *
	 * @return the template for import data quality metrics
	 */
	public String getTemplate() {
		AssetsTemplateParameter assetsTemplateParameter = new AssetsTemplateParameter();

		DomainImportCommand domainMetricsImportCommand = domainMetricsImportCommand();
		CommunityImportCommand communityImportCommand = new CommunityImportCommand()
				.withIdentifier(domainMetricsImportCommand.getIdentifier().getCommunity());
		assetsTemplateParameter.withCommunities(singletonList(communityImportCommand))
				.withDomains(singletonList(domainMetricsImportCommand));

		Attributes metricAttributes = specifyMetricAttributes();

		Relations metricToColumnRelation = specifyRelationToColumn();

		AssetIdentifier dqMetricIdentifier = new AssetIdentifier()
				.withExternalSystemId(ConfigProperties.getExternalSystemId())
				.withExternalEntityId(EXTERNAL_ENTITY_ID.getPlaceholder());
		AssetTypeIdentifier dqMetricAssetTypeIdentifier = new AssetTypeIdentifier()
				.withName(DATA_QUALITY_METRIC_ASSET_TYPE);
		AssetImportCommand dqMetricAssetImportCommand = new AssetImportCommand()
				.withIdentifier(dqMetricIdentifier)
				.withName(DATA_QUALITY_METRIC_ASSET_NAME.getPlaceholder())
				.withDomain(domainMetricsImportCommand.getIdentifier())
				.withType(dqMetricAssetTypeIdentifier)
				.withRelations(metricToColumnRelation)
				.withAttributes(metricAttributes);

		assetsTemplateParameter.setAssets(singletonList(dqMetricAssetImportCommand));
		logConfigDataUsedToGenerateTemplate();
		return assetsTemplateCreator.getTemplate(assetsTemplateParameter);
	}

	private DomainImportCommand domainMetricsImportCommand() {
		CommunityIdentifier communityIdentifier = new CommunityIdentifier()
				.withName(ConfigProperties.getDataQualityMetricsCommunityName());
		DomainIdentifier domainIdentifier = new DomainIdentifier()
				.withCommunity(communityIdentifier)
				.withName(ConfigProperties.getDataQualityMetricsDomainName());
		DomainTypeIdentifier domainTypeImportCommand = new DomainTypeIdentifier()
				.withName(RULEBOOK_DOMAIN_TYPE);
		return new DomainImportCommand()
				.withIdentifier(domainIdentifier)
				.withType(domainTypeImportCommand);
	}

	private Relations specifyRelationToColumn() {
		AssetIdentifier columnIdentifier = columnIdentifier();
		Relations metricToColumnRelation = new Relations();
		metricToColumnRelation.withAdditionalProperty(DQ_METRIC_APPLIES_TO_COLUMN_RELATION,
				singletonList(columnIdentifier));
		return metricToColumnRelation;
	}

	private AssetIdentifier columnIdentifier() {
		CommunityIdentifier communityIdentifier = new CommunityIdentifier()
				.withName(RELATED_COLUMN_COMMUNITY.getPlaceholder());
		DomainIdentifier domainIdentifier = new DomainIdentifier()
				.withCommunity(communityIdentifier)
				.withName(RELATED_COLUMN_DOMAIN.getPlaceholder());
		return new AssetIdentifier()
				.withDomain(domainIdentifier)
				.withName(RELATED_COLUMN_FULL_NAME.getPlaceholder());
	}

	private Attributes specifyMetricAttributes() {
		Attributes metricAttributes = new Attributes();
		setAttribute(metricAttributes, PASSING_FRACTION_ATTRIBUTE_TYPE, PASSING_FRACTION_ATTRIBUTE.getPlaceholder());
		setAttribute(metricAttributes, THRESHOLD_ATTRIBUTE_TYPE, THRESHOLD_ATTRIBUTE.getPlaceholder());
		setAttribute(metricAttributes, ROWS_PASSED_ATTRIBUTE_TYPE, ROWS_PASSED_ATTRIBUTE.getPlaceholder());
		setAttribute(metricAttributes, ROWS_FAILED_ATTRIBUTE_TYPE, ROWS_FAILED_ATTRIBUTE.getPlaceholder());
		setAttribute(metricAttributes, RESULT_ATTRIBUTE_TYPE, RESULT_ATTRIBUTE.getPlaceholder());
		setAttribute(metricAttributes, LOADED_ROWS_ATTRIBUTE_TYPE, LOADED_ROWS_ATTRIBUTE.getPlaceholder());
		return metricAttributes;
	}

	private void setAttribute(Attributes metricAttributes, String propertyName, String propertyValue) {
		AttributeValue attributeValue = new AttributeValue().withValue(propertyValue);
		metricAttributes.withAdditionalProperty(propertyName, singletonList(attributeValue));
	}

	private void logConfigDataUsedToGenerateTemplate() {
		LOGGER.info("Config data used to generate Data Quality Metrics Template:");
		LOGGER.info("External System Id: '{}'", ConfigProperties.getExternalSystemId());
		LOGGER.info("Data Quality Metrics Domain: '{}'", ConfigProperties.getDataQualityMetricsDomainName());
		LOGGER.info("Data Quality Metrics Community: '{}'", ConfigProperties.getDataQualityMetricsCommunityName());
	}

}
