<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div class="sticky-top">
    <div v-if="showBadge" class="bg">
      <div class="container py-2">
        <div class="d-inline rounded custom-badge text-small"><span>TESTING</span></div>
      </div>
    </div>
    <nav class="border-bottom bg-white">
      <div class="container">
        <b-navbar class="px-0" toggleable="lg">
          <b-navbar-brand :to="localePath('index')">
            <div class="h-100 d-flex flex-row align-items-center">
              <div class="flex-shrink-1">
                <img src="/open-data-hub.svg" alt="Open Data Hub">
              </div>
              <div class="mx-2 align-self-stretch border-black border-right"></div>
              <ToolBadge class="mr-2" />
            </div>
          </b-navbar-brand>
          <b-navbar-nav class="flex-grow-1 align-items-center">
            <b-nav-item :to="localePath('index')">
              Web Components
            </b-nav-item>
            <b-nav-item :to="localePath('how-to')">
              How to
            </b-nav-item>
            <b-nav-item :to="localePath('validator')">
              Validator
            </b-nav-item>
            <b-nav-item href="https://opendatahub.com/contact" target="_blank">
              Contact
            </b-nav-item>
            <b-nav-item class="ml-auto no-external-icon text-small" href="https://opendatahub.com" target="_blank">
              presented by <img class="pl-2" src="/open-data-hub-sm.svg" alt="Open Data Hub">
            </b-nav-item>
          </b-navbar-nav>
        </b-navbar>
      </div>
    </nav>
  </div>
</template>

<script>
import ToolBadge from '~/components/menu/tool-badge.vue';

export default {
  components: { ToolBadge },
  name: "Nav",
  data: () => {
    return {
      buildEnv: process.env.BUILD_ENVIRONMENT,
      showBadge: process.env.BUILD_ENVIRONMENT === "testing"
    }
  }
}
</script>

<style scoped>
  * {    
    font-family: "Source Sans Pro", "Open Sans", arial, sans-serif;
    font-size: 20px;
    line-height: 28px;
  }

  .nav-item:not(.no-external-icon) [target="_blank"]::after {
    padding-left: 0.4rem;
    content: url(/icons/external.svg)
  }

  .nav-item .nuxt-link-exact-active {
    font-weight: bold;
  }

  .bg {
    background-color: #F4F8F9;
  }

  .custom-badge {
    background-color: #FA00FF;
    padding: 6px 12px
  }

  .border-black {
    border-color: black !important;
  }

  .text-small,
  .text-small * {
    font-size: 14px;
  }
</style>
