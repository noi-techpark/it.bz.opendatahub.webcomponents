<template>
  <header class="border-bottom border-light fixed-top">
    <div class="container-fluid container-extended pt-2 pb-2 pl-2 pr-4">
      <div class="d-flex justify-content-start flex-row">
        <div>
          <div>
            <a href="https://opendatahub.com/" target="_blank">
              <img src="/logo-open-data-hub.svg" height="40px" class="mt-2" />
            </a>
            <div class="vertical-separator justify-content-start"></div>
          </div>
        </div>
        <div class="d-flex flex-row align-items-center">
          <div class="mt-lg-0 nav-bar">
            <b-nav>
              <b-nav-item
                id="home-item"
                :to="localePath('index')"
                class="home-menu-item font-weight-bold"
                >Web Components</b-nav-item
              >
              <b-nav-item :to="localePath('how-to')">How to</b-nav-item>
              <!--<b-nav-item-dropdown
                id="contribute-dropdown"
                ref="dropdown1"
                text="Contribute"
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
                id="make-use-dropdown"
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
              </b-nav-item-dropdown>-->
              <b-nav-item :to="localePath('validator')">Validator</b-nav-item>
              <b-nav-item href="https://opendatahub.com/contact/"
                >Contact us</b-nav-item
              >
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
        <div>
          <div class="vertical-separator justify-content-start"></div>
          <a href="https://noi.bz.it/" target="_blank">
            <img src="/NOI_1_BK.svg" height="40px" class="mt-2" />
          </a>
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
