<template>
  <div class="container pt-3 pb-3 pl-2 pr-2">
    <vue-plyr>
      <div class="plyr__video-embed">
        <iframe :src="url" allowtransparency allow="autoplay"></iframe>
      </div>
    </vue-plyr>
  </div>
</template>

<script>
export default {
  name: 'VideoPlayer',
  props: {
    url: {
      type: String,
      default: '',
    },
  },
};
</script>
