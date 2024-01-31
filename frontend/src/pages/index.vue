<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div>
    <!--    <banner></banner>-->
    <div class="ml-auto header-container">
        <div :class="{ 'collapsed': !isHeaderExpanded, 'p-4 mb-5': isHeaderExpanded}" class="container container-w inner-header-container">
            <div class="text-w p-2">
            <h1>Web Components</h1>
            Here you can find already developed Web Components with data from the
            Open Data Hub, <strong>available and ready to be used.</strong> Filter
            by category and find the Web Components you need for your website or
            application, in the fields of tourism, weather, mobility and many
            more.
            </div>
            <iframe
            class="video-w"
            title="video-player"
            src="https://player.vimeo.com/video/734000845?h=2099fddb82"
            width="480"
            height="300"
            frameborder="0"
            allow="autoplay; fullscreen; picture-in-picture"
            allowfullscreen
            ></iframe>
        </div>
        <div
        class="
            container-fluid container-extended
            d-flex
            justify-content-center
            flex-row
            cursor-pointer
            toggle-header-container
        "
        @click="toggleHeaderHeight"
        >
        <div class="show-header-container" v-if="!isHeaderExpanded">
            <div class="mb-3">"How to integrate"</div>
            <span class="chevron semi-bold bottom" title="Expand"></span>
        </div>
        <div v-else>
            <span class="chevron semi-bold top" title="Collapse"></span>
        </div>
        </div>
    </div>

    <Searchbar
      :search-term="searchTerm"
      :selected-sorting="sortingCondition"
      :selected-tags="getSearchTags"
      :focus-search="true"
      @sorting-updated="redirectSearchTerm($event)"
      @tags-updated="redirectSearchTerm($event)"
      @term-submitted="redirectSearchTerm($event)"
      @term-updated="updateSearchTerm($event)"
    ></Searchbar>

    <WcHighlighted v-if="noFilters"></WcHighlighted>
    <WcFiltered
      :sorting="sortingCondition"
      :tags="searchTags"
      :term="searchTerm"
      :return-to="returnTo"
    />

  </div>
</template>

<script>
import Searchbar from '~/components/searchbar.vue';
import WcFiltered from '~/components/wc-filtered';
import WcHighlighted from '~/components/wc-highlighted';
import VueCookie from 'vue-cookie'

export default {
  components: {
    Searchbar,
    WcHighlighted,
    WcFiltered,
    VueCookie
  },

  data() {
    return {
      isHeaderExpanded: false,
      viewMode: 'filtered',
      timer: null,
    };
  },

  computed: {
    getDefaultSortingCondition(){
        // return {
        //     condition:'title',
        //     order:'asc'
        // }
        return {
            condition:null,
            order:null
        }
    },
    noFilters(){
        if ((!this.$route.query.term || this.$route.query.term == '') && (!this.$route.query.tags || this.$route.query.tags.length == 0) && (!this.$route.query.sorting || this.$route.query.sorting == '') ){
            return true;
        }
        return false;
    },
    searchTerm() {
      if (!this.$route.query.term) {
        return '';
      }
      return this.$route.query.term;
    },
    sortingCondition() {
        const defaultSortingCondition = this.getDefaultSortingCondition; 
        if (!this.$route.query.sorting) {
            return defaultSortingCondition;
        }
        let els = this.$route.query.sorting.split('|');
        if(els.length != 2){
            return defaultSortingCondition; 
        }
        
        let cond = {condition:els[0],order:els[1]}; 
        return cond;
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
          name: 'index',
          query: {
            tags: this.$route.query.tags,
          },
        });
      }

      return this.localePath({
        name: 'index',
        query: {
          tags: this.$route.query.tags,
          term: this.$route.query.term,
        },
      });
    },
  },
  mounted(){
    let cookieValue = VueCookie.get('isHeaderExpanded');
    if(cookieValue && cookieValue == 'true'){
       this.isHeaderExpanded = true; 
    }else{
        this.isHeaderExpanded = false; 
    }
  },
  methods: {
    toggleHeaderHeight() {
      this.isHeaderExpanded = !this.isHeaderExpanded;
      VueCookie.set('isHeaderExpanded', this.isHeaderExpanded, 90);
    },
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
      if (ev.tags.length === 0 && ev.term !== null && ev.term !== '') {
        tags = 'any';
      }

      let sortingCondition = ''; 
      if(ev.sorting && ev.sorting.condition !== null && ev.sorting.order !== null){
        sortingCondition = ev.sorting.condition+"|"+ev.sorting.order;
      }


      let query = {}
      if (tags) {
        query.tags = tags;
      }
      if (ev.term !== null && ev.term !== '') {
        query.term = ev.term;
      }
      if (sortingCondition !== '') {
        query.sorting = sortingCondition;
      }

      this.$router.push(
        this.localePath({
          name: 'index',
          query: query,
        })
      );
    },
  },
};
</script>

<style lang="scss">
#widget-componentcards {
  .card-footer {
    background-color: inherit;
    border-top: none;
  }
}

.header-container{
    box-shadow: 0 5px 5px -6px;

    .inner-header-container {
        
        &.collapsed {
            margin-top: -52px;
            margin-bottom: 0;
            padding-bottom: 0;
            max-height: 0;
            overflow: hidden;
        }
    }
    .toggle-header-container{
        margin-bottom: 2rem;
        padding-bottom: 1.5rem;

        .show-header-container{    
            text-align: center;
            font-size: 1rem;
            font-weight: 600;
        }
    }
}

.implode:not(:first-child)::before {
  content: ', ';
}
</style>
