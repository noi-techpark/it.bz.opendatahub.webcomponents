<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div v-if="visibleWebcomponents.length > 0">
    <div class="bg-light">
      <div class="container container-extended p-4">
        <div class="d-flex justify-content-between align-items-center">
          <h1
            class="components-title d-flex flex-column"
            style="min-width: 40%"
          >
            highlighted
          </h1>
        </div>

        <div id="widget-componentcards" class="row">
          <div
            v-for="entry in visibleWebcomponents"
            :key="entry.uuid"
            class="col-md-6 col-lg-6 col-xl-4 mb-4"
          >
            <WebcomponentEntryCard :entry="entry" uuKey="highlighted"  />
          </div>
        </div>
        <!-- <div class="text-center">
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
              >show alphabetically
              <span class="chevron right ml-2"></span>
            </span>
          </nuxt-link>
        </div> -->
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
      listLimit: 1000,
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
        
        //dev
        item.highlighted = true;
        // console.log(item)
        
        return (item.highlighted && index < 3)
      });
    },
  },
  mounted() {
    this.$store.dispatch('webcomponent-list/loadPage', {
      pageRequest: new PageRequest(500, 0),
      filter: {
        tags: null,
        searchTerm: null,
        highlighted: true,
      },
    });
  },
  methods: {

  },
});
</script>
