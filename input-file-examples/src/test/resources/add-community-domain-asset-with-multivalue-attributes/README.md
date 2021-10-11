# Add community, domain, asset with multi-value attributes

### Table of Contents  
1. [Preconditions](#preconditions)  
1. [Description](#description)
1. [Files](#files)


<a name="preconditions"></a>
### Preconditions
- Asset Type "Table" has multi-value attribute type assigned with name "indexes". 
  In order to achieve that you have to perform following actions in the application:
    - add new attribute type with name = "indexes", kind = "MULTI_VALUE_LIST", allowed values = ["surname", "name", "id"]
    - assign created attribute type to the asset type with name "Table"
    
- Listed resources below have not existed before.


<a name="preconditions"></a>
### Description
In this example we're going to add:
- Community named _DBs Community_;
- Domain named _Physical Domain_ which is located under _DBs Community_;
- Asset named _DB_TABLE_ with following multi-value attribute:
    - indexes.
    
    
<a name="files"></a>    
### Files
Files for described example can be found below:

- JSON input file [download JSON input file](multivalue-attributes.json)
- JSON template (to be used for import from CSV/Excel) [download JSON template](multivalue-attributes-template.json)
- CSV input file [download CSV input file](multivalue-attributes.csv)
- Excel input file [download Excel input file](multivalue-attributes.xlsx)
