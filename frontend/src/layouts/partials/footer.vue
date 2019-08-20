<template>
  <footer>
    <div class="container p-5 text-center font-larger">
      <div class="text-uppercase">get in touch</div>
      <hr />
      <div class="text-muted">
        If you would like to become a member of the Open Data Hub Community as a
        Data Provider,
      </div>
      <div class="text-muted">
        Data Consumer or as a Contributer, you can meet us at our events or get
        in touch with us through e-mail.
      </div>

      <div class="mt-4 text-muted">
        Write us an e-mail to
        <a href="" class="text-secondary">help@opendatahub.bz.it</a>!
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
              &copy; 2019 Open Data Hub,
              <a href="mailto:info@opendatahub.bz.it">info@opendatahub.bz.it</a>
            </div>
            <a href="https://konverto.eu" target="_blank" class="text-muted"
              >web by KONVERTO</a
            >
          </div>
          <div>
            <div
              onclick="window.scrollTo(0,0);"
              class="btn-circle arrow-up filled"
            >
              <img src="/icons/ic_arrow.svg" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<style lang="scss">
#widget-imprint {
  a {
    margin-left: 1rem;
    margin-right: 1rem;
  }
}
</style>
