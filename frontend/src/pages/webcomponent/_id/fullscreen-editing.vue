<template>
  <div class="d-flex flex-row">
    <div id="twrap" style="width: 50%">
      <iframe
        id="tframe"
        class="full-height full-width"
        style="min-height: 100vh"
        title="iframe-preview"
      ></iframe>
      <detail-bottom-bar
        :selected-view="selectedView"
        @updatePreview="updatePreview"
        @setSelectedView="setSelectedView"
        @copyCode="copySnippetToClipboard"
      >
      </detail-bottom-bar>
      <WCSConfigTool
        v-if="config"
        :config="config.configuration"
        style="display: none"
        @snippet="updateSnippet"
      ></WCSConfigTool>
    </div>
    <prism-editor
      v-model="code"
      class="my-editor pt-4"
      :highlight="highlighter"
      :line-numbers="true"
      style="border: 0; background-color: inherit; width: 50%"
    ></prism-editor>
  </div>
</template>

<script>
import WCSConfigTool from 'odh-web-components-configurator/src/components/wcs-configurator';
import { PrismEditor } from 'vue-prism-editor';
import DetailBottomBar from '~/components/detail-bottom-bar';

import 'vue-prism-editor/dist/prismeditor.min.css'; // import the styles somewhere
// eslint-disable-next-line import/order
import { highlight, languages } from 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-markup';
import 'prismjs/themes/prism.css';

export default {
  name: 'FullscreenEditing',
  components: { DetailBottomBar, WCSConfigTool, PrismEditor },
  layout(context) {
    return 'fullscreen';
  },
  data() {
    return {
      selectedView: 'editing',
      autoUpdate: true,
      code: '',
    };
  },
  computed: {
    component() {
      return this.$store.getters['webcomponent/currentWebcomponent'];
    },
    config() {
      return this.$store.getters['webcomponent/currentConfig'];
    },
    snipp() {
      return this.$store.getters['webcomponent/currentSnipp'];
    },
  },
  created() {
    this.$store.dispatch('webcomponent/loadWebcomponent', {
      uuid: this.$route.params.id,
      version: this.$route.params.version,
    });
    this.code = this.snipp;
  },
  methods: {
    highlighter(code) {
      return highlight(code, languages.markup); // returns html
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
      newElement.setAttribute('style', 'min-height: 100vh;');
      newElement.setAttribute('frameborder', '0');

      document.getElementById('twrap').appendChild(newElement);

      newElement.contentDocument.write(this.code);
      newElement.contentDocument.close();
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
      const dummy = document.createElement('textarea');
      document.body.appendChild(dummy);
      dummy.value = this.code;
      dummy.select();
      document.execCommand('copy');
      document.body.removeChild(dummy);
    },
  },
};
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
