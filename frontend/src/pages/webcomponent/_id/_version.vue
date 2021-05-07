<template>
  <div v-if="component" class="mb-5">
    <WcDetailBlock
      :return-link="returnLink"
      :show-preview="showPreview"
      :external-preview-url="externalPreviewUrl"
      @set-show-preview="setShowPreview"
    ></WcDetailBlock>
    <div v-if="showPreview && hasAnyVersion">
      <div
        class="container-fluid container-extended pb-2 p-2 pr-sm-5 pl-sm-5 pt-3"
      >
        <b-alert
          :show="!isLatestVersionActive"
          variant="danger"
          class="mt-4 mb-4"
          >You have not selected the latest version of this
          webcomponent.</b-alert
        >
      </div>
      <div
        class="container-fluid container-extended pb-4 p-2 pl-4 pr-4 pt-sm-2"
      >
        <div class="row">
          <div class="col-lg-8 detail-content-left">
            <b-card id="widget-preview" class="full-height">
              <b-card-text id="twrap" class="text-center">
                <iframe
                  id="tframe"
                  class="full-height full-width"
                  style="min-height: 800px"
                  title="iframe-preview"
                ></iframe>
              </b-card-text>
            </b-card>
          </div>
          <b-tabs
            pills
            class="config-tabs col-lg-4 pt-3 pt-lg-0 detail-content-right"
          >
            <b-tab id="first-tab" title="EASY CONFIGURATION" active>
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
                  class="white"
                  style="min-height: 250px"
                >
                  <b-card-text>
                    <prism-editor
                      v-model="code"
                      class="my-editor"
                      :highlight="highlighter"
                      line-numbers
                      style="border: 0; background-color: inherit"
                    />
                  </b-card-text>

                  <div
                    v-if="code !== snipp"
                    slot="footer"
                    class="text-right text-uppercase"
                  >
                    <span
                      style="cursor: pointer"
                      class="mr-4"
                      @click="toggleEditMode()"
                    >
                      <font-awesome-icon :icon="['fas', 'times']" />RESET
                    </span>
                  </div>
                </b-card>
              </div></b-tab
            >
            <template #tabs-end>
              <div class="version-select-container ml-md-5">
                <b-form-select
                  :value="selectedVersion"
                  class="version-select ml-md-2"
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
      <detail-bottom-bar
        :selected-view="selectedView"
        @updatePreview="updatePreview"
        @setSelectedView="setSelectedView"
        @copyCode="copySnippetToClipboard"
      >
      </detail-bottom-bar>
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
import 'vue-prism-editor/dist/prismeditor.min.css'; // import the styles somewhere
// eslint-disable-next-line import/order
import { highlight, languages } from 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-markup.min';
import 'prismjs/themes/prism.css';
import DetailBottomBar from '~/components/detail-bottom-bar'; // import syntax highlighting styles

export default {
  components: {
    DetailBottomBar,
    ComponentReadMe,
    WcDetailBlock,
    WCSConfigTool,
    PrismEditor,
  },
  data() {
    return {
      snippOriginal: '',
      attribs: '',
      editmode: false,
      autoUpdate: true,
      previewBaseURL: this.$api.baseUrl,
      showPreview: true,
      selectedView: '',
      code: '',
    };
  },
  computed: {
    hasAnyVersion() {
      return !!this.$store.getters['webcomponent/currentVersion'];
    },
    externalPreviewUrl() {
      return (
        this.previewBaseURL +
        '/preview/' +
        this.component.uuid +
        '/' +
        this.selectedVersion +
        this.attribs
      );
    },
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
      return this.$store.getters['webcomponent/currentWebcomponent'];
    },
    config() {
      return this.$store.getters['webcomponent/currentConfig'];
    },
    selectedVersion() {
      return this.$store.getters['webcomponent/currentVersion'];
    },
    snipp() {
      return this.$store.getters['webcomponent/currentSnipp'];
    },
  },
  created() {
    // webcomponentStore.
    this.$store.dispatch('webcomponent/loadWebcomponent', {
      uuid: this.$route.params.id,
      version: this.$route.params.version,
    });
    this.code = this.snipp;
    this.snippOriginal = this.snipp;
  },
  methods: {
    highlighter(code) {
      return highlight(code, languages.markup, 'markup'); // returns html
    },
    setShowPreview(show) {
      this.showPreview = show;
    },
    toggleEditMode() {
      this.code = this.snippOriginal;
      if (this.autoUpdate) {
        this.updatePreview();
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
      this.$store.dispatch('webcomponent/updateSnipp', {
        snipp: data + '\n' + this.getDistIncludes().join('\n'),
      });
      this.code = this.snipp;

      if (this.autoUpdate) {
        this.updatePreview();
      }
    },
    copySnippetToClipboard() {
      document.execCommand('copy');
      const dummy = document.createElement('textarea');
      document.body.appendChild(dummy);
      dummy.value = this.code;
      dummy.select();
      document.execCommand('copy');
      document.body.removeChild(dummy);
    },
    setSelectedView(newSelectedView) {
      this.selectedView = newSelectedView;
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

      newElement.contentDocument.write(this.code);
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
  max-width: 150px;
}

.version-select-container {
  display: flex;
  flex-direction: row;
  flex-grow: 1;
  justify-content: flex-end;
  padding-left: 15px;
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

@media (max-width: 576px) {
  .version-select {
    width: 100%;
    min-width: 100%;
  }

  .version-select-container {
    padding-left: 0;
    width: 100%;
    min-width: 100%;
  }
}

footer .bg-light {
  padding-bottom: 30px;
}
</style>
