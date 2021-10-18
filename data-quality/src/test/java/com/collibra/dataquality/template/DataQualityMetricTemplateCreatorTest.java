package com.collibra.dataquality.template;

import com.collibra.importer.parameters.AssetImportCommand;
import com.collibra.importer.parameters.AssetsTemplateParameter;
import com.collibra.importer.properties.ConfigProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataQualityMetricTemplateCreatorTest {

	@Mock
	private AssetsTemplateCreator assetsTemplateCreator;

	@InjectMocks
	private DataQualityMetricTemplateCreator dataQualityMetricTemplateCreator;

	@Test
	void createsDataQualityMetricTemplate() {
		// given
		int expectedNumberOfAttributes = 6;
		int expectedNumberOfRelations = 1;
		String templateFromAssetsTemplateCreator = "";
		when(assetsTemplateCreator.getTemplate(any())).thenReturn(templateFromAssetsTemplateCreator);

		// when
		String template = dataQualityMetricTemplateCreator.getTemplate();

		// then
		assertThat(template).isNotNull();
		assertThat(template).isEqualTo(templateFromAssetsTemplateCreator);
		ArgumentCaptor<AssetsTemplateParameter> captor = ArgumentCaptor.forClass(AssetsTemplateParameter.class);
		verify(assetsTemplateCreator).getTemplate(captor.capture());

		AssetsTemplateParameter assetsTemplateParameter = captor.getValue();
		assertThat(assetsTemplateParameter.getCommunities()).hasSize(1);
		assertThat(assetsTemplateParameter.getDomains()).hasSize(1);
		assertThat(assetsTemplateParameter.getAssets()).hasSize(1);

		assertThat(assetsTemplateParameter.getCommunities().get(0).getIdentifier().getName())
				.isEqualTo(ConfigProperties.getDataQualityMetricsCommunityName());

		assertThat(assetsTemplateParameter.getDomains().get(0).getIdentifier().getName())
				.isEqualTo(ConfigProperties.getDataQualityMetricsDomainName());
		assertThat(assetsTemplateParameter.getDomains().get(0).getIdentifier().getCommunity().getName())
				.isEqualTo(ConfigProperties.getDataQualityMetricsCommunityName());

		AssetImportCommand assetImportCommand = assetsTemplateParameter.getAssets().get(0);
		assertThat(assetImportCommand.getAttributes().getAdditionalProperties().size())
				.isEqualTo(expectedNumberOfAttributes);
		assertThat(assetImportCommand.getRelations().getAdditionalProperties().size())
				.isEqualTo(expectedNumberOfRelations);
		assertThat(assetImportCommand.getIdentifier().getExternalSystemId())
				.isEqualTo(ConfigProperties.getExternalSystemId());
	}

}
