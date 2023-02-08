<template>
  <footer>
    <!--<div v-if="$route.path === '/'" class="p-5 text-center container">
      <h1 class="text-uppercase">#Publishers #Contributors #Partners</h1>
      <hr style="width: 50px; background-color: white" />
      <b-row class="quote-row">
        <b-col cols="6" lg="3" md="6" sm="6" style="display: flex">
          <quote-item
            title="Contributing"
            image-src="/icons/publishers/publisher4.png"
            quote="Often the best way to get them is to put our hands into starting new
            projects or contributing to existing ones."
          ></quote-item>
        </b-col>
        <b-col cols="6" lg="3" md="6" sm="6" style="display: flex">
          <quote-item
            title="Sharing"
            image-src="/icons/publishers/publisher2.png"
            quote="There are several reasons you might want to contribute to open source software."
          ></quote-item>
        </b-col>
        <b-col cols="6" lg="3" md="6" sm="6" style="display: flex">
          <quote-item
            title="Knowledge"
            image-src="/icons/publishers/publisher3.png"
            quote="It's useful regardless of whether or not you're a student."
          ></quote-item>
        </b-col>
        <b-col cols="6" lg="3" md="6" sm="6" style="display: flex">
          <quote-item
            title="Networking"
            image-src="/icons/publishers/publisher1.png"
            quote="The fulfilling feeling of giving back and contributing to society is unparalleled."
          ></quote-item>
        </b-col>
      </b-row>
      <nuxt-link :to="localePath('community')" class="text-secondary"
        >learn more about our community</nuxt-link
      >
    </div>-->
    <div class="get-in-touch">
      <div class="p-1 p-md-5 text-center font-larger">
        <nuxt-link :to="localePath('/contact')"
          ><h2 class="pt-5 text-uppercase footer-text">
            get in touch
          </h2></nuxt-link
        >
        <hr style="width: 50px; background-color: white" />
        <h3 class="footer-text text pb-5">
          If you would like to become a member of the Open Data Hub Community as
          a Data Provider, Data Consumer, Web Component User or as a
          Contributor, you can meet us at our events, or get in touch with us
          through e-mail or contact form.
        </h3>
      </div>
    </div>
    <div class="bg-light" hidden>
      <div id="widget-tagcloud" class="container p-5 text-center">
        <h1>Categories</h1>
        <div
          class="
            font-italic
            text-capitalize
            d-flex
            flex-wrap
            justify-content-center
          "
        >
          <nuxt-link
            v-for="tag in searchTags"
            :key="tag"
            :to="
              localePath({
                name: 'search',
                query: { tags: tag },
              })
            "
            class="category"
            >{{ tag }}</nuxt-link
          >
        </div>
      </div>
    </div>
    <footer-info :show-scroll-button="true"></footer-info>
  </footer>
</template>

<script>
import FooterInfo from '~/layouts/partials/footer-info';

export default {
  components: { FooterInfo },
  computed: {
    searchTags() {
      return this.$store.getters['searchtags/getSearchtags'];
    },
  },
};
</script>

<style lang="scss">
@import 'src/assets/styles/bootstrap/_variables.scss';
#widget-imprint {
  a {
    margin-left: 1rem;
    margin-right: 1rem;
  }
}

.get-in-touch {
  background-color: #000;
}

.get-in-touch .pt-5 {
  padding-top: 1rem !important;
}

.get-in-touch .pb-5 {
  font-weight: normal !important;
  padding-bottom: 1rem !important;
}

.footer-text {
  color: white;
  margin-left: 10%;
  margin-right: 10%;
}

.quote-row {
  margin-top: 30px;
  margin-bottom: 50px;
}
</style>
