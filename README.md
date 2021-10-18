# Collibra Import API tutorial and examples

The Collibra Import API enhances your experience with data by giving you the tools to integrate your systems with the Collibra Platform. This project is meant both to show you the basics of building an import-based integration application and to get you started with your project. 
___
1. `$ git clone https://github.com/collibra/import-api-examples.git`
2. Fill in required properties (collibra.url, collibra.username, collibra.password) in [config.properties](import-api-java-client/src/main/resources/config.properties) in **import-api-java-client\src\main\resources**
3. `$ ./gradlew build` to compile the code in all the 3 modules and to run the unit tests.<br/>Add `-PIGNORE_TEST_FAILURES` if you don't have a running dgc instance, but want to generate the client anyway.
___

This repository has the following sub-modules:

- An [import-api-java-client](import-api-java-client/README.md) that helps you generate clients for the Import API.
- An [input-file-examples](input-file-examples/README.md) project that demonstrates how to import communities, domains,
  assets, relations, etc from CSV, Excel and JSON files.
- An [import-api-tutorial module](import-api-tutorial/README.md) that demonstrates how to use the Import and
  Synchronization APIs with series of examples.

## Collibra developer terms

This repository is part of the Collibra Developer Toolkit. By using or accessing the Developer Toolkit, you agree to
the [Collibra Developer Terms](https://www.collibra.com/developer-terms).

The code provided in this repository is available under the [following license](LICENSE.md). 
## Additional resources

- [Collibra Developer Portal](https://developer.collibra.com/)
- [Collibra Documentation](https://community.collibra.com/documentation/)
- [Collibra](https://www.collibra.com/)
- [Policy Center](https://www.collibra.com/policies/)
