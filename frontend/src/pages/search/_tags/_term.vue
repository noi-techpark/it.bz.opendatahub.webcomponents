<template>
  <div>
    <WcSearch
      :term="searchTerm"
      :selectedTags="searchTags"
      v-on:term-updated="updateSearchTerm($event)"
      v-on:term-submitted="redirectSearchTerm($event)"
    />
    <WcFiltered :tags="searchTags" :term="searchTerm" />
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
      searchTerm: this.$route.params.term,
      searchTags: this.$route.params.tags.split(',')
    };
  },
  methods: {
    updateSearchTerm(ev) {
      console.log(ev);

      this.searchTerm = ev.term;
      this.searchTags = ev.tags;
    },
    redirectSearchTerm(ev) {
      this.$router.push('/search/' + this.$route.params.tags + '/' + ev.term);
    }
  }
};
</script>
