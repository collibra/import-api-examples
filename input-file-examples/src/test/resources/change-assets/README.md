# Change assets

### Table of Contents  
1. [Preconditions](#preconditions)  
1. [Description](#description)
1. [Files](#files)
1. [Additional Information](#additional-information)


<a name="preconditions"></a>
### Preconditions
- To execute following example there is a need to import file with assets before.
Import files can be found in example:  [Add community, domain, assets with relations](../add-community-domain-with-tables-columns/README.md)


<a name="description"></a>
### Description
In this example we're going to change:

- schema SCHEMA_1 will have display name renamed to SCHEMA (full name is not impacted);

- table TABLE_1 will have display name renamed to T1 (full name is not impacted);

- table TABLE_2 will have display name renamed to T2 (full name is not impacted);

- column TAB_1_COLUMN_1 will have display name renamed to C1 (full name is not impacted);

- column TAB_1_COLUMN_2 will have display name renamed to C2 (full name is not impacted);

- column TAB_2_COLUMN_1 will have display name renamed to C3 (full name is not impacted).


<a name="files"></a>
### Files
Files for described example can be found below:

- JSON input file [download JSON input file](schema-tables-columns-updated.json)
- JSON template (to be used for import from CSV/Excel) [download JSON template](schema-tables-columns-updated-template.json)
- CSV input file [download CSV input file](schema-tables-columns-updated.csv)
- Excel input file [download Excel input file](schema-tables-columns-updated.xlsx)


<a name="additional-information"></a>
### Additional information
Keep in mind that the unchanged resources will not be included in the operation summary as they were not changed.

Depending on the chosen operation (import or synchronization), there will be differences in terms of performance due 
to the existence of Update Discovery feature in Synchronization API. If both of the mentioned examples were executed 
using the Synchronization API, the resources that stayed intact (e.g. community or domain) are not included in further 
processing for optimisation purposes (thanks to Update Discovery process).


