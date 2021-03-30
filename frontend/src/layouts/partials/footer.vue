<template>
  <footer>
    <div class="get-in-touch">
      <div class="container p-5 text-center font-larger">
        <div class="text-uppercase h1 footer-text">get in touch</div>
        <hr style="width: 10%; background-color: white" />
        <div class="h1 footer-text">
          If you would like to become a member of the Open Data Hub Community as
          a Data Provider, Data Consumer, Web Component User or as a
          Contributer, you can meet us at our events, or get in touch with us
          through e-mail or contact form.
        </div>
      </div>
    </div>
    <div class="bg-light">
      <div class="container container-extended p-5">
        <div id="widget-imprint" class="text-center text-uppercase">
          <nuxt-link to="/imprint" class="text-muted">imprint</nuxt-link>
          <a
            href="https://noi.bz.it/en/privacy-cookie-policy"
            class="text-muted"
            >PRIVACY & COOKIE POLICY</a
          >
        </div>
        <div class="d-flex justify-content-between mt-5">
          <div class="font-smaller">
            <div class="font-weight-bold">
              Open Data Hub,
              <a href="mailto:info@opendatahub.bz.it">info@opendatahub.bz.it</a>
            </div>
            <a href="https://konverto.eu" target="_blank" class="text-muted"
              >web by KONVERTO</a
            >
          </div>
          <div>
            <div v-scroll-to="'#__nuxt'" class="btn-circle arrow-up filled">
              <img src="/icons/ic_arrow.svg" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<style lang="scss">
@import 'src/assets/styles/bootstrap/_variables.scss';
#widget-imprint {
  a {
    margin-left: 1rem;
    margin-right: 1rem;
  }
}

.get-in-touch {
  background-color: $secondary;
}

.footer-text {
  color: white;
}
</style>
