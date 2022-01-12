# ODH Webcomponents

> FRONTEND

- [ODH Webcomponents](#odh-webcomponents)
	- [Source Code](#source-code)
	- [Requirements](#requirements)
	- [Configuration](#configuration)
		- [Mode](#mode)
		- [Configuration](#configuration-1)
			- [ReCaptcha](#recaptcha)
			- [API](#api)
	- [Local Development with Docker](#local-development-with-docker)
	- [Build Setup](#build-setup)
	- [Adding pages](#adding-pages)
	- [Deployment](#deployment)
		- [Single Page Application](#single-page-application)
		- [Universal Mode](#universal-mode)
	- [Create new static content pages](#create-new-static-content-pages)
	- [Embed a video in a static content page](#embed-a-video-in-a-static-content-page)
	- [Create a new banner](#create-a-new-banner)

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
* ODH Web Component Configurator: https://github.com/noi-techpark/webcomp-configtool.git
  > NB: If you update `webcomp-configtool`, you need to tag that master branch and update
  > the `package.json` of this repository accordingly.
  > See "odh-web-components-configurator": "https://github.com/noi-techpark/webcomp-configtool.git#v0.9"
  > and put the latest version tag to the end

## Configuration

> nuxt.config.js

### Mode

```
mode: 'universal|spa'
```

Change mode to either 'universal' or 'spa'.

Universal mode supports server-side-rendering (ssr) but requires node.js to run.

Spa mode (single-page-application) only requires node.js to build the project.

### Configuration

This can be done through a `.env` file. Copy `.env.example` to `.env` and adjust
the values as you like.

#### ReCaptcha

You must configure a ReCaptcha API key that is valid for the domain your
frontend is exposed on. You can optain such a key here:
https://www.google.com/recaptcha/admin/create

See [VUE reCaptcha module](https://www.npmjs.com/package/vue-recaptcha) for more
details.

IMPORTANT: Only captcha keys of Version2 are supported.

Key inside `.env` is `RECAPTCHA_PUBLIC_KEY`.

#### API

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

# To know on which address/port the application is listening to, run...
$ docker-compose logs

# If you like, you can enter the docker container to debug or run commands you like
$ docker-compose run --rm app bash

# Stop the docker instance
$ docker-compose stop app
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

See [Nuxt i18n](https://nuxt-community.github.io/nuxt-i18n/basic-usage.html) if
you need translation.

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

You will probably need a reverse proxy to add https and proxy the application
from port 3000 to port 443 on your domain.

## Create new static content pages

1. Create a `.vue` file under the path inside `pages` where you want to create a
   new page. The directory structure corresponds to the structure of the url.
2. Create a `.md` file with the same name in the same directory
3. Import the md file in your vue file, for example:
```js
import legally from './legally.md';
```
4. Create a computed property with the content of the md file
```js
  computed: {
    md()
	{
      return legally;
    };
  };
```
5. Import the markdown page component
```js
import MarkdownPage from '~/components/markdown-page';
```
6. Include the markdown page component inside the html code and pass the computed property
```html
<markdown-page :content="md"></markdown-page>
```
7. Update the menues
   - Go to `frontend/src/layouts/partials/header.vue` and see the code there for
     how to do it, if you want to have it inside the main menue, which is only
     visible for desktop web page layouts
   - Go to `frontend/src/layouts/partials/nav-menu.vue` and see the code there.
     Some menue items are only visible for mobile devices, if the class
     `mobile-only` is present.
   - In general make sure that items are not presented twice in the header and
     nav-menu aka burger.

## Embed a video in a static content page

Include the following code and replace the url of your video inside the url parameter

```html
    <video-player
      url=""
    ></video-player>
```

## Create a new banner

1. Open `src/components/banner.vue`
2. Add a new banner slide tag
```html
<banner-slide><banner-slide>
```
3. Customize the banner with tag parameters
- "title": Title of the banner
- "subtitle": Subtitle below the title
- "imgUrl": Url of the background image
- "path": Path to the page inside the a tag
- "linkText": Text of the link
4. Example
```html
      <banner-slide
        img-url="https://picsum.photos/1024/480/?image=52"
        title="Image"
        subtitle="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
          eros felis, tincidunt a tincidunt eget, convallis vel est. Ut
          pellentesque ut lacus vel interdum."
        link-text="learn more"
        path="/contact"
      ></banner-slide>
```
