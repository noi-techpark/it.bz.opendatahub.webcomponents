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
import developer from './developer.md';
import MarkdownPage from '~/components/markdown-page';

export default {
  name: 'Developer',
  components: { MarkdownPage },
  computed: {
    md() {
      return developer;
    },
  },
};
</script>
