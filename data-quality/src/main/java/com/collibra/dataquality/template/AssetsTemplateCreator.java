package com.collibra.dataquality.template;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.collibra.importer.parameters.AssetsTemplateParameter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Assets Template Creator which creates template needed for import request using csv file.
 */
public class AssetsTemplateCreator {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private static final Logger LOGGER = LoggerFactory.getLogger(AssetsTemplateCreator.class);

	/**
	 * Gets the json template based on commands.
	 *
	 * @param assetsTemplateParameter aggregates all commands needed to prepare json template
	 * @return the template which is serialized into JSON from Commands wrapped in assetsTemplateParameter
	 */
	public String getTemplate(AssetsTemplateParameter assetsTemplateParameter) {
		if (assetsTemplateParameter == null) {
			throw new IllegalArgumentException("Expected assetsTemplateParameter to be not null.");
		}
		List<?> resources = Stream.of(
				assetsTemplateParameter.getCommunities(),
				assetsTemplateParameter.getDomains(),
				assetsTemplateParameter.getAssets())
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		try {
			String template = OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(resources);
			LOGGER.debug("template: {}", template);
			return template;
		} catch (JsonProcessingException e) {
			LOGGER.error("Error while building template from AssetsTemplateParameter. ", e);
			return "{}";
		}
	}

}
