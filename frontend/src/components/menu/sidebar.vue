<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div class="sidebar row">
    <div
      v-if="isPanelOpen"
      class="sidebar-backdrop"
      @click="toggleActive"
    ></div>
    <transition name="slide">
      <b-col v-if="isPanelOpen" class="sidebar-panel" cols="12" md="7">
        <slot></slot>
      </b-col>
    </transition>
  </div>
</template>
<script>
export default {
  props: {
    isPanelOpen: {
      type: Boolean,
    },
  },
  methods: {
    toggleActive() {
      this.$emit('toggle-menu');
    },
  },
};
</script>
<style>
.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s ease;
}

.slide-enter,
.slide-leave-to {
  transform: translateX(100%);
  transition: all 200ms ease-in 0s;
}

.sidebar-backdrop {
  background-color: black;
  opacity: 0.4;
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  cursor: pointer;
  z-index: 999;
}

.sidebar-panel {
  overflow-y: auto;
  background-color: white;
  position: fixed;
  right: 0;
  top: 0;
  height: 100vh;
  z-index: 999;
  padding: 0 !important;
}
</style>
