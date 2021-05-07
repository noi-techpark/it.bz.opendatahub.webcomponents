<template>
  <div class="bottom-bar-container d-flex justify-content-center">
    <div class="bottom-bar d-inline-flex justify-content-center">
      <div
        v-if="selectedView === 'preview'"
        class="bottom-bar-button selected d-flex justify-content-center align-items-center text-uppercase"
        @click="
          $router.push({
            path: '/webcomponent/' + $route.params.id,
          })
        "
      >
        <img :src="require('static/icons/ic_min_preview.svg')" class="p-1" />
        <div class="bottom-bar-button-text p-1">minimize preview</div>
      </div>
      <div
        v-else
        class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
        @click="
          $router.push('/webcomponent/' + $route.params.id + '/fullscreen')
        "
      >
        <img :src="require('static/icons/ic_max_preview.svg')" class="p-1" />
        <div class="bottom-bar-button-text p-1">fullscreen preview</div>
      </div>
      <div
        v-if="selectedView === 'editing'"
        class="bottom-bar-button selected d-flex justify-content-center align-items-center text-uppercase"
        @click="
          $router.push({
            path: '/webcomponent/' + $route.params.id,
          })
        "
      >
        <img :src="require('static/icons/ic_min_editing.svg')" class="p-1" />
        <div class="bottom-bar-button-text p-1">minimize editing</div>
      </div>
      <div
        v-else
        class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
        @click="
          $router.push(
            '/webcomponent/' + $route.params.id + '/fullscreen-editing'
          )
        "
      >
        <img :src="require('static/icons/ic_max_editing.svg')" class="p-1" />
        <div class="bottom-bar-button-text p-1">fullscreen editing</div>
      </div>
      <div
        id="copy-code"
        class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
        @click="copyCode"
      >
        <img :src="require('static/icons/ic_copy.svg')" class="p-1" />
        <div class="bottom-bar-button-text p-1">copy code</div>
      </div>
      <b-popover
        id="popover"
        target="copy-code"
        :show.sync="showPopover"
        triggers="click"
        placement="top"
      >
        Copied to clipboard
      </b-popover>
      <div
        class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
        @click="createCodeSandbox"
      >
        <img :src="require('static/icons/codesandbox.svg')" class="p-1" />
        <div class="bottom-bar-button-text p-1">open codesandbox</div>
      </div>
      <div
        class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
        @click="$emit('updatePreview')"
      >
        <img :src="require('static/icons/ic_update.svg')" class="p-1" />
        <div class="bottom-bar-button-text p-1">update preview</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DetailBottomBar',
  props: {
    selectedView: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      showPopover: false,
      intervalId: 0,
    };
  },
  computed: {
    snipp() {
      return this.$store.getters['webcomponent/currentSnipp'];
    },
  },
  methods: {
    copyCode() {
      clearInterval(this.intervalId);
      this.showPopover = true;
      this.intervalId = setInterval(
        function () {
          this.showPopover = false;
        }.bind(this),
        3000
      );
      this.$emit('copyCode');
    },
    async createCodeSandbox() {
      await this.$store.dispatch('loader/startLoading');

      const result = await this.$api.webcomponent.createCodeSandbox(this.snipp);

      if (result) {
        const url = 'https://codesandbox.io/s/' + result;
        window.open(url, '_blank').focus();
      }

      await this.$store.dispatch('loader/finishLoading');
    },
  },
};
</script>
