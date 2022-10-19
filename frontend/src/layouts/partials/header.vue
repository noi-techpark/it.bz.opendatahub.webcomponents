<template>
  <header class="border-bottom border-light fixed-top">
    <div class="container-fluid container-extended pt-2 pb-2 pl-2 pr-4">
      <div class="d-flex justify-content-start flex-row">
        <div>
          <a href="https://opendatahub.com/" target="_blank">
            <img src="/logo-open-data-hub.svg" height="40px" class="mt-2" />
          </a>
          <div class="vertical-separator justify-content-start"></div>
        </div>
        <b-nav class="d-flex flex-row align-items-center">
          <b-nav-item
            id="home-item"
            :to="localePath('index')"
            class="home-menu-item font-weight-bold"
            >Web Components</b-nav-item
          >
          <b-nav-item class="home-menu-item" :to="localePath('how-to')"
            >How to</b-nav-item
          >
          <b-nav-item class="home-menu-item" :to="localePath('validator')"
            >Validator</b-nav-item
          >
        </b-nav>
        <b-nav class="d-flex flex-row align-items-center ml-auto">
          <b-nav-item
            class="home-menu-item"
            href="https://opendatahub.com/contact/"
            >Contact us</b-nav-item
          >
          <div class="home-menu-item">
            <div class="vertical-separator"></div>
            <a href="https://noi.bz.it/" target="_blank">
              <img src="/NOI_1_BK.svg" height="40px" class="mt-2" />
            </a>
          </div>
        </b-nav>
        <div class="d-flex flex-row align-items-center">
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
<style scoped>
#logo {
  margin-top: -7px;
}
</style>
