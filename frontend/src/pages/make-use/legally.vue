<template>
  <markdown-page :content="md"></markdown-page>
</template>

<script>
import legally from './legally.md';
import MarkdownPage from '~/components/markdown-page';

export default {
  name: 'Legally',
  components: { MarkdownPage },
  computed: {
    md() {
      return legally;
    },
  },
};
</script>
