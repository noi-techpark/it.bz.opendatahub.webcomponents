<template>
  <sidebar :is-panel-open="menuActive" @toggle-menu="toggleMenu">
    <div
      class="side-bar-container d-flex flex-column align-items-end h-100"
      style=""
    >
      <div class="d-flex justify-content-end">
        <burger
          v-b-toggle.sidebar-right
          @toggle-menu="toggleMenu"
          :active="menuActive"
          class="p-4"
        ></burger>
      </div>
      <div
        class="d-flex justify-content-between flex-column h-100 w-100"
        style="overflow: scroll;"
      >
        <div class="nav-content">
          <h4 class="side-menu-item-closed">
            <nuxt-link :to="localePath('index')" @click.native="toggleMenu"
              >Home</nuxt-link
            >
          </h4>
          <h4
            @click="contributeActive = !contributeActive"
            :class="[
              contributeActive ? 'side-menu-item-open' : 'side-menu-item-closed'
            ]"
            class="mobile-only side-menu-item"
          >
            <div class="d-flex justify-content-between">
              Contribute
              <span
                :class="[contributeActive ? 'chevron top' : 'chevron bottom']"
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
              makeUseActive ? 'side-menu-item-open' : 'side-menu-item-closed'
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
              contactActive ? 'side-menu-item-open' : 'side-menu-item-closed'
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
            <nuxt-link :to="localePath('community')" @click.native="toggleMenu"
              >Community</nuxt-link
            >
          </h4>
          <h4 class="side-menu-item side-menu-item-closed">
            <nuxt-link :to="localePath('validator')" @click.native="toggleMenu"
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
        <div style="min-height: 200px !important; background-color: #888888">
          <footer-info></footer-info>
        </div>
      </div>
    </div>
  </sidebar>
</template>

<script>
import Burger from '~/components/menu/burger'
import Sidebar from '~/components/menu/sidebar'
import FooterInfo from '~/layouts/partials/footer-info'
export default {
  name: 'NavMenu',
  components: { FooterInfo, Sidebar, Burger },
  props: {
    menuActive: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      contributeActive: false,
      makeUseActive: false,
      contactActive: false
    }
  },
  methods: {
    toggleMenu() {
      this.$emit('toggleMenu', !this.menuActive)
    }
  }
}
</script>

<style scoped></style>
