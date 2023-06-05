<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div class="bg-light">
    <div class="container p-4 mb-5">
      <h1>Getting Started</h1>

      To register with the webcomponents hub a wcs-manifest.json file must be in
      your repository's root directory. A manifest file should look like this:
      <p />
      <pre>
{
  "title": "Generic Map",
  "description": "Generic Map to access the Open Data Hub Mobility API v2 (with Ninja)",
  "descriptionAbstract": "Generic Map to access the Open Data Hub Mobility",
  "license": "GPL-3.0",
  "copyrightHolders": [
    {
      "name": "NOI",
      "email": "info@noi.bz.it",
      "organization": "NOI S.p.A",
      "organizationUrl": "https://noi.bz.it"
    }
  ],
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
      </pre>

      <h2>Configuration</h2>
      The configuration section of the manifest is needed for the configurator
      to know what options to include.
      <p />
      Here are some examples of what a configuration may include:
      <p />
      <pre>
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
      </pre>
    </div>
  </div>
</template>
