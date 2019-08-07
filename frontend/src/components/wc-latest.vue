<template>
  <div>
    <div class="bg-light">
      <div class="container container-extended p-4">
        <div class="float-right font-weight-bold">
          1 &mdash; {{ allList.length }}
        </div>
        <h1>Newest</h1>

        <div id="widget-componentcards" class="row">
          <div
            v-for="entry in allList"
            :key="entry.uuid"
            class="col-sm-6 col-md-4 col-lg-3 mb-4"
          >
            <WcCard :entry="entry" />
          </div>
        </div>
        <div class="text-center">
          <a
            v-if="currentPage < maxPage"
            href="javascript: void(0);"
            class="text-secondary d-flex flex-column text-decoration-none"
            @click="loadMore()"
          >
            <span>load more components</span>
            <span class="chevron bottom bold"></span>
          </a>
          <nuxt-link
            v-else
            to="#"
            class="text-secondary d-flex flex-column text-decoration-none"
          >
            <span
              >show all components
              <span class="chevron right bold ml-2"></span>
            </span>
          </nuxt-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import WcCard from '~/components/wc-card.vue';

export default {
  components: {
    WcCard
  },
  data() {
    return {
      pageSize: 8,
      currentPage: 0,
      maxPage: 0,
      allList: []
    };
  },
  mounted() {
    this.loadAll();
  },
  methods: {
    async loadAll() {
      const page = await this.$api.webcomponent.listAllPaged(
        this.currentPage,
        this.pageSize
      );

      this.maxPage = 1; // Math.min(1, page.totalPages - 1);

      this.allList = page.content;
    },
    async loadMore() {
      // this.currentPage++;

      const page = await this.$api.webcomponent.listAllPaged(
        ++this.currentPage,
        this.pageSize
      );

      this.allList = this.allList.concat(page.content);
    }
  }
};
</script>

<style></style>
