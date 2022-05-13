# Open Data Hub: Web Component Store

This repository contains the source code of the Open Data Hub Web Component
Store. It is a store to collect and preview our web components. 

[![CI/CD data-service (api)](https://github.com/noi-techpark/it.bz.opendatahub.webcomponents/actions/workflows/data-service.yml/badge.svg)](https://github.com/noi-techpark/it.bz.opendatahub.webcomponents/actions/workflows/data-service.yml)
[![CI/CD delivery-service (cdn)](https://github.com/noi-techpark/it.bz.opendatahub.webcomponents/actions/workflows/delivery-service.yml/badge.svg)](https://github.com/noi-techpark/it.bz.opendatahub.webcomponents/actions/workflows/delivery-service.yml)
[![CI/CD frontend](https://github.com/noi-techpark/it.bz.opendatahub.webcomponents/actions/workflows/frontend.yml/badge.svg)](https://github.com/noi-techpark/it.bz.opendatahub.webcomponents/actions/workflows/frontend.yml)

Functionalities are as follows: 
  - Search 
  - Filter
  - Configure and preview
  - Copy a code snippet to be integrated in your web page 

In addition, it connects the source code of each web component to the actual
representation. 

This repository is divided into two sub-projects, namely:
  - [frontend](frontend/README.md)
  - [backend](backend/README.md)

## Local webcomponent development
To test your webcomponent locally you can start an instance of the store on your local machine and deploy your created webcomponent to it.  

To start it follow these steps:
- `cp .env.example .env`
- Adjust .env with your values for WC_PATH (the absolute path of you webcomponent)
- Adjust ports in .env if they have conflicts with services already running on your machine
- `docker-compose up -d`
- Wait until the containers are running
- Access the store on `localhost:8999` (or the port you defined)

To stop everything:
- `docker-compose stop`
- `rm -f workspace` (in case you want to delete your test)
