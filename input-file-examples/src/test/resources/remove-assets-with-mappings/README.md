# Remove assets with implicit mappings removal

### Table of Contents  
1. [Preconditions](#preconditions)  
1. [Description](#description)
1. [Files](#files)
1. [Additional information](#additional-information)


<a name="preconditions"></a>
### Preconditions
- To execute following example there is a need to import file with assets and implicit creation of mappings,
file can be found here: [Add assets with implicit creation of mappings](../add-assets-with-implicit-mappings/README.md)


<a name="description"></a>
### Description
In this example we're going to remove:

- table _TABLE_1_ (linked mapping will also be removed);
- column _TAB_1_COLUMN_1_ (linked mapping will also be removed);
- column _TAB_1_COLUMN_2_ (linked mapping will also be removed).


<a name="files"></a>
### Files
Files for described example can be found below:

- JSON input file [download JSON input file](assets-with-implicit-mappings-removal.json)
- JSON template (to be used for import from CSV/Excel) [download JSON template](assets-with-implicit-mappings-removal-template.json)
- CSV input file [download CSV input file](assets-with-implicit-mappings-removal.csv)
- Excel input file [download Excel input file](assets-with-implicit-mappings-removal.xlsx)


<a name="additional-information"></a>
### Additional information
The removal operation (and as a result, this example) is currently relevant only for the Synchronization API.



