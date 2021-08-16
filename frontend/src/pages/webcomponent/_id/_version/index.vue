<template>
  <div style="min-height: 1000px">
    <div v-if="component && config" class="mb-5">
      <WcDetailBlock
        :return-link="returnLink"
        :show-preview="showPreview"
        @set-show-preview="setShowPreview"
      ></WcDetailBlock>
      <div v-if="showPreview && hasAnyVersion">
        <div class="container-fluid container-extended pb-4 p-2 pr-4 pt-sm-2">
          <div class="detail-content-left">
            <b-alert
              :show="!isLatestVersionActive"
              variant="danger"
              class="mt-4 mb-4 detail-content-left"
              >You have not selected the latest version of this
              webcomponent.</b-alert
            >
          </div>
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
              v-model="tabIndex"
            >
              <b-tab
                id="first-tab"
                title="EASY CONFIGURATION"
                :active="initTabOne"
              >
                <div v-show="!editMode">
                  <div class="full-height widget-config">
                    <span>
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
                        :restore-snippet="snippet"
                        @snippet="updateSnippetFromTool"
                      ></WCSConfigTool>
                    </b-card-text>
                  </div>
                </div>
                <div v-show="editMode">
                  <div class="text-uppercase font-weight-bold mb-2">
                    configuration
                  </div>
                  <b-card class="full-height widget-config">
                    <b-card-text
                      ><strong
                        >The selected configuration is not compatible with our
                        configuration tool.</strong
                      ><br />
                      You can either switch over to the "EDIT CODE" tab or
                      "DISCARD CHANGES". {{ snippetFromTool }}</b-card-text
                    >

                    <div slot="footer" class="text-right text-uppercase">
                      <span
                        style="cursor: pointer"
                        class="mr-4"
                        @click="resetEditorSnippet()"
                      >
                        DISCARD CHANGES
                      </span>
                    </div>
                  </b-card>
                </div></b-tab
              >
              <b-tab title="EDIT CODE" class="second-tab" :active="!initTabOne"
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
                        v-model="editorCode"
                        class="my-editor"
                        :highlight="highlighter"
                        style="border: 0; background-color: inherit"
                        @blur="updateSnippetFromEditor"
                      />
                    </b-card-text>

                    <div
                      v-if="editorCode !== snippetFromTool"
                      slot="footer"
                      class="text-right text-uppercase"
                    >
                      <span
                        style="cursor: pointer"
                        class="mr-4"
                        @click="resetEditorSnippet()"
                      >
                        DISCARD CHANGES
                      </span>
                    </div>
                  </b-card>
                  <b-alert
                    class="mt-2"
                    variant="info"
                    :show="snippet !== snippetFromTool"
                    >This is a custom snippet and cannot be loaded into the EASY
                    CONFIGURATION.</b-alert
                  >
                </div></b-tab
              >
              <template #tabs-end>
                <div class="version-select-container ml-md-5">
                  <b-form-select
                    :value="selectedVersion"
                    class="version-select ml-md-2"
                    @change="reloadWebcomponentAtVersion"
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
          @updatePreview="forceUpdatePreview"
          @copyCode="copySnippetToClipboard"
        >
        </detail-bottom-bar>
      </div>

      <div v-else>
        <component-read-me />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import WCSConfigTool from 'odh-web-components-configurator/src/components/wcs-configurator.vue';
import { PrismEditor } from 'vue-prism-editor';
import WcDetailBlock from '../../../../components/webcomponent/WcDetailBlock.vue';
import ComponentReadMe from '~/components/webcomponent/ComponentReadMe.vue';
// eslint-disable-next-line import/order
import { highlight, languages } from 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-markup.min';

import DetailBottomBar from '~/components/detail-bottom-bar.vue';
import { copyToClipboard } from '~/utils/ClipboardUtils';
import { WebcomponentModel } from '~/domain/model/WebcomponentModel';
import { WebcomponentConfigurationModel } from '~/domain/model/WebcomponentConfigurationModel';

export default Vue.extend({
  components: {
    DetailBottomBar,
    ComponentReadMe,
    WcDetailBlock,
    WCSConfigTool,
    PrismEditor,
  },
  data() {
    return {
      previewBaseURL: (this as any).$api.baseUrl,
      autoUpdate: true,
      showPreview: true,
      editorCode: '',
      initTabOne: false,
      timer: null,
      tabIndex: 0,
    };
  },

  async fetch() {
    this.$store.commit('webcomponent/SET_EDIT_MODE', true);

    await this.$store.dispatch('webcomponent/loadWebcomponent', {
      uuid: this.$route.params.id,
      version: this.$route.params.version,
    });

    if (!this.snippet) {
      this.$store.commit('webcomponent/SET_EDIT_MODE', false);
    }
  },

  computed: {
    hasAnyVersion(): boolean {
      return !!this.$store.state.webcomponent.versionTag;
    },
    returnLink(): string {
      if (this.$route.query.from) {
        return this.$route.query.from;
      }

      return this.localePath('index');
    },
    isLatestVersionActive(): boolean {
      if (!this.component) {
        return false;
      }
      return this.selectedVersion === this.component.versions[0].versionTag;
    },
    component(): WebcomponentModel {
      return this.$store.state.webcomponent.webcomponent;
    },
    config(): WebcomponentConfigurationModel {
      return this.$store.state.webcomponent.configuration;
    },
    selectedVersion(): string {
      return this.$store.state.webcomponent.versionTag;
    },
    snippetFromTool(): string {
      return this.$store.state.webcomponent.snippetFromTool;
    },
    snippet(): string {
      return this.$store.state.webcomponent.snippet;
    },
    editMode() {
      return this.editorCode && this.editorCode !== this.snippetFromTool;
    },
    externalPreviewUrl(): string {
      if (!this.component || !this.config) {
        return '';
      }
      return (
        this.previewBaseURL +
        '/preview/' +
        this.component.uuid +
        '/' +
        this.selectedVersion +
        '?attribs=' +
        this.$store.getters['webcomponent/transportString']
      );
    },
  },

  watch: {
    snippet(value) {
      this.editorCode = value;
    },
    externalPreviewUrl(url) {
      if (url) {
        this.updatePreview();
      }
    },
    tabIndex(value) {
      if (value === 0) {
        if (this.editorCode !== this.snippetFromTool) {
          return;
        }
        this.$store.commit('webcomponent/SET_EDIT_MODE', false);
      } else {
        this.$store.commit('webcomponent/SET_EDIT_MODE', true);
      }
    },
  },

  mounted() {
    if (this.externalPreviewUrl) {
      this.updatePreview();
    }
    if (!this.editorCode) {
      this.editorCode = this.snippet;
    }

    this.initTabOne = !this.editMode;
    this.$store.commit('webcomponent/SET_EDIT_MODE', this.editMode);
  },

  methods: {
    async reloadWebcomponentAtVersion(version: string) {
      await this.$router.push(
        this.localePath({
          name: 'webcomponent-id-version',
          params: { id: this.$route.params.id, version },
        })
      );

      setTimeout(() => {
        location.reload();
      }, 100);
    },

    updateSnippetFromTool(snippet: string) {
      this.$store.commit('webcomponent/SET_SNIPPET_FROM_TOOL', snippet);
    },

    updateSnippetFromEditor() {
      if (this.editorCode === this.snippet) {
        return;
      }

      this.$store.commit(
        'webcomponent/SET_SNIPPET_FROM_EDITOR',
        this.editorCode
      );
    },

    highlighter(code: string): string {
      return highlight(code, languages.markup, 'markup'); // returns html
    },

    setShowPreview(show: boolean): void {
      this.showPreview = show;
    },

    resetEditorSnippet(): void {
      this.$store.dispatch('webcomponent/resetSnippet');
      this.tabIndex = 0;
    },

    copySnippetToClipboard(): void {
      copyToClipboard(this.editorCode);
    },

    forceUpdatePreview() {
      this.updateSnippetFromEditor();
      this.updatePreview(true);
    },

    updatePreview(force = false): void {
      const src = this.externalPreviewUrl;
      if (!src) {
        return; // probably not yet loaded
      }

      if (!force && !this.autoUpdate) {
        return;
      }

      const oldElement = document.getElementById('tframe');

      if (!oldElement) {
        return;
      }

      oldElement.parentNode.removeChild(oldElement);

      const newElement = document.createElement('iframe');
      newElement.setAttribute('id', 'tframe');
      newElement.setAttribute('class', 'full-height full-width');
      newElement.setAttribute('style', 'min-height: 800px;');
      newElement.setAttribute('frameborder', '0');

      document.getElementById('twrap').appendChild(newElement);
      newElement.src = src;

      // newElement.contentDocument.write(this.effectiveSnippet);
      newElement.contentDocument.close();
    },
  },
});
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
