<template>
  <div>
    <div>
      <div class="container container-extended p-4 pb-0">
        <div class="row">
          <div class="col-6">
            <b-form-input placeholder="Filter by categories"></b-form-input>
          </div>
          <div class="col-6">
            <b-form-input placeholder="Search custom elements"></b-form-input>
          </div>
        </div>
      </div>
    </div>

    <div class="bg-light">
      <div class="container container-extended p-4">
        <h1>Newest</h1>

        <div class="row" id="widget-componentcards">
          <div v-for="entry in allList" :key="entry.uuid" class="col-3 mb-4">
            <nuxt-link
              :to="'/component/' + entry.uuid"
              style="color:inherit;text-decoration: inherit;"
            >
              <b-card
                img-src="https://picsum.photos/600/300/?image=25"
                img-alt="Image"
                img-top
                class="full-height"
              >
                <b-card-title title-tag="div" class="h4">
                  <span class="text-secondary">#</span>{{ entry.title }}
                </b-card-title>

                <b-card-text class="text-muted">
                  {{ entry.descriptionAbstract }}
                </b-card-text>

                <div slot="footer" class="row">
                  <div class="col-6">
                    <div>
                      Author:
                      <span class="font-weight-bold">Herbert Müller</span>
                    </div>
                    <div>
                      Category: <span class="font-weight-bold">Wetter</span>
                    </div>
                  </div>
                  <div class="col-6">
                    <div>
                      Version: <span class="font-weight-bold">3.4</span>
                    </div>
                    <div>
                      License: <span class="font-weight-bold">Karibu</span>
                    </div>
                  </div>
                </div>
              </b-card>
            </nuxt-link>
          </div>
        </div>
        <div class="text-center text-secondary">
          load more components<br />
          v
        </div>
      </div>
    </div>
    <div class="bg-secondary">
      <div class="container p-5 text-center text-white" id="widget-tagcloud">
        <h1>Categories</h1>
        <div class="font-italic">
          <nuxt-link to="/">Weather</nuxt-link>
          <nuxt-link to="/">Traffic</nuxt-link>
          <nuxt-link to="/">Booking</nuxt-link>
          <nuxt-link to="/">Statistics</nuxt-link>
          <nuxt-link to="/">Events</nuxt-link>
          <nuxt-link to="/">Hotelsearch</nuxt-link>
          <nuxt-link to="/">Weather</nuxt-link>
          <nuxt-link to="/">Traffic</nuxt-link>
          <nuxt-link to="/">Booking</nuxt-link>
          <nuxt-link to="/">Statistics</nuxt-link>
          <nuxt-link to="/">Events</nuxt-link>
          <nuxt-link to="/">Hotelsearch</nuxt-link>
          <nuxt-link to="/">Weather</nuxt-link>
          <nuxt-link to="/">Traffic</nuxt-link>
          <nuxt-link to="/">Booking</nuxt-link>
          <nuxt-link to="/">Statistics</nuxt-link>
          <nuxt-link to="/">Events</nuxt-link>
          <nuxt-link to="/">Hotelsearch</nuxt-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      allList: []
    };
  },
  mounted() {
    this.loadAll();
  },
  methods: {
    async loadAll() {
      this.allList = await this.$api.webcomponent.listAll();
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
</style>
