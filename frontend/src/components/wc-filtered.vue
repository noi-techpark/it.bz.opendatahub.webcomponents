<template>
  <div>
    <div class="bg-light">
      <div class="container container-extended p-4">
        <div
          class="d-flex justify-content-between align-items-center pb-2 flex-wrap"
        >
          <h1 class="components-title">
            {{ currentPage.totalElements }} components
          </h1>
          <div>
            <Paginator
              :current-page="currentPage.number"
              :total-pages="currentPage.totalPages"
              @page-select="toPage($event)"
            />
          </div>
          <div class="d-flex flex-row">
            <div
              :class="{ disabled: isFirst }"
              class="btn-circle arrow-left outline mr-2"
              @click="previousPage()"
            >
              <svg
                id="Ebene_1"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                x="0px"
                y="0px"
                width="48px"
                height="45px"
                viewBox="0 0 48 45"
                style="enable-background: new 0 0 48 45"
                xml:space="preserve"
              >
                <style type="text/css">
                  .st0 {
                    fill: #fff;
                  }
                </style>
                <polygon
                  class="st0"
                  points="21.3,1.3 22.7,2.7 4.4,21 47,21 47,23 4.4,23 23.7,42.3 22.3,43.7 0.6,22 "
                />
              </svg>
            </div>
            <div
              :class="{ disabled: isLast }"
              class="btn-circle arrow-right outline"
              @click="nextPage()"
            >
              <svg
                id="Ebene_1"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                x="0px"
                y="0px"
                width="48px"
                height="45px"
                viewBox="0 0 48 45"
                style="enable-background: new 0 0 48 45"
                xml:space="preserve"
              >
                <style type="text/css">
                  .st0 {
                    fill: #fff;
                  }
                </style>
                <polygon
                  class="st0"
                  points="21.3,1.3 22.7,2.7 4.4,21 47,21 47,23 4.4,23 23.7,42.3 22.3,43.7 0.6,22 "
                />
              </svg>
            </div>
          </div>
        </div>

        <div v-if="hasContent" id="widget-componentcards" class="row">
          <div
            v-for="entry in currentPage.content"
            :key="entry.uuid"
            class="col-md-6 col-lg-4 col-xl-3 mb-4"
          >
            <WebcomponentEntryCard :entry="entry" :return-to="returnTo" />
          </div>
        </div>
        <div v-else class="container text-center h1">
          Your search came up empty.
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { PageRequest } from '../domain/repository/PagingAndSorting';
import Paginator from '~/components/paginator.vue';
import WebcomponentEntryCard from '~/components/webcomponent-entry-card.vue';

export default Vue.extend({
  components: {
    Paginator,
    WebcomponentEntryCard,
  },
  props: {
    tags: {
      default: () => {
        return [];
      },
      type: Array,
    },
    term: { default: '', type: String },
    returnTo: {
      default: null,
      type: String,
    },
  },
  data() {
    return {
      pageSize: 24,
      currentPageNumber: 0,
      timer: null,
    };
  },
  fetch() {},
  computed: {
    hasContent() {
      return !this.currentPage.empty;
    },
    isFirst() {
      return this.currentPage.first;
    },
    isLast() {
      return this.currentPage.last;
    },
    currentPage() {
      return this.$store.getters['webcomponent-list/getLoadedPage'];
    },
  },
  watch: {
    term(newVal, oldVal) {
      if (this.timer) {
        clearTimeout(this.timer);
        this.timer = null;
      }

      this.timer = setTimeout(this.update, 350);
    },
    tags(newVal, oldVal) {
      if (this.timer) {
        clearTimeout(this.timer);
        this.timer = null;
      }

      this.timer = setTimeout(this.update, 350);
    },
  },
  created() {
    this.loadPage(this.currentPageNumber, this.pageSize);
  },
  methods: {
    toPage(page) {
      this.currentPageNumber = page;
      this.loadPage(this.currentPageNumber, this.pageSize);
    },
    nextPage() {
      if (this.isLast === false) {
        this.loadPage(++this.currentPageNumber, this.pageSize);
      }
    },
    previousPage() {
      if (this.isFirst === false) {
        this.loadPage(--this.currentPageNumber, this.pageSize);
      }
    },
    update() {
      this.currentPageNumber = 0;
      this.loadPage(this.currentPageNumber, this.pageSize);
    },
    loadPage(pageNumber, pageSize) {
      let term = '';
      if (this.term) {
        term = this.term;
      }

      let tags = '';
      let filtered = [];
      if (Array.isArray(this.tags)) {
        filtered = this.tags.filter((elem) => {
          return elem !== 'any';
        });
        tags = filtered.join(',');
      }

      this.$store.dispatch('webcomponent-list/loadPage', {
        pageRequest: new PageRequest(pageSize, pageNumber),
        filter: {
          tags,
          searchTerm: term,
        },
      });
    },
  },
});
</script>

<style lang="scss">
@media (max-width: 768px) {
  .components-title {
    width: 100%;
  }
}
</style>
