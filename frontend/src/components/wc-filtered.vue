<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div>
    <div class="bg-light">
      <div class="container container-extended p-4">
        <h1 class="components-title d-flex flex-column" style="min-width: 40%">
          <span v-if="noFilters">all components</span>
          <span v-else>components found </span>
          <span style="font-size: small">
            {{ currentPage.totalElements }} components {{ getFilteringString }}
            <!-- <nuxt-link
              :to="
                localePath({
                  query: {
                    latest: true,
                  },
                })
              "
              >&gt;show newest</nuxt-link
            > -->
            </span>
        </h1>

        <div v-if="hasContent" id="widget-componentcards" class="row">
          <div
            v-for="entry in currentPage.content"
            :key="entry.uuid"
            class="col-md-6 col-lg-6 col-xl-4 mb-4" style="position: relative; z-index: 1;"
          >
            <WebcomponentEntryCard :entry="entry" :return-to="returnTo" uuKey="filtered" />
          </div>
        </div>
        <div v-else class="container text-center" style="height: 400px">
          <h1>Your search came up empty.</h1>
        </div>
        <!-- <paging-row
          :current-page="currentPage"
          :is-first="isFirst"
          :is-last="isLast"
          @toPage="toPage"
          @previousPage="previousPage"
          @nextPage="nextPage"
        ></paging-row> -->
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { PageRequest } from '../domain/repository/PagingAndSorting';
// import PagingRow from './paging-row.vue';
import WebcomponentEntryCard from '~/components/webcomponent-entry-card.vue';

export default Vue.extend({
  components: {
    // PagingRow,
    WebcomponentEntryCard,
  },
  props: {
    sorting: {
      default: () => {
        return {};
      },
      type: Object,
    },
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
      pageSize: 1000,
      currentPageNumber: 0,
      timer: null,
    };
  },
  fetch() {},
  computed: {
    getFilteringString(){
        let filteringStr = '';
        if(this.term && this.term !== ''){
            filteringStr += '"'+this.term+'"';
        }
        if(this.tags && this.tags.length !== 0 && this.tags[0] !== 'any'){
            if(filteringStr !== ''){
                filteringStr += ', ';
            }
            filteringStr += this.tags.join(', ')
        }

        if(filteringStr == ''){
            return ''
        }else{
            return 'filtered by '+filteringStr
        }
    },
    noFilters(){
        if ((!this.term || this.term == '') && (!this.tags || this.tags.length == 0) && (!this.sorting || (this.sorting.condition == null && this.sorting.order == null)) ){
            return true;
        }
        return false;
    },
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
  mounted() {
    this.loadPage(this.currentPageNumber, this.pageSize);
  },
  methods: {
    // toPage(page) {
    //   this.currentPageNumber = page;
    //   this.loadPage(this.currentPageNumber, this.pageSize);
    // },
    // nextPage() {
    //   if (this.isLast === false) {
    //     this.loadPage(++this.currentPageNumber, this.pageSize);
    //   }
    // },
    // previousPage() {
    //   if (this.isFirst === false) {
    //     this.loadPage(--this.currentPageNumber, this.pageSize);
    //   }
    // },
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
        sorting:this.sorting,
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
