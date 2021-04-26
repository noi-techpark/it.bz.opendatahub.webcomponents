<template>
  <div>
    <loading
      :active.sync="isLoading"
      :z-index="999999"
      color="#61853b"
      background-color="#020202"
      :opacity="0.7"
    ></loading>
    <Header />
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

import Header from './partials/header';
import Footer from './partials/footer';

export default Vue.extend({
  components: {
    Header,
    Footer,
    Loading: Loading as any,
  },
  computed: {
    ...mapGetters('loader', ['isLoading']),
  },
  created() {
    this.$store.dispatch('searchtags/loadSearchtags');
  },
});
</script>
