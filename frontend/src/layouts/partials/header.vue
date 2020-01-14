<template>
  <header class="border-bottom border-light">
    <div class="container container-extended pt-4 pb-2 pl-4 pr-4">
      <div class="d-flex justify-content-between flex-column flex-lg-row">
        <div>
          <nuxt-link :to="localePath('index')">
            <img src="/logo.png" height="50px" style="max-width: 100%" />
          </nuxt-link>
          <button
            v-tooltip="{
              content:
                'ALPHA: Some missing features, unstable and buggy<br />' +
                '<b>BETA: All features implemented, testing and on-going bug fixes</b><br />' +
                'STABLE: All features implemented, stable release',
              placement: 'right-end',
              html: true
            }"
            class="beta_badge d-none d-sm-block"
          >
            BETA
          </button>
        </div>
        <div class="mt-4 mt-lg-0">
          <b-nav class="text-uppercase">
            <b-nav-item :to="localePath('index')">home</b-nav-item>
            <b-nav-item :to="localePath('about')">about</b-nav-item>
            <b-nav-item :to="localePath('community')">community</b-nav-item>
            <b-nav-item :to="localePath('contact')">contact</b-nav-item>
          </b-nav>
        </div>
      </div>
    </div>
  </header>
</template>
