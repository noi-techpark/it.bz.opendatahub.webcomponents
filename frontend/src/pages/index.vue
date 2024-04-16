<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div>
    <div class="ml-auto header-container">
      <div class="container container-w inner-header-container text-center">
        <div class="text-w mb-2">
          Explore the collection of pre-developed Web Components from the
          <a
            href="https://opendatahub.com/"
            style="font-weight: bold; text-decoration: underline"
            >Open Data Hub</a
          >, designed for seamless integration into your projects. Filter by
          category to find the ideal Web Components for your website or
          application, spanning various domains such as tourism, weather,
          mobility, and beyond.
        </div>
      </div>
      <div class="container container-w d-flex justify-content-center">
        <div>
          <a href="/how-to-add" target="_blank" class="btn btn-primary mb-2">
            Add a webcomponent
          </a>
        </div>
        <div>
          <a href="/how-to-create" target="_blank" class="btn btn-primary">
            <!-- Update this link to point to the appropriate page on your website -->
            Create a webcomponent
          </a>
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
import VueCookie from 'vue-cookie';

export default {
  components: {
    Searchbar,
    WcHighlighted,
    WcFiltered,
    VueCookie,
  },

  data() {
    return {
      isHeaderExpanded: false,
      viewMode: 'filtered',
      timer: null,
    };
  },

  computed: {
    getDefaultSortingCondition() {
      // return {
      //     condition:'title',
      //     order:'asc'
      // }
      return {
        condition: null,
        order: null,
      };
    },
    noFilters() {
      if (
        (!this.$route.query.term || this.$route.query.term == '') &&
        (!this.$route.query.tags || this.$route.query.tags.length == 0) &&
        (!this.$route.query.sorting || this.$route.query.sorting == '')
      ) {
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
      if (els.length != 2) {
        return defaultSortingCondition;
      }

      let cond = { condition: els[0], order: els[1] };
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
  mounted() {
    let cookieValue = VueCookie.get('isHeaderExpanded');
    if (cookieValue && cookieValue == 'true') {
      this.isHeaderExpanded = true;
    } else {
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
      if (
        ev.sorting &&
        ev.sorting.condition !== null &&
        ev.sorting.order !== null
      ) {
        sortingCondition = ev.sorting.condition + '|' + ev.sorting.order;
      }

      let query = {};
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

.header-container {
  // box-shadow: 0 5px 5px -6px;

  .inner-header-container {
    &.collapsed {
      margin-top: -52px;
      margin-bottom: 0;
      padding-bottom: 0;
      max-height: 0;
      overflow: hidden;
    }
  }
  .toggle-header-container {
    margin-bottom: 2rem;
    padding-bottom: 1.5rem;

    .show-header-container {
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
