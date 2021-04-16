<template>
  <div id="twrap">
    <iframe
      id="tframe"
      class="full-height full-width"
      style="min-height: 800px"
      title="iframe-preview"
    ></iframe>
    <detail-bottom-bar
      :selected-view="selectedView"
      @updatePreview="updatePreview"
      @setSelectedView="setSelectedView"
    >
    </detail-bottom-bar>
    <WCSConfigTool
      v-if="config"
      :config="config.configuration"
      style="display: none"
      @snippet="updateSnippet"
    ></WCSConfigTool>
  </div>
</template>

<script>
import WCSConfigTool from 'odh-web-components-configurator/src/components/wcs-configurator';
import { webcomponentStore } from '~/utils/store-accessor';
import DetailBottomBar from '~/components/detail-bottom-bar';

export default {
  name: 'Fullscreen',
  components: { DetailBottomBar, WCSConfigTool },
  layout(context) {
    return 'fullscreen';
  },
  data() {
    return {
      selectedView: 'preview',
      autoUpdate: true,
    };
  },
  computed: {
    config() {
      return webcomponentStore.currentConfig;
    },
    component() {
      return webcomponentStore.currentWebcomponent;
    },
    snipp() {
      return webcomponentStore.currentSnipp;
    },
  },
  created() {
    webcomponentStore.loadWebcomponent({
      uuid: this.$route.params.id,
      version: this.$route.params.version,
    });
  },
  methods: {
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

      newElement.contentDocument.write(this.snipp);
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
      webcomponentStore.updateSnipp({
        snipp: data + '\n' + this.getDistIncludes().join('\n'),
      });

      if (this.autoUpdate) {
        this.updatePreview();
      }
    },
    copySnippetToClipboard() {
      const copyText = document.getElementById('code-snippet');

      copyText.select();

      document.execCommand('copy');
    },
  },
};
</script>
