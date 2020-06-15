/* eslint-disable */
<template>
  <div class="mb-5">
    <div class="container container-extended pb-4 pl-4 pr-4">
      <h1>
        Validator
      </h1>
      <div class="row">
        <div class="col-md-8">
          <div class="text-uppercase font-weight-bold mb-2">
            wcs-manifest.json
          </div>
          <b-card id="widget-preview" class="full-height">
            <b-card-text id="twrap" class="text-center">
              <textarea
                v-model="wcs_manifest"
                class="container-fluid"
                style="min-height: 500px; font-family: monospace"
              ></textarea>
            </b-card-text>
            <div slot="footer" class="d-flex flex-column flex-sm-row">
              <font-awesome-icon
                v-if="errors"
                :icon="['fas', 'bug']"
                class="text-danger ml-4 mr-1"
              />
              <span v-if="errors" class="text-danger mt-sm-0">
                {{ errors }}
              </span>
              <span v-else class="text-success mt-sm-0">
                <font-awesome-icon
                  :icon="['fas', 'check']"
                  class="text-success ml-4 mr-1"
                />
                SYNTAX VALID
              </span>
            </div>
          </b-card>
        </div>
        <div class="col-md-4 mt-5 mt-md-0">
          <div class="text-uppercase font-weight-bold mb-2">configuration</div>
          <b-card class="full-height widget-config">
            <b-card-text>
              <WCSConfigTool
                :config="configonly"
                @snippet="updateSnippet"
              ></WCSConfigTool>
            </b-card-text>
          </b-card>
        </div>
      </div>
      <div class="row mt-5">
        <div class="col-12">
          <div class="text-uppercase font-weight-bold mb-2">code snippet</div>
          <b-card id="widget-codesnippet" style="min-height: 250px;">
            <b-card-text>
              <textarea
                id="code-snippet"
                v-model="snipp"
                class="full-width full-height code-snippet"
                style="border: 0; background-color: inherit;font-family: 'Courier New', Courier, monospace"
                rows="10"
              ></textarea>
            </b-card-text>
          </b-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import WCSConfigTool from 'odh-web-components-configurator/src/components/wcs-configurator'
import Ajv from 'ajv'
import Schema from 'static/wcs-manifest-schema.json'
import Example from 'static/wcs-manifest-example.json'

export default {
  components: { WCSConfigTool },
  data() {
    return {
      wcs_manifest: '',
      Schema,
      Example,
      snipp: '',
      errors: []
    }
  },
  computed: {
    configonly() {
      // try {
      //   return this.parseJson(this.wcs_manifest)
      // } catch (e) {
      //   console.log(e)
      // }
      return {
        tagName: 'map-widget',
        options: []
      }
    }
  },
  mounted() {
    this.wcs_manifest = JSON.stringify(this.Example, null, 2)
  },
  methods: {
    updateSnippet(data) {
      this.snipp = data
    },
    parseJson(data) {
      let res = {}
      const ajv = new Ajv({
        $data: true,
        verbose: true,
        allErrors: true
      })
      try {
        let validate
        try {
          validate = ajv.compile(this.Schema)
        } catch (e) {
          this.errors = [...this.errors, { text: e.message }]
          console.log(e.message)
          return
        }

        try {
          res = JSON.parse(this.wcs_manifest)
          const valid = validate(res)
          if (!valid) {
            const errors = validate.errors.map((error) => {
              return {
                text: error.message,
                path: error.schemaPath
              }
            })
            this.errors = [...this.errors, ...errors]
            console.log(errors)
            return
          }
        } catch (e) {
          this.errors = [...this.errors, { text: e.message }]
          console.log(e.message)
          return
        }

        if (res.configuration !== undefined) {
          this.errors = []
          return res.configuration
        }
      } catch (e) {
        this.errors = [...this.errors, { text: 'ERROR: ' + e }]
      }
      return {}
    }
  }
}
</script>
