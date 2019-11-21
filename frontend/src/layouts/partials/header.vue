<template>
  <header class="border-bottom border-light">
    <div class="container container-extended pt-4 pb-2 pl-4 pr-4">
      <div class="d-flex justify-content-between">
        <div>
          <nuxt-link :to="localePath('index')">
            <img src="/logo.png" height="50px" />
          </nuxt-link>
          <a href="https://en.wikipedia.org/wiki/Software_release_life_cycle#Stages_of_development" target="_blank" class="alpha_badge">ALPHA</a>
        </div>
        <div>
          <b-nav class="text-uppercase">
            <b-nav-item :to="localePath('index')">home</b-nav-item>
            <b-nav-item-dropdown
              id="my-nav-dropdown"
              text="about"
              toggle-class="nav-link-custom"
              right
            >
              <b-dropdown-item>IDM</b-dropdown-item>
              <b-dropdown-item>ODH</b-dropdown-item>
              <b-dropdown-divider></b-dropdown-divider>
              <b-dropdown-item>NOI</b-dropdown-item>
            </b-nav-item-dropdown>
            <b-nav-item :to="localePath('getting-started')"
              >getting started</b-nav-item
            >
            <b-nav-item>community</b-nav-item>
          </b-nav>
        </div>
      </div>
    </div>
  </header>
</template>
