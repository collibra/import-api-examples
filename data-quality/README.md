# Import data quality metrics from an external system

The repository contains an example project that imports **Data Quality Metrics** from an external system to the Collibra Platform
using the Collibra Import REST API. The input file with the metrics is transformed into second file that is used as a request payload
for the Import API `/import/csv-job` endpoint.

## Table of contents
1. [Project setup](#project-setup)
1. [About the external system metrics input file](#about-the-external-system-metrics-input-file)
1. [About the transformation process and template](#about-the-transformation-process-and-template)
1. [Result of the import](#result-of-the-import)
1. [Collibra developer terms](#collibra-developer-terms)
1. [Additional resources](#additional-resources)

<a name="project-setup"></a>
## Project setup

To compile the code specific to DataQuality module, invoke `gradle clean assemble`.

This project has an application entry point in the [Application](src/main/java/com/collibra/dataquality/app/Application.java) class.

<a name="project-configuration"></a>
## Project configuration

The configuration properties are located in the [**config.properties**](../import-api-java-client/src/main/resources/config.properties) file.
All properties are required to run the DataQuality tests.

**DataQuality Module Configuration**
Parameter | Description | Example
--- | --- | ---
collibra.external-system-metric-file | A CSV file that contains metrics data from an external system. It is used in the transformation process. | /input/input-scores.csv
collibra.dq-metrics-community-name | The name of the community that contains the domain with the data quality metrics. | Data Quality Metrics Community
collibra.dq-metrics-domain-name | The name of the domain that contains the data quality metrics. | Data Quality Metrics Domain
collibra.external-system-id | The external system ID used in the Collibra Platform mapping that identifies the data quality metrics. | External-System-ID

## Tests

- To run an import using test data with transformation process, execute the
[DataQualityMetricsImporterTest](src/test/java/com/collibra/dataquality/importer/DataQualityMetricsImporterTest.java) test.
- You can also invoke `gradle test` to run all the unit tests.

<a name="metrics-input-file"></a>
## About the external system metrics input file

The sample input file for data quality metrics is in the [**input** folder](src/main/resources/input/input-scores.csv).
The CSV file contains metrics data enriched with a column identifier. Metrics are in relation with a given column asset.

## Prerequisites

* The specified column asset must exist in Collibra Platform.
* The external system metrics file must have a header row.
* All numeric values for external system metrics are mandatory.

The external system metrics input file is used as an input for the transformation process.

CSV file column index | Parameter | Example
--- | --- | ---
1 | Metric Id | Test1 Metric Id
2 | Project Name | Test1 Project Name
3 | Scorecard Name | Test1 Scorecard Name
4 | Score Name | Test1 Score Name
5 | Total Rows | 10
6 | Valid Percentage | 50
7 | Invalid Rows | 5
8 | Threshold | 40
9 | Max Time Created | Test Max Time Created
10 | Column Name | TAB_1_COLUMN_1
11 | Column Domain Name | Physical Domain
12 | Column Community Name | DBs Community

<a name="transformation"></a>
### About the transformation process and template

The import process is executed using the `/import/csv-job` REST API endpoint. The most important components
of this call are:
* The CSV file
* The JSON template

The `External System Metrics Input File` represents the input for the transformation process. The output is a CSV file that contains
the required data for the import. A separate JSON template is created to link specific attributes and relations
with the data stored in the CSV file.

### About the CSV file

The CSV file is used as a request payload:

CSV file column index | Parameter | External system metrics file columns | Description | Example
--- | --- | --- | --- | ---
1 | External Metric Id | Metric Id | Imported as **External Entity ID** | Test1 Metric Id
2 | Full Name | Project Name, Score Name, Scorecard Name | Concatenated names with " / " as a delimiter | Test1 Project Name / Test1 Score Name / Test1 Scorecard Name
3 | Passing Fraction | Valid Percentage | Used to create the **Passing Fraction** attribute | 50
4 | Threshold | Threshold | Used to create the **Threshold** attribute | 40
5 | Rows Passed | Total Rows, Invalid Rows | Difference between **Total Rows** and **Invalid Rows**, used to create the **Rows Passed** attribute | 5
6 | Rows Failed | Invalid Rows | Used to create the **Rows Failed** attribute | 5
7 | Result | Valid Percentage, Threshold | `true` when **Valid Percentage** is greater than or equal to **Threshold**, `false` otherwise | true
8 | Loaded Rows | Total Rows | It will be used to create **Loaded Rows** attribute | 10
9 | Column Asset Name |  Column Name  | The name of the column that the imported metrics are linked to | TAB_1_COLUMN_1
10 | Column Domain Name | Column Domain Name | The name of the domain that contains the linked column | Physical Domain
11 | Column Community Name | Column Community Name | The name of the community that contains the domain with the linked column | DBs Community

### About the JSON template

The data quality metrics template creator is in the [**template** folder](src/main/java/com/collibra/dataquality/template/DataQualityMetricTemplateCreator.java).

The template contains information about:
 * Data quality metric asset definition
 * Attribute type names for the data quality metrics
 * Relation between data the quality metrics and the column
 * Mapping for the data quality metric asset
 * The domain and community of the data quality metrics

<a name="metrics-input-file"></a>
## Result of the import

You need both the CSV file and the JSON template for the import operation.

The result of the import is:
- A community for the domain that contains the data quality metrics (if the community does not exist)
- A domain for the data quality metrics (if the domain did not exist)
- An asset of type **Data Quality Metric** with the specified attributes
- A relation between the **Data Quality Metric** asset and the specified **Column** asset (Asset/complies to/applies to/Governance Asset)
- A mapping for the added asset (External System ID defined in the **config.properties** file)

You can check the status of the import job thanks to the implemented polling mechanism.

<a name="terms"></a>
## Collibra developer terms

This repository is part of the Collibra Developer Toolkit. By using or accessing
the Developer Toolkit, you agree to the [Collibra Developer Terms](https://www.collibra.com/developer-terms).

<a name="resources"></a>
## Additional resources

- [Collibra Developer Portal](https://developer.collibra.com/)
- [Collibra Documentation](https://community.collibra.com/documentation/)
- [Collibra](https://www.collibra.com/)
- [Policy Center](https://www.collibra.com/policies/)
