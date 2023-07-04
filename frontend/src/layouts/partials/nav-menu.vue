<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <sidebar :is-panel-open="menuActive" @toggle-menu="toggleMenu">
    <div class="side-bar-container d-flex flex-column align-items-end h-100">
      <div
        class="
          d-flex
          justify-content-end
          align-items-center
          container-fluid container-extended
          pt-4
          pb-2
          pl-4
          pr-4
        "
        style="min-height: 50px"
      >
        <div
          class="d-flex justify-content-center align-items-center"
          style="min-height: 50px"
        ></div>
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
          <div class="side-menu-item side-menu-item-closed menu-link">
            <nuxt-link :to="localePath('how-to')" @click.native="toggleMenu"
              >How to</nuxt-link
            >
          </div>
          <div class="side-menu-item side-menu-item-closed menu-link">
            <nuxt-link :to="localePath('validator')" @click.native="toggleMenu"
              >Validator</nuxt-link
            >
          </div>
          <div class="side-menu-item side-menu-item-closed menu-link">
            <a
              href="https://opendatahub.com/contact/"
              @click.native="toggleMenu"
              >Contact us</a
            >
          </div>
          <div>
            <a href="https://noi.bz.it/" target="_blank">
              <img src="/NOI_1_BK.svg" height="50px" class="mt-2" />
            </a>
          </div>
        </div>
      </div>
    </div>
  </sidebar>
</template>

<script>
import Sidebar from '~/components/menu/sidebar';

export default {
  name: 'NavMenu',
  components: { Sidebar },
  props: {
    menuActive: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      contributeActive: true,
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
