# Change asset using its mapping for identification

### Table of Contents  
1. [Preconditions](#preconditions)  
1. [Description](#description)
1. [Files](#files)
1. [Additional information](#additional-information)


<a name="preconditions"></a>
### Preconditions
- To execute following example there is a need to import file with assets and mappings before.
Import files can be found in example: [Add assets with implicit creation of mappings](../add-assets-with-implicit-mappings/README.md)


<a name="description"></a>
### Description
In this example we're going to change:

- table TABLE_2 will be renamed to TABLE_2_UPDATED (impacts full name only);

- column TAB_1_COLUMN_2 will be renamed to TAB_2_COLUMN_2_UPDATED (impacts full name only);

- column TAB_2_COLUMN_2_UPDATED will be linked to table TABLE_2_UPDATED from now on.


<a name="files"></a>
### Files
Files for described example can be found below:

- JSON input file [download JSON input file](assets-with-implicit-mappings-updated.json)
- JSON template (to be used for import from CSV/Excel) [download JSON template](assets-with-implicit-mappings-updated-template.json)
- CSV input file [download CSV input file](assets-with-implicit-mappings-updated.csv)
- Excel input file [download Excel input file](assets-with-implicit-mappings-updated.xlsx)


<a name="additional-information"></a>
### Additional information
Keep in mind that the unchanged resources will not be included in the operation summary as they were not changed.

Depending on the chosen operation (import or synchronization), there will be differences in terms of performance due 
to the existence of Update Discovery feature in Synchronization API. If both of the mentioned examples were executed 
using the Synchronization API, the resources that stayed intact (e.g. community or domain) are not included in further 
processing for optimisation purposes (thanks to Update Discovery process).
