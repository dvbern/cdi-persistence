# cdi-persistence

This library provides an abstraction of the entity manager and can be used to make instances managed and persistent.

## Getting Started

These instructions will get you an overview on how to implement and use the cdi-persistence library. See further down for installing or deployment notes.

### Usage

<What things you need to install the software and how to install them>

1. Add the following dependencies to your project:
```
<dependency>
	<groupId>ch.dvbern.oss.cdipersistence</groupId>
	<artifactId>cdi-persistence-api</artifactId>
	<version>1.0</version>
</dependency>
<dependency>
	<groupId>ch.dvbern.oss.cdipersistence</groupId>
	<artifactId>cdi-persistence-test</artifactId>
	<version>1.0</version>
	<scope>test</scope>
</dependency>
```
2. Write your PersistenceService, implementing the Interface Persistence

3. The persistence.xml found in the test-sources of the cdi-persistence-test-Module must be copied to the test-sources of your project as it references the (project specific) orm.xml (Adapt path if necessary)

4. Do not change the attribute "javax.persistence.jdbc.url" as it es referenced by the ch.dvbern.lib.inmemorypersistence.TestPersistenceManager.

5. Inject Persistence in your Tests: Your entities will be saved in a in-memory HSQLDB that will be cleared after each test.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Contributing Guidelines

Please read [Contributing.md](CONTRIBUTING.md) for the process for submitting pull requests to us.

## Code of Conduct

One healthy social atmospehere is very important to us, wherefore we rate our Code of Conduct high. For details check the file [CodeOfConduct.md](CODE_OF_CONDUCT.md)

## Authors

* **DV Bern AG** - *Initial work* - [dvbern](https://github.com/dvbern)

See also the list of [contributors](https://github.com/dvbern/cdi-persistence/contributors) who participated in this project.

## License

This project is licensed under the Apache 2.0 License - see the [License.md](LICENSE.md) file for details.

