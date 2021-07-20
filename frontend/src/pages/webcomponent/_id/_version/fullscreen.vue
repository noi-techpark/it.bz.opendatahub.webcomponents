<template>
  <div id="twrap" style="max-height: 100vh">
    <iframe
      id="tframe"
      class="full-height full-width"
      style="margin-bottom: 60px"
      title="iframe-preview"
      frameborder="0"
    ></iframe>
    <detail-bottom-bar
      selected-view="preview"
      @updatePreview="updatePreview"
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
</template>

<script lang="ts">
import Vue from 'vue';
import WCSConfigTool from 'odh-web-components-configurator/src/components/wcs-configurator.vue';
import DetailBottomBar from '~/components/detail-bottom-bar.vue';
import { WebcomponentModel } from '~/domain/model/WebcomponentModel';
import { WebcomponentConfigurationModel } from '~/domain/model/WebcomponentConfigurationModel';
import { copyToClipboard } from '~/utils/ClipboardUtils';

export default Vue.extend({
  name: 'Fullscreen',
  components: { DetailBottomBar, WCSConfigTool },
  layout(context) {
    return 'fullscreen';
  },
  data() {
    return {
      previewBaseURL: (this as any).$api.baseUrl,
      attribs: (this as any).$route.query.attribs,
    };
  },

  async fetch() {
    this.$store.commit('webcomponent/SET_EDIT_MODE', true);

    await this.$store.dispatch('webcomponent/loadWebcomponent', {
      uuid: this.$route.params.id,
      version: this.$route.params.version,
    });

    if (this.$route.query.attribs) {
      await this.$store.dispatch(
        'webcomponent/restoreSnippet',
        this.$route.query.attribs
      );
    } else {
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
  },

  methods: {
    updateSnippetFromTool(snippet: string) {
      this.$store.commit('webcomponent/SET_SNIPPET_FROM_TOOL', snippet);
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

      // newElement.contentDocument.write(this.snippet);
      newElement.contentDocument.close();
    },
    copySnippetToClipboard() {
      copyToClipboard(this.snippet);
    },
  },
});
</script>
