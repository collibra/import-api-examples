# Add complex relations


### Table of Contents  
1. [Preconditions](#preconditions)  
1. [Description](#description)
1. [Files](#files)

[NOTE]
=====
For an example of importing complex relations using the names of the legs (relations), see [example](../add-complex-relations-with-legs-by-names/README.md).
=====

<a name="preconditions"></a>
### Preconditions

Let’s assume that we have the following resources:
- Community with name DBs Community;
- Domain (Physical Data Dictionary) with name Physical Domain (located under DBs Community);
- Domain (Mapping Domain) with name My Mapping Domain (located under DBs Community);
- Assets (Columns) with names: DB_COL_SOURCE_1, DB_COL_SOURCE_2, DB_COL_TARGET_1, DB_COL_TARGET_1 (located under domain Physical Domain);
- Asset (Mapping Specification) with name MapSpec (located under domain My Mapping Domain).

The resources above can be created using [following json](precondition_for_complex_relations_creation.json)


<a name="description"></a>
### Description
In this example we're going to add:
- a complex relation (Field Mapping) linking mentioned assets and an attribute (Description);
- a mapping for the imported complex relation, for further reference (with external system and external entity ID as specified in the input).


<a name="files"></a>    
### Files
Files for described example can be found below:

- JSON input file [download JSON input file](complex-relations.json)
- JSON template (to be used for import from CSV/Excel) [download JSON template](complex-relations-template.json)
- CSV input file [download CSV input file](complex-relations.csv)
- Excel input file [download Excel input file](complex-relations.xlsx)


