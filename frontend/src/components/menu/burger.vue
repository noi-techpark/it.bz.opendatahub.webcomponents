<template>
  <div
    id="burger"
    :class="{
      active: active
    }"
    @click="toggleActive"
  >
    <button type="button" class="burger-button" title="Menu">
      <span class="burger-bar burger-bar--1"></span>
      <span class="burger-bar burger-bar--2"></span>
      <span class="burger-bar burger-bar--3"></span>
    </button>
  </div>
</template>

<script>
export default {
  name: 'Burger',
  props: {
    active: {
      type: Boolean,
      required: true,
      default: false
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
.hidden {
  visibility: hidden;
}

button {
  cursor: pointer;
}

/* remove blue outline */
button:focus {
  outline: 0;
}

.burger-button {
  position: relative;
  height: 30px;
  width: 35px;
  display: block;
  z-index: 999;
  border: 0;
  border-radius: 0;
  background-color: transparent;
  pointer-events: all;
  transition: transform 0.6s cubic-bezier(0.165, 0.84, 0.44, 1);
}

.burger-bar {
  background-color: #130f40;
  position: absolute;
  top: 50%;
  right: 6px;
  left: 6px;
  height: 2px;
  width: auto;
  margin-top: -1px;
  transition: transform 0.6s cubic-bezier(0.165, 0.84, 0.44, 1),
    opacity 0.3s cubic-bezier(0.165, 0.84, 0.44, 1),
    background-color 0.6s cubic-bezier(0.165, 0.84, 0.44, 1);
}

.burger-bar--1 {
  -webkit-transform: translateY(-6px);
  transform: translateY(-6px) scaleX(0.7);
  transform-origin: 0 50%;
}

.burger-bar--2 {
}

.burger-button:hover .burger-bar--1 {
  transform: translateY(-6px) scaleX(1);
}

.no-touchevents .burger-bar--1:hover {
  transform: translateY(-6px) scaleX(1);
}

.burger-bar--3 {
  transform-origin: 0 50%;
  transform: translateY(6px) scaleX(0.85);
}

.burger-button:hover .burger-bar--3 {
  transform: translateY(6px) scaleX(1);
}

.no-touchevents .burger-bar--3:hover {
  transform: translateY(6px) scaleX(1);
}

#burger.active .burger-button {
  transform: rotate(-180deg);
}

#burger.active .burger-bar {
  background-color: black;
}

#burger.active .burger-bar--1 {
  transform: rotate(45deg);
  transform-origin: 50% 50%;
}

#burger.active .burger-bar--2 {
  opacity: 0;
}

#burger.active .burger-bar--3 {
  transform: rotate(-45deg);
  transform-origin: 50% 50%;
}
</style>
