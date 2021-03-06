# Add community, domain, assets with source/target relations

### Table of Contents  
1. [Preconditions](#preconditions)  
1. [Description](#description)
1. [Files](#files)

---
**NOTE**

For an example of importing relations using the names of the relation types, see [example](../add-community-domain-assets-relations-by-names/README.md).

---

<a name="preconditions"></a>
### Preconditions
- Listed resources below have not existed before.


<a name="description"></a>
### Description 
In this example we're going to add:
- Community named _DBs Community_;
- Domain named _Physical Domain_ which is located under _DBs Community_;
- Add asset _DB_SCHEMA_ without relations;
- Add asset _DB_TABLE_ with source relation to _USERS_SCHEMA_;
- Add asset _DB_COLUMN_ with target relation to _USERS_TABLE_.


<a name="files"></a>
### Files
Files for described example can be found below:

- JSON input file [download JSON input file](relations.json)
- JSON template (to be used for import from CSV/Excel) [download JSON template](relations-template.json)
- CSV input file [download CSV input file](relations.csv)
- Excel input file [download Excel input file](relations.xlsx)

