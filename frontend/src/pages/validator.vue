/* eslint-disable */
<template>
  <div class="mb-5">
    <div class="container container-extended pb-4 pl-4 pr-4">
      <h1>Validator</h1>
      <div class="row">
        <div class="col-md-8">
          <div class="text-uppercase font-weight-bold mb-2">
            wcs-manifest.json
          </div>
          <b-card id="widget-preview" class="full-height">
            <b-card-text id="twrap" class="text-center">
              <AceEditor
                v-model="wcsManifest"
                theme="github"
                lang="json"
                class="container-fluid text-monospace"
                style="min-height: 500px"
                @input="parseJson"
                @init="editorInit"
              ></AceEditor>
            </b-card-text>
            <div slot="footer" class="d-flex flex-column flex-sm-row">
              <div v-if="errors">
                <table class="d-table">
                  <tr v-for="(error, eidx) in errors" :key="eidx" class="">
                    <td class="d-table-cell align-text-top">
                      <font-awesome-icon
                        :icon="['fas', 'bug']"
                        class="text-danger ml-4 mr-1"
                      />
                    </td>
                    <td class="d-table-cell">
                      <span class="text-danger mt-sm-0">
                        <code class="bg-light">{{
                          error.path ? error.path : '(ROOT)'
                        }}</code
                        >: {{ error.text }}
                        <span v-if="error.params">
                          &#10132;&nbsp;
                          <code
                            v-for="(param, pidx) in error.params"
                            :key="pidx"
                            class="bg-light"
                          >
                            {{ param }}
                          </code>
                        </span>
                      </span>
                    </td>
                  </tr>
                </table>
              </div>
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
          <div class="text-uppercase font-weight-bold mb-2">
            configuration preview
          </div>
          <b-card class="full-height widget-config">
            <b-card-text>
              <WcsConfigTool
                v-if="config"
                :config="config"
                @snippet="updateSnippet"
              ></WcsConfigTool>
              <span v-else> No preview due to errors </span>
            </b-card-text>
          </b-card>
        </div>
      </div>
      <div class="row mt-5">
        <div class="col-12">
          <div class="text-uppercase font-weight-bold mb-2">code snippet</div>
          <b-card id="widget-codesnippet" style="min-height: 250px">
            <b-card-text>
              <textarea
                v-if="!errors"
                id="code-snippet"
                v-model="snipp"
                class="full-width full-height code-snippet text-monospace"
                style="border: 0; background-color: inherit"
                rows="10"
              ></textarea>
              <span v-else> No code snippet due to errors </span>
            </b-card-text>
          </b-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import WcsConfigTool from 'odh-web-components-configurator/src/components/wcs-configurator';
import Ajv from 'ajv';
import Schema from 'static/schemas/wcs-manifest-schema.json';
import Example from 'static/wcs-manifest-example.json';
import AceEditor from 'vue2-ace-editor';

export default {
  components: { WcsConfigTool, AceEditor },
  data() {
    return {
      wcsManifest: '',
      Schema,
      Example,
      snipp: '',
      config: null,
      errors: null,
    };
  },
  mounted() {
    this.wcsManifest = JSON.stringify(this.Example, null, 2);
    this.parseJson();
  },
  methods: {
    editorInit() {
      require('brace/ext/language_tools');
      require('brace/mode/json');
      require('brace/theme/github');
    },
    updateSnippet(data) {
      this.snipp = data;
    },
    getLineNumber(errorMsg) {
      let lineNumber = null;

      /* Firefox gives us the line number */
      let match = /line ([0-9]+)/.exec(errorMsg);
      if (match) {
        lineNumber = match.length > 1 ? match[1] : match[0];
      } else {
        /* Other browsers just give a position in the JSON string */
        match = /position ([0-9]+)/.exec(errorMsg);
        if (match) {
          const position = match.length > 1 ? match[1] : match[0];
          const tmp = this.wcsManifest.substring(0, position);
          lineNumber = (tmp.match(/\n/g) || '').length + 1;
        }
      }
      return lineNumber;
    },
    parseJson() {
      let wcsManifestParsed = {};
      this.errors = [];
      this.config = null;
      const ajv = new Ajv({
        $data: true,
        verbose: false,
        allErrors: true,
        format: 'full',
      });
      ajv.addKeyword('validDefault', {
        type: ['string', 'null', 'array'],
        modifying: true,
        validate(
          schema,
          data,
          parentSchema,
          dataPath,
          parentData,
          parentDataProperty,
          rootData
        ) {
          if (
            !!parentData.default &&
            !!parentData.values &&
            parentData.values.includes(parentData.default)
          ) {
            return true;
          }
          return false;
        },
        errors: true,
      });
      try {
        const validate = ajv.compile(this.Schema);
        wcsManifestParsed = JSON.parse(this.wcsManifest);
        if (!validate(wcsManifestParsed)) {
          const errors = validate.errors
            .filter((error) => {
              switch (error.message) {
                case 'boolean schema is false':
                case 'should match "then" schema':
                case 'should match "else" schema':
                case 'should match some schema in anyOf':
                  return false;
              }
              return true;
            })
            .map((error) => {
              if (
                error.params.keyword &&
                error.params.keyword === 'validDefault'
              ) {
                error.params.keyword = 'Choose any item inside .values';
              }
              return {
                text: error.message,
                path: error.dataPath,
                params:
                  Object.keys(error.params).length === 0 &&
                  error.params.constructor === Object
                    ? null
                    : error.params,
              };
            });
          this.errors = [...this.errors, ...errors];

          return;
        }
      } catch (e) {
        if (e instanceof SyntaxError) {
          const lineNumber = this.getLineNumber(e.message);
          this.errors = [
            ...this.errors,
            {
              text:
                'Syntax error' + (lineNumber ? ' @ line ' + lineNumber : ''),
            },
          ];
        } else {
          this.errors = [...this.errors, { text: e.message }];
        }
        return;
      }
      this.errors = null;
      this.config = wcsManifestParsed.configuration;
    },
  },
};
</script>
