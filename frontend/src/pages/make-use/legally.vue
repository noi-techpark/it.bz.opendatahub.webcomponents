<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div>
    <markdown-page :content="md"></markdown-page>
    <video-player
      url="https://www.youtube.com/watch?v=7MWRdge6LN8"
    ></video-player>
  </div>
</template>

<script>
import legally from './legally.md';
import MarkdownPage from '~/components/markdown-page';
import VideoPlayer from '~/components/video-player';

export default {
  name: 'Legally',
  components: { VideoPlayer, MarkdownPage },
  computed: {
    md() {
      return legally;
    },
  },
};
</script>
