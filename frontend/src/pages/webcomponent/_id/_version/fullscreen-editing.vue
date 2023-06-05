<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div class="d-flex flex-row">
    <div id="twrap" style="width: 50%">
      <iframe
        id="tframe"
        class="full-height full-width"
        style="min-height: 100vh; margin-bottom: 60px"
        title="iframe-preview"
      ></iframe>
      <detail-bottom-bar
        selected-view="editing"
        @updatePreview="updateSnippetFromEditor"
        @copyCode="copySnippetToClipboard"
      >
      </detail-bottom-bar>
      <WCSConfigTool
        v-if="config"
        :config="config.configuration"
        style="display: none"
        @snippet="updateSnippetFromTool"
      ></WCSConfigTool>
    </div>
    <prism-editor
      v-model="editorCode"
      class="my-editor pt-4"
      :highlight="highlighter"
      @blur="updateSnippetFromEditor"
      style="
        border: 0;
        background-color: inherit;
        width: 50%;
        padding-left: 8px;
      "
    ></prism-editor>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import WCSConfigTool from 'odh-web-components-configurator/src/components/wcs-configurator.vue';
import { PrismEditor } from 'vue-prism-editor';
import DetailBottomBar from '~/components/detail-bottom-bar.vue';

// eslint-disable-next-line import/order
import { highlight, languages } from 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-markup';
import { WebcomponentModel } from '~/domain/model/WebcomponentModel';
import { WebcomponentConfigurationModel } from '~/domain/model/WebcomponentConfigurationModel';
import { copyToClipboard } from '~/utils/ClipboardUtils';

export default Vue.extend({
  name: 'FullscreenEditing',
  components: { DetailBottomBar, WCSConfigTool, PrismEditor },
  layout(context) {
    return 'fullscreen';
  },
  data() {
    return {
      previewBaseURL: (this as any).$api.baseUrl,
      editorCode: '',
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
    component(): WebcomponentModel {
      return this.$store.state.webcomponent.webcomponent;
    },
    config(): WebcomponentConfigurationModel {
      return this.$store.state.webcomponent.configuration;
    },
    selectedVersion(): string {
      return this.$store.state.webcomponent.versionTag;
    },
    snippet(): string {
      return this.$store.state.webcomponent.snippet;
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
  },

  mounted() {
    if (this.externalPreviewUrl) {
      this.updatePreview();
    }
    if (!this.editorCode) {
      this.editorCode = this.snippet;
    }
  },

  methods: {
    highlighter(code) {
      return highlight(code, languages.markup); // returns html
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

    updatePreview() {
      const src = this.externalPreviewUrl;
      if (!src) {
        return; // probably not yet loaded
      }

      const oldElement = document.getElementById('tframe');

      oldElement.parentNode.removeChild(oldElement);

      const width = document.documentElement.clientWidth;
      const newElement = document.createElement('iframe');
      newElement.setAttribute('id', 'tframe');
      newElement.setAttribute('class', 'full-height full-width');
      if (width > 576) {
        newElement.setAttribute(
          'style',
          'min-height: 100vh; padding-bottom: 65px;'
        );
      } else {
        newElement.setAttribute(
          'style',
          'min-height: 100vh; padding-bottom: 45px;'
        );
      }
      newElement.setAttribute('frameborder', '0');
      newElement.src = src;

      document.getElementById('twrap').appendChild(newElement);

      // newElement.contentDocument.write(this.editorCode);
      newElement.contentDocument.close();
    },
    copySnippetToClipboard() {
      copyToClipboard(this.editorCode);
    },
  },
});
</script>
<style>
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
