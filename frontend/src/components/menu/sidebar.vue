<template>
  <div class="sidebar row">
    <div
      @click="toggleActive"
      v-if="isPanelOpen"
      class="sidebar-backdrop"
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
      type: Boolean
    }
  },
  methods: {
    toggleActive() {
      this.$emit('toggle-menu')
    }
  }
}
</script>
<style>
.slide-enter-active,
.slide-leave-active {
  transition: transform 0.2s ease;
}

.slide-enter,
.slide-leave-to {
  transform: translateX(100%);
  transition: all 150ms ease-in 0s;
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
  padding: 2rem 20px 2rem 20px;
}
</style>
