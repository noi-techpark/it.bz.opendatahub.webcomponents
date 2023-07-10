<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div>
    <loading
      :active.sync="isLoading"
      :z-index="999999"
      color="#61853b"
      background-color="#020202"
      :opacity="0.7"
    ></loading>
    <Nav />
    <main>
      <nuxt />
    </main>
    <Footer />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';

import { mapGetters } from 'vuex';

import Loading from 'vue-loading-overlay';

import Nav from './partials/nav.vue';
import Footer from './partials/footer.vue';

export default Vue.extend({
  components: {
    Nav,
    Footer,
    Loading: Loading as any,
  },
  computed: {
    ...mapGetters('loader', ['isLoading']),
  },
  mounted() {
    this.$store.dispatch('searchtags/loadSearchtags');
  },
});
</script>
