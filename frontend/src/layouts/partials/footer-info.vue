<template>
  <div class="bg-light">
    <div class="container container-extended p-5">
      <div id="widget-imprint" class="text-center text-uppercase font-smaller">
        <nuxt-link to="/imprint" class="text-muted">imprint</nuxt-link>
        <a href="https://noi.bz.it/en/privacy-cookie-policy" class="text-muted"
          >PRIVACY & COOKIE POLICY</a
        >
      </div>
      <div class="d-flex justify-content-between mt-5">
        <div class="">
          <div class="">
            ℗ 2021 Open Data Hub,
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
          <div
            v-if="showScrollButton"
            v-scroll-to="'#__nuxt'"
            class="btn-circle btn-circle-footer arrow-up filled"
          >
            <img src="/icons/ic_arrow.svg" />
          </div>
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
};
</script>
