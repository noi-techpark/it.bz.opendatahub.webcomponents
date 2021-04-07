<template>
  <div>
    <banner></banner>
    <Searchbar
      @tags-updated="redirectSearchTerm($event)"
      @term-submitted="redirectSearchTerm($event)"
      @term-updated="redirectSearchTerm($event)"
    ></Searchbar>

    <WcLatest></WcLatest>

    <div class="bg-secondary">
      <div id="widget-tagcloud" class="container p-5 text-center text-white">
        <h1>Categories</h1>
        <div
          class="font-italic text-capitalize d-flex flex-wrap justify-content-center"
        >
          <nuxt-link
            v-for="tag in searchTags"
            :key="tag"
            :to="
              localePath({
                name: 'search-tags',
                params: { tags: tag }
              })
            "
            >{{ tag }}</nuxt-link
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Searchbar from '~/components/searchbar.vue'
import WcLatest from '~/components/wc-latest.vue'
import Banner from '~/components/banner'

export default {
  components: {
    Banner,
    Searchbar,
    WcLatest
  },
  data() {
    return {
      searchTags: []
    }
  },
  mounted() {
    this.loadSearchTags()
  },
  methods: {
    async loadSearchTags() {
      this.searchTags = await this.$api.searchtag.listAll()
    },
    redirectSearchTerm(ev) {
      let tags = ev.tags.join('|')
      if (ev.tags.length === 0) {
        tags = 'any'
      }

      if (ev.term !== null && ev.term !== '') {
        this.$router.push(
          this.localePath({
            name: 'search-tags-term',
            params: { tags, term: ev.term }
          })
        )
      } else {
        this.$router.push(
          this.localePath({
            name: 'search-tags',
            params: { tags }
          })
        )
      }
    }
  }
}
</script>

<style lang="scss">
#widget-componentcards {
  .card-footer {
    background-color: inherit;
    border-top: none;
  }
}

.implode:not(:first-child)::before {
  content: ', ';
}
</style>
