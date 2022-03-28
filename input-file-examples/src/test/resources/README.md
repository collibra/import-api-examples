# Input file examples

To help you get started with the Collibra Import API and understand
the data and structural requirements of the input files, you can use these examples
to create and update mock data in your Collibra Platform.

Each example has the following files:
- Json file
- CSV file
- Excel file
- JSON template for CSV and Excel files

>**Note:** You must be [authenticated](https://university.collibra.com/developer-tutorials/rest-api-authentication/)
as a user with a role that has sufficient permissions to add, update or remove the imported resources.

## Import from a JSON file

```bash
curl -X POST 'https://<your_collibra_platform_url>/rest/2.0/import/json-job' \
-H 'accept: application/json' \
-H 'Content-Type: multipart/form-data' \
-F 'fileName=import_file' \
-F 'file=@<path_to_input_file>.json'
```

## Import from CSV or Excel files
>**Note:** To use the CSV and Excel files, you must also provide the contents
of the JSON template as a string `template` parameter.

Example for a CSV file:

```bash
curl -X POST 'https://<your_collibra_platform_url>/rest/2.0/import/csv-job' \
-H 'accept: application/json' \
-H 'Content-Type: multipart/form-data' \
-F 'fileName=import_file' \
-F 'headerRow=true' \
-F 'file=@<path_to_input_file>.csv' \
-F 'template=<json_template_contents>'
```

## Examples:
 1. [Add community, domain, assets with relations](add-community-domain-with-tables-columns/README.md)
 1. [Add mappings for assets](add-mappings-for-assets/README.md)
 1. [Add assets with implicit creation of mappings](add-assets-with-implicit-mappings/README.md)
 1. [Change asset using its mapping for identification](change-assets-identified-by-mappings/README.md)
 1. [Change assets](change-assets/README.md)
 1. [Change assets identified by mappings with external ids](change-assets-identified-by-mappings-external/README.md)
 1. [Add community, domain, assets with attributes](add-community-domain-asset-with-attributes/README.md)
 1. [Add community, domain, assets with multi-value attributes](add-community-domain-asset-with-multivalue-attributes/README.md)
 1. [Add community, domain, assets with source/target relations](add-community-domain-assets-relations/README.md)
 1. [Add community, domain, assets with relations, identified by names](add-community-domain-assets-relations-by-names/README.md)
 1. [Add community, domain, assets with responsibilities](add-community-domain-assets-with-responsibilities/README.md)
 1. [Add community, Domain, Assets with Tags](add-community-domain-assets-with-tags/README.md)
 1. [Add complex relations](add-complex-relations/README.md)
 1. [Add complex relations, with legs identified by names](add-complex-relations-with-legs-by-names/README.md)
 1. [Remove assets](remove-assets/README.md)
 1. [Remove assets with implicit mappings removal](remove-assets-with-mappings/README.md)

## Collibra developer terms

This repository is part of the Collibra Developer Toolkit. By using or accessing
the Developer Toolkit, you agree to the [Collibra Developer Terms](https://www.collibra.com/developer-terms).

## Additional resources

- [Collibra Developer Portal](https://developer.collibra.com/)
- [Collibra Documentation](https://community.collibra.com/documentation/)
- [Collibra](https://www.collibra.com/)
- [Policy Center](https://www.collibra.com/policies/)
