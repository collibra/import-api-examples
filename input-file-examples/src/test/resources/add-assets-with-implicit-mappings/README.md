# Add assets with implicit mappings

### Table of Contents  
1. [Preconditions](#preconditions)  
1. [Description](#description)
1. [Files](#files) 

 
<a name="preconditions"></a>
### Preconditions
- Listed resources below have not existed before.


<a name="description"></a>
### Description
In this example we're going to add:
- Community named _DBs Community_;
- Domain named _Physical Domain_ which is located under _DBs Community_;
- Assets named _SCHEMA_1, TABLE_1, TABLE_2, TAB_1_COLUMN_1, TAB_1_COLUMN_2, TAB_2_COLUMN_1_ which are located under _Physical Domain_;
- Relations:
    - _SCHEMA_1 contains TABLE_1_;
    
    - _SCHEMA_1 contains TABLE_2_;
    
    - _TAB_1_COLUMN_1 is part of TABLE_1_;
    
    - _TAB_1_COLUMN_2 is part of TABLE_1_;
    
    - _TAB_2_COLUMN_1 is part of TABLE_2_;
    
- Mappings for assets:
    - _(EXT_SYSTEM, EXT_SCHEMA_1_ID) → SCHEMA_1_;

    - _(EXT_SYSTEM, EXT_TABLE_1_ID) → TABLE_1_;

    - _(EXT_SYSTEM, EXT_TABLE_2_ID) → TABLE_2_;

    - _(EXT_SYSTEM, EXT_TABLE_1_COLUMN_1_ID) → TAB_1_COLUMN_1_;

    - _(EXT_SYSTEM, EXT_TABLE_1_COLUMN_2_ID) → TAB_1_COLUMN_2_;

    - _(EXT_SYSTEM, EXT_TABLE_2_COLUMN_1_ID) → TAB_2_COLUMN_1_.
    
    
<a name="files"></a>    
### Files 
Files for described example can be found below:

- JSON input file [download JSON input file](assets-with-implicit-mappings.json)
- JSON template (to be used for import from CSV/Excel) [download JSON template](assets-with-implicit-mappings-template.json)
- CSV input file [download CSV input file](assets-with-implicit-mappings.csv)
- Excel input file [download Excel input file](assets-with-implicit-mappings.xlsx)    
