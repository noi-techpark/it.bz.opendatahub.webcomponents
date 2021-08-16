<template>
  <div>
    <div class="bg-light">
      <div class="container container-extended p-4">
        <div class="d-flex justify-content-between align-items-center">
          <h1>Newest</h1>
          <div class="font-weight-bold d-flex align-items-center">
            1
            <div
              class="m-2"
              style="width: 58px; height: 1px; background-color: #707070"
            ></div>
            {{ visibleWebcomponents.length }}
            <nuxt-link
              :to="localePath({ name: 'index', query: { tags: 'any' } })"
              class="d-flex flex-column text-decoration-none pl-3"
            >
              <span data-testid="show-all">show all </span>
            </nuxt-link>
          </div>
        </div>

        <div id="widget-componentcards" class="row">
          <div
            v-for="entry in visibleWebcomponents"
            :key="entry.uuid"
            class="col-md-6 col-lg-4 col-xl-3 mb-4"
          >
            <WebcomponentEntryCard :entry="entry" />
          </div>
        </div>
        <div class="text-center">
          <a
            v-if="!moreEnabled"
            href="javascript: void(0);"
            class="d-flex flex-column text-decoration-none"
            @click="loadMore()"
          >
            <span data-testid="load-more">load more components</span>
            <span class="chevron bottom"></span>
          </a>
          <nuxt-link
            v-else
            :to="localePath({ name: 'index', query: { tags: 'any' } })"
            class="d-flex flex-column text-decoration-none"
          >
            <span data-testid="show-all"
              >show all components
              <span class="chevron right ml-2"></span>
            </span>
          </nuxt-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { PageRequest } from '../domain/repository/PagingAndSorting';
import WebcomponentEntryCard from '~/components/webcomponent-entry-card.vue';

export default Vue.extend({
  components: {
    WebcomponentEntryCard,
  },
  data() {
    return {
      listLimit: 8,
      moreEnabled: false,
    };
  },
  computed: {
    dummies() {
      return [{}, {}, {}, {}, {}, {}, {}, {}];
    },
    visibleWebcomponents() {
      if (this.$store.getters['webcomponent-list/getLoadedPage'].empty) {
        return this.dummies;
      }

      return this.$store.getters[
        'webcomponent-list/getLoadedPage'
      ].content.filter((item, index) => {
        return index < this.listLimit;
      });
    },
  },
  mounted() {
    this.$store.dispatch('webcomponent-list/loadPage', {
      pageRequest: new PageRequest(16, 0),
      filter: {
        tags: null,
        searchTerm: null,
        latest: true,
      },
    });
  },
  methods: {
    loadMore() {
      this.listLimit = 16;
      this.moreEnabled = true;
    },
  },
});
</script>
