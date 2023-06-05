<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div class="container p-4">
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
