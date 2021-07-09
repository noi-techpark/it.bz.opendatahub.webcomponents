<template>
  <div>
    <banner></banner>
    <Searchbar
      :search-term="searchTerm"
      :selected-tags="getSearchTags"
      :focus-search="true"
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
import Banner from '~/components/banner';

export default {
  components: {
    Banner,
    Searchbar,
    WcFiltered,
  },
  data() {
    return {
      timer: null,
    };
  },
  computed: {
    searchTerm() {
      if (!this.$route.query.term) {
        return '';
      }
      return this.$route.query.term;
    },
    searchTags() {
      if (!this.$route.query.tags) {
        return [];
      }
      return this.$route.query.tags.split('|');
    },
    getSearchTags() {
      return this.searchTags.filter((entry) => {
        return entry !== 'any';
      });
    },
    returnTo() {
      if (!this.$route.query.term) {
        return this.localePath({
          name: 'search',
          query: {
            tags: this.$route.query.tags,
          },
        });
      }

      return this.localePath({
        name: 'search',
        query: {
          tags: this.$route.query.tags,
          term: this.$route.query.term,
        },
      });
    },
  },
  methods: {
    updateSearchTerm(ev) {
      this.searchTerm = ev.term;
      this.searchTags = ev.tags;

      if (this.timer) {
        clearTimeout(this.timer);
      }

      this.timer = setTimeout(this.redirectSearchTerm, 300, ev);
    },
    redirectSearchTerm(ev) {
      let tags = ev.tags.join('|');
      if (ev.tags.length === 0) {
        tags = 'any';
      }

      if (ev.term !== null && ev.term !== '') {
        this.$router.push(
          this.localePath({
            name: 'search',
            query: { tags, term: ev.term },
          })
        );
      } else {
        this.$router.push(
          this.localePath({
            name: 'search',
            query: { tags },
          })
        );
      }
    },
  },
};
</script>
