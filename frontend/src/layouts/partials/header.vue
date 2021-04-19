<template>
  <header class="border-bottom border-light fixed-top">
    <div class="container-fluid container-extended pt-4 pb-2 pl-4 pr-4">
      <div class="d-flex justify-content-between flex-row">
        <div>
          <nuxt-link :to="localePath('index')">
            <img
              id="logo"
              src="/logo.svg"
              height="50px"
              style="max-width: 100%"
            />
          </nuxt-link>
        </div>
        <div class="d-flex flex-row align-items-center">
          <div class="mt-lg-0 nav-bar">
            <b-nav class="text-uppercase">
              <b-nav-item :to="localePath('index')" class="home-menu-item"
                >home</b-nav-item
              >
              <!-- b-nav-item :to="localePath('about')">about</b-nav-item -->
              <b-nav-item-dropdown
                id="my-nav-dropdown"
                ref="dropdown1"
                text="contribute"
                toggle-class="nav-link-custom nav-dropdown"
                right
                :no-caret="true"
              >
                <div class="triangle"></div>
                <b-nav-item
                  :to="localePath('/contribute/developer')"
                  @click="$refs.dropdown1.hide()"
                  >... as web component developer</b-nav-item
                >
                <hr />
                <b-nav-item
                  :to="localePath('/contribute/data-provider')"
                  @click="$refs.dropdown1.hide()"
                  >... as data provider</b-nav-item
                >
              </b-nav-item-dropdown>
              <b-nav-item-dropdown
                id="my-nav-dropdown"
                ref="dropdown2"
                text="make use"
                toggle-class="nav-link-custom"
                right
              >
                <div class="triangle"></div>
                <b-nav-item
                  :to="localePath('/make-use/technically')"
                  @click="$refs.dropdown2.hide()"
                  >... technically</b-nav-item
                >
                <hr />
                <b-nav-item
                  :to="localePath('/make-use/legally')"
                  @click="$refs.dropdown2.hide()"
                  >... legally</b-nav-item
                >
              </b-nav-item-dropdown>
              <b-nav-item-dropdown
                id="my-nav-dropdown"
                ref="dropdown3"
                text="contact"
                toggle-class="nav-link-custom"
                right
              >
                <div class="triangle"></div>
                <b-nav-item
                  :to="localePath('/contact/idea')"
                  @click="$refs.dropdown3.hide()"
                  >... a new web component (idea)</b-nav-item
                >
                <hr />
                <b-nav-item
                  :to="localePath('/contact/data-correction')"
                  @click="$refs.dropdown3.hide()"
                  >... a correction of data</b-nav-item
                >
              </b-nav-item-dropdown>
            </b-nav>
          </div>
          <burger
            v-b-toggle.sidebar-right
            :active="menuActive"
            class="mr-4"
            @toggle-menu="toggleMenu"
          ></burger>
          <nav-menu
            :menu-active="menuActive"
            @toggleMenu="toggleMenu"
          ></nav-menu>
        </div>
      </div>
    </div>
  </header>
</template>
<script>
import Burger from '~/components/menu/burger';
import NavMenu from '~/layouts/partials/nav-menu';
export default {
  name: 'Header',
  components: { NavMenu, Burger },
  data() {
    return {
      menuActive: false,
      contributeActive: false,
      makeUseActive: false,
      contactActive: false,
    };
  },
  methods: {
    toggleMenu() {
      this.menuActive = !this.menuActive;
    },
  },
};
</script>
