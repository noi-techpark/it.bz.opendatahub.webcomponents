<template>
  <div>
    <WcSearch></WcSearch>

    <div class="bg-secondary">
      <div id="widget-tagcloud" class="container p-5 text-center text-white">
        <h1>Categories</h1>
        <div class="font-italic text-capitalize">
          <nuxt-link v-for="tag in searchTags" :key="tag" to="/">{{
            tag
          }}</nuxt-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import WcSearch from '~/components/wc-search.vue';

export default {
  components: {
    WcSearch
  },
  data() {
    return {
      searchTags: []
    };
  },
  mounted() {
    this.loadSearchTags();
  },
  methods: {
    async loadSearchTags() {
      this.searchTags = await this.$api.searchtag.listAll();
    }
  }
};
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
