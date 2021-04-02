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
          <sidebar :is-panel-open="menuActive" @toggle-menu="toggleMenu">
            <div class="d-flex justify-content-end">
              <burger
                v-b-toggle.sidebar-right
                @toggle-menu="toggleMenu"
                :active="menuActive"
              ></burger>
            </div>
            <div class="nav-content">
              <h4 class="side-menu-item-closed">
                <nuxt-link :to="localePath('index')" @click.native="toggleMenu"
                  >Home</nuxt-link
                >
              </h4>
              <h4
                @click="contributeActive = !contributeActive"
                :class="[
                  contributeActive
                    ? 'side-menu-item-open'
                    : 'side-menu-item-closed'
                ]"
                class="mobile-only side-menu-item"
              >
                <div class="d-flex justify-content-between">
                  Contribute
                  <span
                    :class="[
                      contributeActive ? 'chevron top' : 'chevron bottom'
                    ]"
                  ></span>
                </div>
              </h4>
              <b-collapse
                id="collapse-1"
                v-model="contributeActive"
                class="pb-3 mobile-only"
              >
                <h5 class="pl-4 side-menu-item">
                  ...as web component developer
                </h5>
                <h5 class="pl-4 side-menu-item">...as data provider</h5>
              </b-collapse>
              <h4
                @click="makeUseActive = !makeUseActive"
                :class="[
                  makeUseActive
                    ? 'side-menu-item-open'
                    : 'side-menu-item-closed'
                ]"
                class="mobile-only side-menu-item"
              >
                <div class="d-flex justify-content-between">
                  Make use
                  <span
                    :class="[makeUseActive ? 'chevron top' : 'chevron bottom']"
                  ></span>
                </div>
              </h4>
              <b-collapse
                id="collapse-2"
                v-model="makeUseActive"
                class="pb-3 mobile-only"
              >
                <h5 class="pl-4 side-menu-item">...technically</h5>
                <h5 class="pl-4 side-menu-item">...legally</h5>
              </b-collapse>
              <h4
                @click="contactActive = !contactActive"
                :class="[
                  contactActive
                    ? 'side-menu-item-open'
                    : 'side-menu-item-closed'
                ]"
                class="mobile-only side-menu-item"
              >
                <div class="d-flex justify-content-between">
                  Contact
                  <span
                    :class="[contactActive ? 'chevron top' : 'chevron bottom']"
                  ></span>
                </div>
              </h4>
              <b-collapse
                id="collapse-3"
                v-model="contactActive"
                class="pb-3 mobile-only"
              >
                <h5 class="pl-4 side-menu-item">
                  ...a new web component (idea)
                </h5>
                <h5 class="pl-4 side-menu-item">...a correction of data</h5>
              </b-collapse>
              <h4 class="side-menu-item side-menu-item-closed">
                <nuxt-link
                  :to="localePath('community')"
                  @click.native="toggleMenu"
                  >Community</nuxt-link
                >
              </h4>
              <h4 class="side-menu-item side-menu-item-closed">
                <nuxt-link
                  :to="localePath('validator')"
                  @click.native="toggleMenu"
                  >Tools</nuxt-link
                >
              </h4>
              <h4 class="side-menu-item-closed">
                <nuxt-link :to="localePath('about')" @click.native="toggleMenu"
                  >About</nuxt-link
                >
              </h4>
              <h4 class="side-menu-item-closed">
                FAQ
              </h4>
            </div>
          </sidebar>
        </div>
      </div>
    </div>
  </header>
</template>
<script>
import Burger from '~/components/menu/burger'
import Sidebar from '~/components/menu/sidebar'
export default {
  name: 'Header',
  components: { Sidebar, Burger },
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
