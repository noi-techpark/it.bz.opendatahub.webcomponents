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
