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
  </div>
</template>

<script>
import { webcomponentStore } from '~/utils/store-accessor';
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
    };
  },
  computed: {
    component() {
      return webcomponentStore.currentWebcomponent;
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
  },
};
</script>
