<template>
  <div>
    <Header />
    <main>
      <nuxt />
    </main>
    <Footer />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';

import Header from './partials/header';
import Footer from './partials/footer';

export default Vue.extend({
  components: {
    Header,
    Footer,
  },
  created() {
    this.$store.dispatch('searchtags/loadSearchtags');
  },
});
</script>
