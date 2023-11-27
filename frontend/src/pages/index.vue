<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div>
    <!--    <banner></banner>-->
    <div class="ml-auto">
      <div class="container container-w p-4 mb-5">
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
    </div>

    <Searchbar
      :search-term="searchTerm"
      :selected-tags="getSearchTags"
      :focus-search="false"
      @tags-updated="redirectSearchTerm($event)"
      @term-submitted="redirectSearchTerm($event)"
      @term-updated="updateSearchTerm($event)"
    ></Searchbar>

    <!-- <div class="container container-extended">
      <b-form-radio-group
        v-model="viewMode"
        :options="['latest', 'all']"
        buttons
        button-variant="outline-primary"
      ></b-form-radio-group>
    </div> -->

    <WcLatest v-if="latest"></WcLatest>
    <WcFiltered
      v-else
      :tags="searchTags"
      :term="searchTerm"
      :return-to="returnTo"
    />
  </div>
</template>

<script>
import Searchbar from '~/components/searchbar.vue';
import WcLatest from '~/components/wc-latest.vue';
import WcFiltered from '~/components/wc-filtered';

export default {
  components: {
    Searchbar,
    WcLatest,
    WcFiltered,
  },

  data() {
    return {
      viewMode: 'filtered',
      timer: null,
    };
  },

  computed: {
    latest() {
      return this.$route.query.latest;
    },
    searchTerm() {
      if (!this.$route.query.term) {
        return '';
      }
      return this.$route.query.term;
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

  methods: {
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

      if (ev.term !== null && ev.term !== '') {
        this.$router.push(
          this.localePath({
            name: 'index',
            query: { tags, term: ev.term },
          })
        );
      } else if (tags) {
        this.$router.push(
          this.localePath({
            name: 'index',
            query: { tags },
          })
        );
      } else {
        this.$router.push(
          this.localePath({
            name: 'index',
          })
        );
      }
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

.implode:not(:first-child)::before {
  content: ', ';
}
</style>
