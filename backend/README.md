# ODH: Webcomponents

Webcomponents store for OpenDataHub.

## Table of contents

- [Gettings started](#getting-started)
- [Building and Testing](#building-and-testing)
- [Configuration](#configuration)
- [Deployment](#deployment)
- [Docker environment](#docker-environment)
- [Information](#information)

## Getting started

These instructions will get you a copy of the project up and running
on your local machine for development and testing purposes.

### Prerequisites

To build the project, the following prerequisites must be met:

- Java JDK 11 or higher (e.g. [OpenJDK](https://openjdk.java.net/))
- [Maven](https://maven.apache.org/) 3.x

To run the project, the following prerequisites must be met:

- Java JDK 11 or higher (e.g. [OpenJDK](https://openjdk.java.net/))
- Tomcat webserver OR local IDE (eg. [IntelliJ](https://www.jetbrains.com/))
- PostgreSQL 9.6+

### Source code

Get a copy of the repository:

```bash
git clone https://github.com/noi-techpark/odh-web-components-store.git
```

Change directory:

```bash
cd odh-web-components-store/backend/
```

### Project Structure

The backend system is divided in four modules.

The 'common' module holds code that is shared between all parts of the application.

'crawler-service', 'data-service' and 'delivery-service' are distinct spring boot applications.

## Building and Testing

### Build

Build the project:

```bash
mvn clean install
```

### Running tests

The unit tests can be executed with the following command:

```bash
mvn clean test
```

## Configuration

Each service application comes with three profiles that can be configured.

> src/main/resources/application[-profile].yml 

You will have to configure the 'datasource' property for each application.

There are some specific settings in the 'application' section of the configuration that might need customization:

> crawler-service 

```
application.repoistory.github.token
```
enter your github token to bypass the 60 calls/hour limit

```
application.workspace.path
```
path to the local file system where the crawler will store the "dist" files.

> delivery-service 

```
application.workspace.path
```
path to the local file system where the crawler has stored the "dist" files

## Deployment

The project requires a Tomcat server as well as a PostgreSQL 9.6+ database server.

Build the project

```bash
mvn clean package
```

There are three services that need to be deployed.

You will find the .war files to deploy in the 'target' folder of each service.

For an initial setup deploy 'data-service' first to have the database populated with tables.

## Docker environment

For local development you can use docker to run a PostgreSQL server.

### Installation

Install [Docker](https://docs.docker.com/install/) (with Docker Compose) locally on your machine.

### Start and stop the containers

Before start working you have to start the Docker containers:

```
docker-compose up --build --detach
```

After finished working you can stop the Docker containers:

```
docker-compose stop
```

## Information

### Support

For support, please contact [info@opendatahub.bz.it](mailto:info@opendatahub.bz.it).

### Contributing

If you'd like to contribute, please follow the following instructions:

- Fork the repository.

- Checkout a topic branch from the `development` branch.

- Make sure the tests are passing.

- Create a pull request against the `development` branch.

### Documentation

More documentation can be found at [https://opendatahub.readthedocs.io/en/latest/index.html](https://opendatahub.readthedocs.io/en/latest/index.html).

### License

The code in this project is licensed under the GNU AFFERO GENERAL PUBLIC LICENSE Version 3 license. See the [LICENSE.md](LICENSE.md) file for more information.
