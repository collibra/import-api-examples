package com.collibra.dataquality.transform;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class ExternalSystemMetric {

	private String metricId;

	private String projectName;

	private String scorecardName;

	private String scoreName;

	private int totalRows;

	private double validPercentage;

	private int invalidRows;

	private double threshold;

	private String maxTimeCreated;

	private String columnName;

	private String columnDomainName;

	private String columnCommunityName;

}
