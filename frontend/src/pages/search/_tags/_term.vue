<template>
  <div>
    <Searchbar
      :searchTerm="searchTerm"
      :selectedTags="getSearchTags"
      v-on:term-updated="updateSearchTerm($event)"
      v-on:term-submitted="redirectSearchTerm($event)"
      v-on:tags-updated="redirectSearchTerm($event)"
    />
    <WcFiltered :tags="searchTags" :term="searchTerm" />
  </div>
</template>

<script>
import Searchbar from '~/components/searchbar.vue';
import WcFiltered from '~/components/wc-filtered.vue';

export default {
  components: {
    Searchbar,
    WcFiltered
  },
  data() {
    return {
      searchTerm: this.$route.params.term,
      searchTags: this.$route.params.tags.split('|')
    };
  },
  computed: {
    getSearchTags() {
      return this.searchTags.filter((entry) => {
        return entry !== 'any';
      });
    }
  },
  methods: {
    updateSearchTerm(ev) {
      console.log(ev);

      this.searchTerm = ev.term;
      this.searchTags = ev.tags;
    },
    redirectSearchTerm(ev) {
      let tags = ev.tags.join('|');
      if (ev.tags.length === 0) {
        tags = 'any';
      }

      if (ev.term !== null && ev.term !== '') {
        this.$router.push(
          this.localePath({
            name: 'search-tags-term',
            params: { tags, term: ev.term }
          })
        );
      } else {
        this.$router.push(
          this.localePath({
            name: 'search-tags',
            params: { tags }
          })
        );
      }
    }
  }
};
</script>
