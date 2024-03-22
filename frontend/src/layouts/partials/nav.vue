<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div class="sticky-top nav-wrapper">
    <nav class="border-bottom bg-white">
      <div class="container">
        <b-navbar class="px-0" toggleable="lg">
          <b-navbar-brand :to="localePath('index')">
            <div class="h-100 d-flex flex-row align-items-center">
              <!-- <div class="flex-shrink-1">
                <img src="/open-data-hub.svg" alt="Open Data Hub">
              </div> -->
              <div class="mx-2 align-self-stretch border-black"></div>
              <ToolBadge class="mr-2 wcs-logo" />
              <div
                v-if="showBadge"
                class="d-inline rounded custom-badge text-small"
              >
                TESTING
              </div>
            </div>
          </b-navbar-brand>

          <b-navbar-toggle
            class="border-0"
            @click="$emit('toggle-overlay')"
            target="navbar-collapse"
          >
            <template>
              <img src="/icons/hamburger.svg" alt="Menu" />
            </template>
          </b-navbar-toggle>

          <b-collapse is-nav id="navbar-collapse" class="py-2 p-lg-0">
            <b-navbar-nav class="flex-grow-1 align-items-center">
              <b-nav-item-dropdown text="How to">
                <b-dropdown-item
                  :to="localePath('how-to-add')"
                  @click="$emit('hide-overlay')"
                >
                  Add
                </b-dropdown-item>
                <b-dropdown-item
                  :to="localePath('how-to-create')"
                  @click="$emit('hide-overlay')"
                >
                  Create
                </b-dropdown-item>
              </b-nav-item-dropdown>
              <b-nav-item
                :to="localePath('validator')"
                @click="$emit('hide-overlay')"
              >
                Validator
              </b-nav-item>
              <b-nav-item
                href="https://opendatahub.com/contact"
                target="_blank"
              >
                Contact
              </b-nav-item>
              <a
                class="ml-auto no-external-icon text-small d-none d-lg-inline"
                href="https://opendatahub.com"
                target="_blank"
              >
                <img
                  class="pl-2 logo-open-data-hub"
                  src="/open-data-hub-sm.svg"
                  alt="Open Data Hub"
                />
              </a>
            </b-navbar-nav>
          </b-collapse>
        </b-navbar>
      </div>
    </nav>
  </div>
</template>

<script>
import ToolBadge from '~/components/menu/tool-badge.vue';

export default {
  components: { ToolBadge },
  name: 'Nav',
  data: () => {
    return {
      showBadge: process.env.BUILD_ENVIRONMENT === 'testing',
    };
  },
};
</script>

<style scoped>
* {
  font-family: 'Source Sans Pro', 'Open Sans', arial, sans-serif;
  font-size: 20px;
  line-height: 28px;
}

.wcs-logo {
  span {
    padding: 0.3rem;
  }
}
.logo-open-data-hub {
  width: 2.2rem;
}

.nav-item:not(.no-external-icon) [target='_blank']::after {
  padding-left: 0.4rem;
  content: url(/icons/external.svg);
}

.nav-item .nuxt-link-exact-active {
  font-weight: bold;
}

.dropdown-custom .dropdown-menu {
  background-color: #f4f8f9 !important;
  border: none !important;
  box-shadow: none !important;
}

.bg {
  background-color: #f4f8f9;
}

.custom-badge {
  background-color: #fa00ff;
  line-height: 20px;
  padding: 6px 12px;
}

.border-black {
  border-color: black !important;
}

.text-small,
.text-small * {
  font-size: 14px;
}
</style>
