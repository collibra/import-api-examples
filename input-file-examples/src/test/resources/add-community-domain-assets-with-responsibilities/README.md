# Add community, domain, asset with responsibilities

### Table of Contents  
1. [Preconditions](#preconditions)  
1. [Description](#description)
1. [Files](#files)


<a name="preconditions"></a>
### Preconditions
- a user identified by ID _e060bb6e-df51-466f-903f-af08142c2e9a_
- a user with user name _john.doe_
- a user group identified by ID _471f5852-1d19-4d74-a70f-989100c3c878_
- a user group with name _Data Citizens_

- Listed below Community, Domain and Asset have not existed before.


<a name="description"></a>
### Description 
In this example we're going to add:
- Community named _Dbs Community_;
- Domain named _Physical Domain_ which is located under _Dbs Community_;
- Asset named _DB_SCHEMA_ and assigns following roles for this asset:
    - Business Steward role is assigned to:
        - a user with user name _john.doe_;
        - a user group identified by ID _471f5852-1d19-4d74-a70f-989100c3c878_;
        - a user group with name _Data Citizens_;
    - Reviewer role (identified by ID 00000000-0000-0000-0000-000000005032) is assigned to:
        - a user identified by ID _e060bb6e-df51-466f-903f-af08142c2e9a_.
 
 
 
<a name="files"></a>
### Files
Files for described example can be found below:

- JSON input file [download JSON input file](responsibilities.json)
- JSON template (to be used for import from CSV/Excel) [download JSON template](responsibilities-template.json)
- CSV input file [download CSV input file](responsibilities.csv)
- Excel input file [download Excel input file](responsibilities.xlsx)
