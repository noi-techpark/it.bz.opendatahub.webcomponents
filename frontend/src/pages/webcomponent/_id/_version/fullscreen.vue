<template>
  <div id="twrap" style="max-height: 100vh">
    <iframe
      id="tframe"
      class="full-height full-width"
      style="margin-bottom: 60px"
      title="iframe-preview"
    ></iframe>
    <detail-bottom-bar
      selected-view="preview"
      @updatePreview="updatePreview"
      @copyCode="copySnippetToClipboard"
    >
    </detail-bottom-bar>
    <WCSConfigTool
      v-if="config && configuratorEnabled"
      :config="config.configuration"
      style="display: none"
      @snippet="updateSnippetFromTool"
    ></WCSConfigTool>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import WCSConfigTool from 'odh-web-components-configurator/src/components/wcs-configurator.vue';
import DetailBottomBar from '~/components/detail-bottom-bar.vue';
import { WebcomponentModel } from '~/domain/model/WebcomponentModel';
import { WebcomponentConfigurationModel } from '~/domain/model/WebcomponentConfigurationModel';
import { getDistIncludes, parseSnippetAttributes } from '~/utils/SnippetUtils';
import { copyToClipboard } from '~/utils/ClipboardUtils';

export default Vue.extend({
  name: 'Fullscreen',
  components: { DetailBottomBar, WCSConfigTool },
  layout(context) {
    return 'fullscreen';
  },
  data() {
    return {
      configuratorEnabled: false,
      editorCode: '',
      previewBaseURL: (this as any).$api.baseUrl,
      attribs: (this as any).$route.query.attribs,
    };
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
    snippetFromTool(): string {
      return this.$store.state.webcomponent.snippetFromTool;
    },
    snippetFromEditor(): string {
      return this.$store.state.webcomponent.snippetFromEditor;
    },
    externalPreviewUrl(): string {
      if (!this.component) {
        return '';
      }
      return (
        this.previewBaseURL +
        '/preview/' +
        this.component.uuid +
        '/' +
        this.selectedVersion +
        this.effectiveAttribs
      );
    },
    effectiveAttribs() {
      if (!this.component) {
        return '';
      }

      if (this.attribs) {
        return atob(this.attribs);
      } else {
        return this.$store.getters['webcomponent/attribs'];
      }
    },
  },

  created() {
    this.initializeWebcomponentAndVersion();

    if (!this.attribs) {
      this.configuratorEnabled = true;
    } else {
      this.editorCode = this.snippetFromEditor;
    }
  },

  watch: {
    externalPreviewUrl(url) {
      if (url) {
        this.updatePreview();
      }
    },
  },

  methods: {
    async initializeWebcomponentAndVersion() {
      await this.$store.dispatch('webcomponent/loadWebcomponent', {
        uuid: this.$route.params.id,
        version: this.$route.params.version,
      });
    },

    updateSnippetFromTool(snippet: string) {
      this.$store.commit(
        'webcomponent/SET_SNIPPET_FROM_TOOL',
        snippet + '\n' + getDistIncludes(this.config).join('\n')
      );

      this.editorCode = this.snippetFromTool;

      this.updatePreview();
    },

    updatePreview() {
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
      newElement.src = this.externalPreviewUrl;

      document.getElementById('twrap').appendChild(newElement);

      // newElement.contentDocument.write(this.snipp);
      newElement.contentDocument.close();
    },
    copySnippetToClipboard() {
      copyToClipboard(this.editorCode);
    },
  },
});
</script>
