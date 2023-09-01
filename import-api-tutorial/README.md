# Import API tutorial

This is a tutorial module that demonstrates how to use the Import and Synchronization APIs. This module has a series of
examples that you can run independently.

___

## Table of contents

1. [Project structure](#project-structure)
1. [Collibra developer terms](#collibra-developer-terms)
1. [Additional resources](#additional-resources)

## Project structure

This module has the following classes and resources:

### [Classes](src/main/java)

- [ImportJsonFileDemo1](src/main/java/com/collibra/importer/demo/ImportJsonFileDemo1.java) - Imports resources from a JSON
  file.
- [ImportCSVFileDemo2](src/main/java/com/collibra/importer/demo/ImportCSVFileDemo2.java) - Imports resources from a CSV
  file.
- [ImportExcelFileDemo3](src/main/java/com/collibra/importer/demo/ImportExcelFileDemo3.java) - Imports resources from
  an Excel file.
- [SynchronizeJsonFileDemo4](src/main/java/com/collibra/importer/demo/SynchronizeJsonFileDemo4.java) - Synchronizes
  imports from 2 batches and finalizes the synchronization.
- [SynchronizeRefreshDemo5](src/main/java/com/collibra/importer/demo/SynchronizeRefreshDemo5.java) - Synchronizes
  imports from 2 batches, finalizes it and sends a refresh of the synchronization. This class demonstrates how
  resources in Collibra can be either deleted or have their status updated using the Synchronization API.

### [Resources](src/main/resources)

- [Demo1_ImportJson.json](src/main/resources/Demo1_ImportJson.json) - Json file to be imported.
- [Demo2_ImportCSV.csv](src/main/resources/Demo2_ImportCSV.csv) - CSV file to be imported.
- [Demo3_ImportExcel.xlsx](src/main/resources/Demo3_ImportExcel.xlsx) - Excel file to be imported.
- [Demo_Template.json](src/main/resources/Demo_Template.json) - Template file used by CSV and Excel Imports.
- [Demo4_SynchronizeBatch1.json](src/main/resources/Demo4_SynchronizeBatch1.json) - Batch1 resources for
  Synchronization. Contains 3 assets.
- [Demo4_SynchronizeBatch2.json](src/main/resources/Demo4_SynchronizeBatch2.json) - Batch2 resources for
  Synchronization. Contains 2 assets.
- [Demo5_SynchronizeBatch1.json](src/main/resources/Demo5_SynchronizeBatch1.json) - Batch1 resources for Synchronization
  refresh. Contains 2 assets.
- [Demo5_SynchronizeBatch2.json](src/main/resources/Demo5_SynchronizeBatch2.json) - Batch2 resources for Synchronization
  Refresh. Contains 2 assets.
- [Import_API.postman_collection.json](src/main/resources/Import_API.postman_collection.json) - Postman collection for Import API.

## Collibra developer terms

This repository is part of the Collibra Developer Toolkit. By using or accessing the Developer Toolkit, you agree to
the [Collibra Developer Terms](https://www.collibra.com/developer-terms).

<a name="resources"></a>

## Additional resources

- [Collibra Developer Portal](https://developer.collibra.com/)
- [Collibra Documentation](https://community.collibra.com/documentation/)
- [Collibra](https://www.collibra.com/)
- [Policy Center](https://www.collibra.com/policies/)
