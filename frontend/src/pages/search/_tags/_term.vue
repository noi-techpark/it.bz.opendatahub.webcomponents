<template>
  <div>
    <WcSearch
      :term="searchTerm"
      v-on:term-updated="updateSearchTerm($event)"
      v-on:term-submitted="redirectSearchTerm($event)"
    />
    <WcFiltered :tags="$route.params.tags" :term="searchTerm" />
  </div>
</template>

<script>
import WcSearch from '~/components/wc-search.vue';
import WcFiltered from '~/components/wc-filtered.vue';

export default {
  components: {
    WcSearch,
    WcFiltered
  },
  data() {
    return {
      searchTerm: this.$route.params.term
    };
  },
  methods: {
    updateSearchTerm(ev) {
      console.log(ev);

      this.searchTerm = ev;
    },
    redirectSearchTerm(ev) {
      this.$router.push('/search/' + this.$route.params.tags + '/' + ev);
    }
  }
};
</script>
