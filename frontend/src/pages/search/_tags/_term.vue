<template>
  <div>
    <Searchbar
      :search-term="searchTerm"
      :selected-tags="getSearchTags"
      @term-updated="updateSearchTerm($event)"
      @term-submitted="redirectSearchTerm($event)"
      @tags-updated="redirectSearchTerm($event)"
    />
    <WcFiltered :tags="searchTags" :term="searchTerm" :return-to="returnTo" />
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
    },
    returnTo() {
      console.log('x', this.$route.params.tags, this.$route.params.term);

      if (!this.$route.params.term) {
        return this.localePath({
          name: 'search-tags',
          params: {
            tags: this.$route.params.tags
          }
        });
      }

      return this.localePath({
        name: 'search-tags-term',
        params: {
          tags: this.$route.params.tags,
          term: this.$route.params.term
        }
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
