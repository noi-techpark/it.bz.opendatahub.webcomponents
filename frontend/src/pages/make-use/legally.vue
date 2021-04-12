<template>
  <div class="container pt-3">
    <h1>Make use legally</h1>
    <vue-markdown :source="md"></vue-markdown>
  </div>
</template>

<script>
import VueMarkdown from 'vue-markdown';
import legally from './legally.md';

export default {
  name: 'Legally',
  components: {
    VueMarkdown,
  },
  computed: {
    md() {
      return legally;
    },
  },
};
</script>
