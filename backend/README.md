# ODH: Webcomponents

Webcomponents store for OpenDataHub.

## Table of contents

- [Gettings started](#getting-started)
- [Building and Testing](#building-and-testing)
- [Configuration](#configuration)
- [Deployment](#deployment)
- [API & swagger](#api--swagger)
- [General Usage](#general-usage)
- [Docker environment](#docker-environment)
- [Information](#information)

## Getting started

These instructions will get you a copy of the project up and running
on your local machine for development and testing purposes.

### Prerequisites

To build the project, the following prerequisites must be met:

- Java JDK 8 or higher (e.g. [OpenJDK](https://openjdk.java.net/))
- [Maven](https://maven.apache.org/) 3.x

To run the project, the following prerequisites must be met:

- Java JDK 8 or higher (e.g. [OpenJDK](https://openjdk.java.net/))
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
application.repository.origin.url
application.repository.origin.branch
```
repository where the master list for webcomponents can be found. must use https and not ssh

```
application.workspace.path
```
path to the local file system where the crawler will store the "dist" files.

> delivery-service 

```
application.workspace.path
```
path to the local file system where the crawler has stored the "dist" files

> data-service 

```
application.workspace.path
```
path to the local file system where the crawler has stored the "dist" files

```
application.deliveryBaseUrl
```
url where the frontend can reach the delivery service to load dist files

## Deployment

The project requires a Tomcat server as well as a PostgreSQL 9.6+ database server.

Build the project

```bash
mvn clean package
```

If you want to indivually build each module, you have to make sure that the common module is built first. eg:
```bash
cd common
mvn clean install
cd data-service
mvn clean package
```

There are three services that need to be deployed.

You will find the .war files to deploy in the 'target' folder of each service.

For an initial setup deploy 'data-service' first to have the database populated with tables.

## API & swagger

> data-service

data-service will expose the main api for webcomponents. it will also expose a swagger-ui (/swagger-ui.html)

> delivery-service

delivery-service will expose api for retrieving the dist files needed to embed webcomponents. it will also expose a swagger-ui (/swagger-ui.html)

## General Usage

### Adding a webcomponent

Adding a new webcomponent requires an entry in the main json file. This file can be found in the repository configured as "application.repository.origin.url".

> origins.json

1) Use a UUID-generator to create a new id
2) Create a new entry in the origins file referencing the repository of the webcomponent

The repository of the new webcomponent must contain a wcs-manifest.json file!

### wcs-manifest.json

To register with the webcomponents hub a wcs-manifest.json file must be in your repository's root directory. A manifest file should look like this:

```
{
  "title": "Generic Map",
  "description": "Generic Map to access the Open Data Hub Mobility API v2 (with Ninja)",
  "descriptionAbstract": "Generic Map to access the Open Data Hub Mobility",
  "license": "GPL-3.0",
  "authors": [
    {
      "name": "NOI",
      "email": "info@noi.bz.it",
      "organization": "NOI S.p.A",
      "organizationUrl": "https://noi.bz.it"
    }
  ],
  "image": "wcs-logo.png",
  "searchTags": ["map"],
  "dist": {
    "basePath": "dist",
    "files": [
      "map_widget.min.js"
    ]
  },
  "configuration": {
    "tagName": "map-widget",
    "options": [
      {
        "key": "domain",
        "type": "select",
        "label": "Domain",
        "options": {
          "values": ["mobility", "tourism"],
          "default": "mobility"
        }
      },
      {
        "key": "station-types",
        "type": "multiselect",
        "label": "Station Types",
        "options": {
          "values": ["CreativeIndustry","EChargingStation","EChargingPlug"],
          "default": []
        }
      }
    ]
  }
}
```

The configuration section of the manifest is needed for the configurator to know what options to include.
```
"configuration": {
    "tagName": "map-widget",
    "options": [
      {
        "key": "singleSelect",
        "type": "select",
        "label": "a single select",
        "options": {
          "values": ["optionA", "optionB"],
          "default": "optionA"
        }
      },
      {
        "key": "multiselectField",
        "type": "multiselect",
        "required": true,
        "options": {
          "label": "a multiselect field",
          "values": ["optionA","optionB","optionC"],
          "default": []
        }
      },
      {
        "key": "sample-A",
        "type": "text",
        "required": false,
        "options": {
          "default": "Test String"
        }
      },
      {
        "key": "sample-B",
        "type": "textarea",
        "required": false,
        "options": {
          "default": "A multi-\nLine Text"
        }
      },
      {
        "key": "sample-C",
        "type": "number",
        "required": false,
        "options": {
          "default": "342",
          "min": 0,
          "max": 600,
          "step": 1
        }
      },
      {
        "key": "sample-D",
        "type": "number",
        "required": false,
        "options": {
          "default": "34.2",
          "min": 0,
          "max": 60.04,
          "step": 0.01
        }
      },
      {
        "key": "booleanField",
        "type": "bool",
        "required": false,
        "options": {
          "label": "a boolean field",
          "default": false"
        }
      },
      {
        "key": "sample-F",
        "type": "object",
        "required": false,
        "options": {
          "default": {}
        }
      }
    ]
  }
```

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
