<template>
  <div>
    <markdown-page :content="md"></markdown-page>
  </div>
</template>

<script>
import technically from './technically.md';
import MarkdownPage from '~/components/markdown-page';

export default {
  name: 'Technically',
  components: { MarkdownPage },
  computed: {
    md() {
      return technically;
    },
  },
};
</script>
