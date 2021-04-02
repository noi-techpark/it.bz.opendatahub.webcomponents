<template>
  <header class="border-bottom border-light">
    <div class="container-fluid container-extended pt-4 pb-2 pl-4 pr-4">
      <div class="d-flex justify-content-between flex-row">
        <div>
          <nuxt-link :to="localePath('index')">
            <img src="/logo.png" height="50px" style="max-width: 100%" />
          </nuxt-link>
        </div>
        <div class="d-flex flex-row align-items-center">
          <div class="mt-lg-0 nav-bar">
            <b-nav class="text-uppercase">
              <b-nav-item :to="localePath('index')">home</b-nav-item>
              <!-- b-nav-item :to="localePath('about')">about</b-nav-item -->
              <b-nav-item-dropdown
                id="my-nav-dropdown"
                text="contribute"
                toggle-class="nav-link-custom nav-dropdown"
                right
              >
                <b-nav-item>... as web component developer</b-nav-item>
                <hr />
                <b-nav-item>... as data provider</b-nav-item>
              </b-nav-item-dropdown>
              <b-nav-item-dropdown
                id="my-nav-dropdown"
                text="make use"
                toggle-class="nav-link-custom"
                right
              >
                <b-nav-item>... technically</b-nav-item>
                <hr />
                <b-nav-item>... legally</b-nav-item>
              </b-nav-item-dropdown>
              <b-nav-item-dropdown
                id="my-nav-dropdown"
                text="contact"
                toggle-class="nav-link-custom"
                right
              >
                <b-nav-item :to="localePath('contact')"
                  >... a new web component (idea)</b-nav-item
                >
                <hr />
                <b-nav-item :to="localePath('contact')"
                  >... a correction of data</b-nav-item
                >
              </b-nav-item-dropdown>
            </b-nav>
          </div>
          <burger
            v-b-toggle.sidebar-right
            @toggle-menu="toggleMenu"
            :active="menuActive"
            class="mr-4"
          ></burger>
          <nav-menu
            :menuActive="menuActive"
            @toggleMenu="toggleMenu"
          ></nav-menu>
        </div>
      </div>
    </div>
  </header>
</template>
<script>
import Burger from '~/components/menu/burger'
import NavMenu from '~/layouts/partials/nav-menu'
export default {
  name: 'Header',
  components: { NavMenu, Burger },
  data() {
    return {
      menuActive: false,
      contributeActive: false,
      makeUseActive: false,
      contactActive: false
    }
  },
  methods: {
    toggleMenu() {
      this.menuActive = !this.menuActive
    }
  }
}
</script>

<style lang="scss"></style>
