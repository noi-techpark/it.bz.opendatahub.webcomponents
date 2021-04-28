<template>
  <div>
    <markdown-page :content="md"></markdown-page>
  </div>
</template>

<script>
import dataProvider from './data-provider.md';
import MarkdownPage from '~/components/markdown-page';

export default {
  name: 'DataProvider',
  components: { MarkdownPage },
  computed: {
    md() {
      return dataProvider;
    },
  },
};
</script>
