<template>
  <div id="twrap">
    <iframe
      id="tframe"
      class="full-height full-width"
      style="margin-bottom: 60px"
      title="iframe-preview"
    ></iframe>
    <detail-bottom-bar
      :selected-view="selectedView"
      @updatePreview="updatePreview"
      @setSelectedView="setSelectedView"
      @copyCode="copySnippetToClipboard"
    >
    </detail-bottom-bar>
  </div>
</template>

<script>
import DetailBottomBar from '~/components/detail-bottom-bar';

export default {
  name: 'Fullscreen',
  components: { DetailBottomBar },
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
  mounted() {
    this.$store.dispatch('webcomponent/loadWebcomponent', {
      uuid: this.$route.params.id,
      version: this.$route.params.version,
    });
    this.updatePreview();
  },
  methods: {
    setSelectedView(newSelectedView) {
      this.selectedView = newSelectedView;
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
      this.$store.dispatch('webcomponent/updateSnipp', {
        snipp: data + '\n' + this.getDistIncludes().join('\n'),
      });

      if (this.autoUpdate) {
        this.updatePreview();
      }
    },
    copySnippetToClipboard() {
      const dummy = document.createElement('textarea');
      document.body.appendChild(dummy);
      dummy.value = this.snipp;
      dummy.select();
      document.execCommand('copy');
      document.body.removeChild(dummy);
    },
  },
};
</script>
