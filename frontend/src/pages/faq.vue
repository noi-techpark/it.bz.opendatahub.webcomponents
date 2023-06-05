<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div>
    <markdown-page :content="md"></markdown-page>
  </div>
</template>

<script>
import faq from './faq.md';
import MarkdownPage from '~/components/markdown-page';

export default {
  name: 'Faq',
  components: { MarkdownPage },
  computed: {
    md() {
      return faq;
    },
  },
};
</script>
