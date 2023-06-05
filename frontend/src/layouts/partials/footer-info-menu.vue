<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div class="bg-light">
    <div class="container container-extended p-5">
      <div class="d-flex flex-column text-uppercase">
        <nuxt-link to="/imprint" class="text-muted">imprint</nuxt-link>
        <a
          href="https://noi.bz.it/en/privacy-cookie-policy"
          class="text-muted"
          target="_blank"
          >PRIVACY & COOKIE POLICY</a
        >
      </div>
      <div class="d-flex justify-content-between mt-5">
        <div class="font-smaller">
          <div class="">
            ℗ 2021 Open Data Hub,
            <a href="mailto:info@opendatahub.com">info@opendatahub.com</a>
          </div>
          <a href="https://konverto.eu" target="_blank" class="text-muted"
            >web by KONVERTO</a
          >
        </div>
        <div>
          <div
            v-if="showScrollButton"
            v-scroll-to="'body'"
            class="btn-circle btn-circle-footer arrow-up filled"
          >
            <img src="/icons/ic_arrow.svg" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'FooterInfoMenu',
  props: {
    showScrollButton: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    scrollToTop() {
      window.scrollTo({ top: 0 });
    },
  },
};
</script>
