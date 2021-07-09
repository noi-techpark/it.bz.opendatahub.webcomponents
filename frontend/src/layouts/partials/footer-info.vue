<template>
  <div class="bg-light">
    <div class="container container-extended p-5">
      <div id="widget-imprint" class="text-center text-uppercase font-smaller">
        <nuxt-link to="/imprint" class="text-muted">imprint</nuxt-link>
        <a
          href="https://noi.bz.it/en/privacy-cookie-policy"
          class="text-muted"
          target="_blank"
          >PRIVACY & COOKIE POLICY</a
        >
      </div>
      <div class="d-flex justify-content-between mt-5">
        <div class="">
          <div class="">
            &copy; {{ currentYear }} Open Data Hub,
            <a href="mailto:info@opendatahub.bz.it">info@opendatahub.bz.it</a>
          </div>
          <a
            href="https://konverto.eu"
            target="_blank"
            class="text-muted font-smaller"
            >web by KONVERTO</a
          >
        </div>
        <div>
          <a
            v-if="showScrollButton"
            v-scroll-to="'body'"
            class="btn-circle arrow-up filled btn-circle-footer"
            :class="{ 'btn-circle-footer-detail': isWebcomponentRoute }"
          >
            <img src="/icons/ic_arrow.svg" class="return-icon-up" />
          </a>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'FooterInfo',
  props: {
    showScrollButton: {
      type: Boolean,
      default: false,
    },
  },
  computed: {
    currentYear() {
      return new Date().getFullYear();
    },
    isWebcomponentRoute() {
      return this.$route.path.includes('webcomponent');
    },
  },
};
</script>
