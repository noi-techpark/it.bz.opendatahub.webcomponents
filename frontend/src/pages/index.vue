<template>
  <div>
    <banner></banner>
    <Searchbar
      @tags-updated="redirectSearchTerm($event)"
      @term-submitted="redirectSearchTerm($event)"
      @term-updated="redirectSearchTerm($event)"
    ></Searchbar>

    <WcLatest></WcLatest>
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
