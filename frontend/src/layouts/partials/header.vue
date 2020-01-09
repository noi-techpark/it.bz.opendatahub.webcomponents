<template>
  <header class="border-bottom border-light">
    <div class="container container-extended pt-4 pb-2 pl-4 pr-4">
      <div class="d-flex justify-content-between flex-column flex-lg-row">
        <div>
          <nuxt-link :to="localePath('index')">
            <img src="/logo.png" height="50px" style="max-width: 100%" />
          </nuxt-link>
          <a
            href="https://en.wikipedia.org/wiki/Software_release_life_cycle#Stages_of_development"
            target="_blank"
            class="beta_badge d-none d-sm-block"
            >BETA</a
          >
        </div>
        <div class="mt-4 mt-lg-0">
          <b-nav class="text-uppercase">
            <b-nav-item :to="localePath('index')">home</b-nav-item>
            <b-nav-item :to="localePath('about')">about</b-nav-item>
            <b-nav-item :to="localePath('community')">community</b-nav-item>
            <b-nav-item :to="localePath('contacts')">contacts</b-nav-item>
          </b-nav>
        </div>
      </div>
    </div>
  </header>
</template>
