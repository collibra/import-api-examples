## Import API Java Client

The repository contains plugins for generating API Clients for the Import and Core APIs. It also helps to generate POJO classes for Template schemas, used by the Import API 
to import CSV and Excel files.

___
1. `$ git clone https://github.com/collibra/import-api-examples.git`
1. Edit [config.properties](src/main/resources/config.properties) in **src/main/resources**
1. `$ gradle clean assemble` to compile the code and generate the required Import API clients and POJO classes.
1. `$ gradle clean build` to compile the code and run the unit tests in Import-api-java-client.
___

## Table of contents

1. [Project structure](#project-structure)
1. [Project Configuration](#project-configuration)
1. [Test](#tests)   
1. [Collibra developer terms](#collibra-developer-terms)
1. [Additional resources](#additional-resources)

<a name="project-structure"></a>
## Project structure

This project uses Gradle as a dependency management tool (Kotlin DSL). The project also uses plugins for generating:
- An Importer API client based on [dgc-importer-rest.json](schemas/dgc-importer-rest.json) using the `openApiGenerate` gradle task
- A Core API client based on [dgc-rest.json](schemas/dgc-rest.json) using the `buildCoreRestClient` gradle task
- POJO classes describing template parameters used to import CSV files based on [schema.json](schemas/schema.json) using the `generateJsonSchema2Pojo` Gradle task.

The dependency `openapi-generator-gradle-plugin` was used to generate the Open API client.
Additionally, the generated code was marked as a source set to make the generated classes detectable within the **data-quality**
project.

The dependency `jsonschema2pojo-gradle-plugin` was used to generate the POJO classes describing the **template** parameter used to import CSV and Excel files.

See [build.gradle.kts](build.gradle.kts) for more information.


<a name="project-configuration"></a>
## Project configuration

The configuration properties are located in the [**config.properties**](src/main/resources/config.properties) file.
All properties are required to access the Collibra Platform and run the DataQuality tests and DataImporterTest.

**Configuration to set up your Collibra environment**

Parameter | Description | Example
--- | --- | ---
collibra.url | The base URL of your Collibra environment.  | https://collibra-example.com
collibra.username | The user name to be used by the API clients. | john.doe
collibra.password | The password to be used by the API clients. | password

<a name="tests"></a>
## Tests

- To run the health check for the project configuration, execute the
[ImportHealthCheckTest](src/test/java/com/collibra/importer/client/ImportHealthCheckTest.java) test.
- To test the configuration and import function, invoke `gradle test`.
  
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
