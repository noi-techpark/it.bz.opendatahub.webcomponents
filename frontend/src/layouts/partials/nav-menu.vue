<template>
  <sidebar :is-panel-open="menuActive" @toggle-menu="toggleMenu">
    <div class="side-bar-container d-flex flex-column align-items-end h-100">
      <div
        class="d-flex justify-content-end container-fluid container-extended pt-4 pb-2 pl-4 pr-4"
      >
        <burger
          v-b-toggle.sidebar-right
          :active="menuActive"
          class="p-4"
          @toggle-menu="toggleMenu"
        ></burger>
      </div>
      <div
        class="d-flex justify-content-between flex-column h-100 w-100"
        style="overflow: scroll"
      >
        <div class="nav-content">
          <div class="side-menu-item-closed menu-link">
            <nuxt-link :to="localePath('index')" @click.native="toggleMenu"
              >Home</nuxt-link
            >
          </div>
          <div
            :class="[
              contributeActive
                ? 'side-menu-item-open'
                : 'side-menu-item-closed',
            ]"
            class="mobile-only side-menu-item"
            @click="contributeActive = !contributeActive"
          >
            <div class="d-flex justify-content-between menu-link">
              Contribute
              <span
                :class="[contributeActive ? 'chevron top' : 'chevron bottom']"
              ></span>
            </div>
          </div>
          <b-collapse
            id="collapse-1"
            v-model="contributeActive"
            class="pb-3 mobile-only"
          >
            <div class="pl-4 side-menu-item menu-sub-link">
              <nuxt-link
                :to="localePath('/contribute/developer')"
                @click.native="toggleMenu"
                >...as web component developer</nuxt-link
              >
            </div>
            <div class="pl-4 side-menu-item menu-sub-link">
              <nuxt-link
                :to="localePath('/contribute/data-provider')"
                @click.native="toggleMenu"
                >...as data provider</nuxt-link
              >
            </div>
          </b-collapse>
          <div
            :class="[
              makeUseActive ? 'side-menu-item-open' : 'side-menu-item-closed',
            ]"
            class="mobile-only side-menu-item"
            @click="makeUseActive = !makeUseActive"
          >
            <div class="d-flex justify-content-between menu-link">
              Make use
              <span
                :class="[makeUseActive ? 'chevron top' : 'chevron bottom']"
              ></span>
            </div>
          </div>
          <b-collapse
            id="collapse-2"
            v-model="makeUseActive"
            class="pb-3 mobile-only"
          >
            <div class="pl-4 side-menu-item menu-sub-link">
              <nuxt-link
                :to="localePath('/make-use/technically')"
                @click.native="toggleMenu"
                >...technically</nuxt-link
              >
            </div>
            <div class="pl-4 side-menu-item menu-sub-link">
              <nuxt-link
                :to="localePath('/make-use/legally')"
                @click.native="toggleMenu"
                >...legally</nuxt-link
              >
            </div>
          </b-collapse>
          <div class="side-menu-item side-menu-item-closed menu-link">
            <nuxt-link :to="localePath('contact')" @click.native="toggleMenu"
              >Contact</nuxt-link
            >
          </div>
          <div class="side-menu-item side-menu-item-closed menu-link">
            <nuxt-link :to="localePath('community')" @click.native="toggleMenu"
              >Community</nuxt-link
            >
          </div>
          <div class="side-menu-item side-menu-item-closed menu-link">
            <nuxt-link :to="localePath('validator')" @click.native="toggleMenu"
              >Tools</nuxt-link
            >
          </div>
          <div class="side-menu-item-closed menu-link">
            <nuxt-link :to="localePath('about')" @click.native="toggleMenu"
              >About</nuxt-link
            >
          </div>
          <div class="side-menu-item-closed menu-link">
            <nuxt-link :to="localePath('faq')" @click.native="toggleMenu"
              >FAQ</nuxt-link
            >
          </div>
        </div>
        <div style="min-height: 200px !important; background-color: #888">
          <footer-info-menu></footer-info-menu>
        </div>
      </div>
    </div>
  </sidebar>
</template>

<script>
import FooterInfoMenu from '@/layouts/partials/footer-info-menu';
import Burger from '~/components/menu/burger';
import Sidebar from '~/components/menu/sidebar';
export default {
  name: 'NavMenu',
  components: { FooterInfoMenu, Sidebar, Burger },
  props: {
    menuActive: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      contributeActive: false,
      makeUseActive: false,
    };
  },
  methods: {
    toggleMenu() {
      this.$emit('toggleMenu', !this.menuActive);
    },
  },
};
</script>

<style scoped>
h4,
h5 {
  font-weight: normal !important;
}
</style>
