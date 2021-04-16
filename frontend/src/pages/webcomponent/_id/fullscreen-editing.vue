<template>
  <div>
    <detail-bottom-bar
      :selected-view="selectedView"
      @updatePreview="updatePreview"
      @setSelectedView="setSelectedView"
    >
    </detail-bottom-bar>
  </div>
</template>

<script>
import DetailBottomBar from '~/components/detail-bottom-bar';
export default {
  name: 'FullscreenEditing.vue',
  components: { DetailBottomBar },
  layout(context) {
    return 'fullscreen';
  },
  data() {
    return {
      selectedView: 'editing',
    };
  },
  methods: {
    setSelectedView(newSelectedView) {
      this.selectedView = newSelectedView;
    },
    updatePreview() {},
  },
};
</script>
