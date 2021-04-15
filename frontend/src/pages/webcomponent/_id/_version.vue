<template>
  <div v-if="component" class="mb-5">
    <WcDetailBlock
      :component="component"
      :return-link="returnLink"
      :show-preview="showPreview"
      @set-show-preview="setShowPreview"
    ></WcDetailBlock>
    <div v-if="showPreview">
      <div class="container-fluid extended pb-2 p-2 pr-sm-5 pl-sm-5 pt-3">
        <b-alert
          :show="!isLatestVersionActive"
          variant="danger"
          class="mt-4 mb-4"
          >You have not selected the latest version of this
          webcomponent.</b-alert
        >
      </div>
      <div class="container-fluid extended pb-4 p-2 pr-sm-5 pl-sm-5 pt-sm-2">
        <div class="row">
          <div class="col-md-8 detail-content-left">
            <div class="text-uppercase font-weight-bold mb-2">preview</div>
            <b-card id="widget-preview" class="full-height">
              <b-card-text id="twrap" class="text-center">
                <iframe
                  id="tframe"
                  class="full-height full-width"
                  style="min-height: 800px"
                  title="iframe-preview"
                ></iframe>
              </b-card-text>

              <div
                slot="footer"
                class="text-right text-uppercase d-flex flex-column flex-sm-row"
              >
                <span
                  class="mt-2 mt-sm-0 link_with_icon"
                  style="cursor: pointer"
                  @click="updatePreview"
                >
                  <font-awesome-icon
                    :icon="['fas', 'redo']"
                    class="ml-4 mr-1"
                  />update preview
                </span>
                <a
                  :href="
                    previewBaseURL +
                    '/preview/' +
                    component.uuid +
                    '/' +
                    selectedVersion +
                    attribs
                  "
                  target="_blank"
                  class="mt-2 mt-sm-0 link_with_icon"
                >
                  <font-awesome-icon
                    :icon="['fas', 'rocket']"
                    class="ml-4 mr-1"
                  />external window
                </a>
              </div>
            </b-card>
          </div>
          <b-tabs
            pills
            class="config-tabs col-md-4 mt-md-0 detail-content-right"
          >
            <b-tab title="EASY CONFIGURATION" class="first-tab" active>
              <div v-show="!editmode">
                <div class="full-height widget-config">
                  <span v-if="!editmode">
                    <b-checkbox
                      v-model="autoUpdate"
                      class="d-inline-block"
                    ></b-checkbox
                    >auto update
                  </span>
                  <b-card-text>
                    <WCSConfigTool
                      v-if="config"
                      :config="config.configuration"
                      @snippet="updateSnippet"
                    ></WCSConfigTool>
                  </b-card-text>

                  <!--<div slot="footer" class="text-right text-uppercase">
                  <font-awesome-icon :icon="['fas', 'check']" /> apply
                </div>-->
                </div>
              </div>
              <div v-show="editmode">
                <div class="text-uppercase font-weight-bold mb-2">
                  configuration
                </div>
                <b-card
                  class="full-height widget-config"
                  style="background-color: #fafafa"
                >
                  <b-card-text
                    >Configurator disabled. Manual configuration
                    active.</b-card-text
                  >

                  <!--<div slot="footer" class="text-right text-uppercase">
                    <font-awesome-icon :icon="['fas', 'check']" /> apply
                  </div>-->
                </b-card>
              </div></b-tab
            >
            <b-tab title="EDIT CODE" class="second-tab"
              ><div>
                <div class="text-uppercase font-weight-bold mb-2">
                  code snippet
                </div>
                <b-card
                  id="widget-codesnippet"
                  :class="{ white: editmode }"
                  style="min-height: 250px"
                >
                  <b-card-text>
                    <prism-editor
                      v-model="snipp"
                      class="my-editor"
                      :readonly="!editmode"
                      :highlight="highlighter"
                      line-numbers
                      style="border: 0; background-color: inherit"
                    />
                  </b-card-text>

                  <div slot="footer" class="text-right text-uppercase">
                    <span
                      v-if="editmode"
                      style="cursor: pointer"
                      class="mr-4"
                      @click="toggleEditMode()"
                    >
                      <font-awesome-icon :icon="['fas', 'times']" />RESET
                    </span>
                    <span
                      v-else
                      style="cursor: pointer"
                      class="mr-4"
                      @click="toggleEditMode()"
                    >
                      <font-awesome-icon :icon="['far', 'edit']" />EDIT
                    </span>
                    <span
                      style="cursor: pointer"
                      @click="copySnippetToClipboard()"
                    >
                      <font-awesome-icon :icon="['far', 'copy']" />COPY
                    </span>
                  </div>
                </b-card>
              </div></b-tab
            >
            <template #tabs-end>
              <div class="version-select-container">
                <b-form-select
                  :value="selectedVersion"
                  style="max-width: 150px"
                  class="version-select ml-2"
                  @change="reloadConfig"
                >
                  <option
                    v-for="version in component.versions"
                    :key="version.versionTag"
                  >
                    {{ version.versionTag }}
                  </option>
                </b-form-select>
              </div>
            </template>
          </b-tabs>
        </div>
      </div>
      <div class="bottom-bar">
        <div class="d-flex justify-content-center">
          <div
            v-if="selectedView === 'preview'"
            class="bottom-bar-button selected d-flex justify-content-center align-items-center text-uppercase"
            @click="selectedView = ''"
          >
            <img :src="require('static/icons/ic_min_preview.svg')" />
            <div class="bottom-bar-button-text p-1">minimize preview</div>
          </div>
          <div
            v-else
            class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
            @click="selectedView = 'preview'"
          >
            <img :src="require('static/icons/ic_max_preview.svg')" />
            <div class="bottom-bar-button-text p-1">fullscreen preview</div>
          </div>
          <div
            v-if="selectedView === 'editing'"
            class="bottom-bar-button selected d-flex justify-content-center align-items-center text-uppercase"
            @click="selectedView = ''"
          >
            <img :src="require('static/icons/ic_min_editing.svg')" />
            <div class="bottom-bar-button-text p-1">minimize editing</div>
          </div>
          <div
            v-else
            class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
            @click="selectedView = 'editing'"
          >
            <img :src="require('static/icons/ic_max_editing.svg')" />
            <div class="bottom-bar-button-text p-1">fullscreen editing</div>
          </div>
          <div
            class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
          >
            <img :src="require('static/icons/ic_copy.svg')" />
            <div class="bottom-bar-button-text p-1">copy code</div>
          </div>
          <div
            class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
            @click="updatePreview"
          >
            <img :src="require('static/icons/ic_update.svg')" />
            <div class="bottom-bar-button-text p-1">update preview</div>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <component-read-me :component="component"></component-read-me>
    </div>
  </div>
</template>

<script>
import WCSConfigTool from 'odh-web-components-configurator/src/components/wcs-configurator';
import { PrismEditor } from 'vue-prism-editor';
import WcDetailBlock from '../../../components/webcomponent/WcDetailBlock';
import ComponentReadMe from '~/components/webcomponent/ComponentReadMe';
import { webcomponentStore } from '~/utils/store-accessor';
import 'vue-prism-editor/dist/prismeditor.min.css'; // import the styles somewhere

// eslint-disable-next-line import/order
import { highlight, languages } from 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-javascript';
import 'prismjs/themes/prism.css'; // import syntax highlighting styles

export default {
  components: {
    ComponentReadMe,
    WcDetailBlock,
    WCSConfigTool,
    PrismEditor,
  },
  data() {
    return {
      snipp: '',
      snippOriginal: '',
      attribs: '',
      editmode: false,
      autoUpdate: true,
      previewBaseURL: '',
      showPreview: true,
      selectedView: '',
    };
  },
  computed: {
    returnLink() {
      if (this.$route.query.from) {
        return this.$route.query.from;
      }

      return this.localePath('index');
    },
    isLatestVersionActive() {
      if (!this.component) {
        return false;
      }
      return this.selectedVersion === this.component.versions[0].versionTag;
    },
    component() {
      return webcomponentStore.currentWebcomponent;
    },
    config() {
      return webcomponentStore.currentConfig;
    },
    selectedVersion() {
      return webcomponentStore.currentVersion;
    },
  },
  created() {
    webcomponentStore.loadWebcomponent({
      uuid: this.$route.params.id,
      version: this.$route.params.version,
    });
  },
  methods: {
    highlighter(code) {
      return highlight(code, languages.js); // returns html
    },
    setShowPreview(show) {
      this.showPreview = show;
    },
    toggleEditMode() {
      this.editmode = !this.editmode;
      if (this.editmode) {
        this.snippOriginal = this.snipp;
      } else {
        this.snipp = this.snippOriginal;
        if (this.autoUpdate) {
          this.updatePreview();
        }
      }
    },
    reloadConfig(version) {
      this.$router.push(
        this.localePath({
          name: 'webcomponent-id-version',
          params: { id: this.$route.params.id, version },
        })
      );
    },
    updateSnippet(data) {
      this.snipp = data + '\n' + this.getDistIncludes().join('\n');

      if (this.autoUpdate) {
        this.updatePreview();
      }
    },
    copySnippetToClipboard() {
      const copyText = document.getElementById('code-snippet');

      copyText.select();

      document.execCommand('copy');
    },
    updatePreview() {
      const oldElement = document.getElementById('tframe');

      oldElement.parentNode.removeChild(oldElement);

      const newElement = document.createElement('iframe');
      newElement.setAttribute('id', 'tframe');
      newElement.setAttribute('class', 'full-height full-width');
      newElement.setAttribute('style', 'min-height: 800px;');
      newElement.setAttribute('frameborder', '0');

      document.getElementById('twrap').appendChild(newElement);

      newElement.contentDocument.write(this.snipp);
      newElement.contentDocument.close();

      this.attribs = this.parseSnippetAttributes();
    },
    buildAttribute(rawKey, rawValue) {
      const key = rawKey.trim();
      const value = encodeURIComponent(rawValue.trim());
      return key + '="' + value + '";';
    },
    parseSnippetAttributes() {
      let pos = this.snipp.search(this.config.configuration.tagName);
      let result = '';

      if (pos < 0) {
        return result;
      }

      pos += this.config.configuration.tagName.length;

      let isKey = true;
      let isValue = false;
      let key = '';
      let value = '';
      let isQuoted = false;
      for (let i = pos; i < this.snipp.length; i++) {
        const c = this.snipp.charAt(i);
        if (isKey) {
          switch (c) {
            case '=':
              isKey = false;
              isValue = true;
              break;
            case '>':
              if (key.trim().length > 0) {
                result += key.trim() + ';';
              }
              return '?attribs=' + result;
            case ' ':
              if (key.trim().length > 0) {
                result += key.trim() + ';';
              }
              break;
            default:
              key += c;
          }
        } else if (isValue) {
          switch (c) {
            case '"':
              if (isQuoted) {
                result += this.buildAttribute(key, value);
                isKey = true;
                isValue = false;
                isQuoted = false;
                key = '';
                value = '';
              } else {
                isQuoted = true;
              }
              break;
            case ' ':
              if (isQuoted) {
                value += ' ';
              } else {
                result += this.buildAttribute(key, value);
                isKey = true;
                isValue = false;
                key = '';
                value = '';
              }
              break;
            case '>':
              if (isQuoted) {
                value += '>';
              } else {
                result += this.buildAttribute(key, value);
                return '?attribs=' + result;
              }
              break;
            default:
              value += c;
          }
        }
      }
      return '?attribs=' + result;
    },
    getDistIncludes() {
      const scripts = [];

      // Wait until the async loadData method has finished
      // eslint-disable-next-line no-prototype-builtins
      if (this.config.hasOwnProperty('dist')) {
        if (
          // eslint-disable-next-line no-prototype-builtins
          this.config.dist.hasOwnProperty('scripts') &&
          this.config.dist.scripts.length > 0
        ) {
          this.config.dist.scripts.forEach((item) => {
            scripts.push(
              '<script ' +
                item.parameter +
                ' src="' +
                this.config.deliveryBaseUrl +
                '/' +
                this.config.dist.basePath +
                '/' +
                item.file +
                '"></scr' +
                'ipt>'
            );
          });
        } else {
          this.config.dist.files.forEach((item) => {
            scripts.push(
              '<script src="' +
                this.config.deliveryBaseUrl +
                '/' +
                this.config.dist.basePath +
                '/' +
                item +
                '"></scr' +
                'ipt>'
            );
          });
        }
      }

      return scripts;
    },
  },
};
</script>

<style lang="scss">
.widget-config {
  .card-footer {
    background-color: inherit;
  }
}

#widget-codesnippet {
  .card-body {
    background-color: #fafafa;
  }

  &.white .card-body {
    background-color: white;
  }

  .card-footer {
    background-color: inherit;
  }
}

#widget-preview {
  .card-footer {
    background-color: inherit;
  }
}

.d-table {
  border-collapse: separate;
  border-spacing: 0 0.5rem;
}

.version-select {
  padding-right: 8px !important;
}

.version-select-container {
  display: flex;
  flex-direction: row;
  flex-grow: 1;
  justify-content: flex-end;
}

.my-editor {
  /* we dont use `language-` classes anymore so thats why we need to add background and text color manually */
  background: #2d2d2d;
  color: #ccc;

  /* you must provide font-family font-size line-height. Example: */
  font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
  font-size: 14px;
  line-height: 1.5;
  padding: 5px;
}

/* optional class for removing the outline */
.prism-editor__textarea:focus {
  outline: none;
}
</style>
