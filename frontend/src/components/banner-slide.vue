<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <b-carousel-slide
    :caption="title"
    img-alt="image"
    :img-src="imgUrl"
    class="banner-slide"
  >
    <p>
      {{ subtitle }}
    </p>
    <nuxt-link :to="localePath(path)" class="text-secondary">{{
      linkText
    }}</nuxt-link>
  </b-carousel-slide>
</template>

<script>
export default {
  name: 'BannerSlide',
  props: {
    imgUrl: {
      type: String,
      default: '',
    },
    title: {
      type: String,
      default: '',
    },
    subtitle: {
      type: String,
      default: '',
    },
    path: {
      type: String,
      default: '',
    },
    linkText: {
      type: String,
      default: '',
    },
  },
};
</script>
