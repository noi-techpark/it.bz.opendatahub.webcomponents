<template>
  <div class="bg-light">
    <div class="container p-4 mb-5">
      <h1>Getting Started</h1>

      To register with the webcomponents hub a wcs-manifest.json file must be in
      your repoistory's root directory. A manifest file should look like this:
      <p />
      <pre>
{
  "title": "Generic Map",
  "description": "Generic Map to access the Open Data Hub Mobility API v2 (with Ninja)",
  "descriptionAbstract": "Generic Map to access the Open Data Hub Mobility",
  "license": "n/a",
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
  "dist": [
    "dist/map_widget.min.js"
  ],
  "configuration": {
    "tagName": "map-widget",
    "options": [
      {
        "key": "domain",
        "type": "select",
        "options": {
          "values": ["mobility", "tourism"],
          "default": "mobility"
        }
      },
      {
        "key": "station-types",
        "type": "multiselect",
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
      <div class="font-weight-bold">warning: this is still WIP!</div>
      <p />
      Here are some examples of what a configuration may include:
      <p />
      <pre>
  "configuration": {
    "tagName": "map-widget",
    "options": [
      {
        "key": "domain",
        "type": "select",
        "required": false,
        "options": {
          "values": ["mobility", "tourism"],
          "default": "mobility"
        }
      },
      {
        "key": "station-types",
        "type": "multiselect",
        "required": true,
        "options": {
          "values": ["CreativeIndustry","EChargingStation","EChargingPlug"],
          "default": []
        }
      },
      {
        "key": "sample-A",
        "type": "string",
        "required": false,
        "options": {
          "default": "Test String"
        }
      },
      {
        "key": "sample-B",
        "type": "text",
        "required": false,
        "options": {
          "default": "A multi-\nLine Text"
        }
      },
      {
        "key": "sample-C",
        "type": "integer",
        "required": false,
        "options": {
          "default": "342",
          "min": 0,
          "max": 600
        }
      },
      {
        "key": "sample-D",
        "type": "float",
        "required": false,
        "options": {
          "default": "34.2",
          "min": 0,
          "max": 60.04
        }
      },
      {
        "key": "sample-E",
        "type": "bool",
        "required": false,
        "options": {
          "default": false
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
