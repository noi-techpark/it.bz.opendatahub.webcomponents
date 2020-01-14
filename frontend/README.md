# ODH Webcomponents

> FRONTEND

## Table of contents

- [Source Code](#source-code)
- [Requirements](#requirements)
- [Configuration](#configuration)
- [Local Development with Docker](#local-development-with-docker)
- [Build Setup](#build-setup)
- [Deployment](#deployment)

## Source Code

Get a copy of the repository:

```bash
git clone https://github.com/noi-techpark/odh-web-components-store.git
```

Change directory:

```bash
cd odh-web-components-store/frontend/
```

## Requirements

* node.js
* yarn

## Configuration

> nuxt.config.js

### Mode

```
mode: 'universal|spa'
```

Change mode to either 'universal' or 'spa'.

Universal mode supports server-side-rendering (ssr) but requires node.js to run.

Spa mode (single-page-application) only requires node.js to build the project.

### API

```
axios: {
    baseURL: 'http://localhost:9030'
}
```

Adjust the baseURL to where the API is running. The API is provided by "data-service" in the 'backend' package.

## Local Development with Docker
We use [docker-compose](https://docs.docker.com/compose/) for local development.

```bash
# Change into directory
$ cd odh-web-components-store/frontend/

# Start up the application
$ docker-compose up

# If you like, you can enter the docker container to debug or run commands you like
$ docker-compose run --rm app bash
```

See [Build Setup](#build-setup) for possible commands.

## Build Setup

``` bash
# install dependencies
$ yarn install

# serve with hot reload at localhost:3000
$ yarn dev

# build for production and launch server
$ yarn build
$ yarn start

# generate static project
$ yarn generate
```

For detailed explanation on how things work, check out [Nuxt.js docs](https://nuxtjs.org).

## Adding pages

You can easily add new pages by creating them in the /src/pages/ folder.
[See Nuxt documentation](https://nuxtjs.org/guide/routing)

See [Nuxt i18n](https://nuxt-community.github.io/nuxt-i18n/basic-usage.html) if you need translation.

## Deployment

Chose one of the following ways depending on the 'mode' set during configuration

### Single Page Application

```
# install dependencies and build the project
$ yarn install
$ yarn build
```

Copy the generated files in the 'dist' folder to your webroot.

Serve the files with a webserver like nginx or apache.

### Universal Mode

```
# install dependencies and build the project
$ yarn install
$ yarn build

# start the server
$ yarn start
```

You will probably need a reverse proxy to add https and proxy the application from port 3000 to port 443 on your domain.
