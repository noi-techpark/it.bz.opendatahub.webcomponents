<template>
  <div class="container pt-3">
    <h1>Make use legally</h1>
    <vue-markdown :source="content"></vue-markdown>
  </div>
</template>

<script>
import VueMarkdown from 'vue-markdown';

export default {
  name: 'MarkdownPage',
  components: {
    VueMarkdown,
  },
  props: {
    content: {
      type: String,
      default: '',
    },
  },
};
</script>
