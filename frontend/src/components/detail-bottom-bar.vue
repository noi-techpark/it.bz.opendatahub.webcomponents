<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div class="bottom-bar-container d-flex justify-content-center">
    <div class="bottom-bar d-inline-flex justify-content-center">
      <nuxt-link
        v-if="selectedView === 'preview'"
        class="bottom-bar-button selected d-flex justify-content-center align-items-center text-uppercase"
        :to="toDetails"
      >
        <img :src="require('static/icons/ic_min_preview.svg')" class="p-1" />
        <div class="bottom-bar-button-text p-1">minimize preview</div>

      </nuxt-link>
           <nuxt-link
        v-else
        class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
        :to="toFullscreen"
      >
        <img :src="require('static/icons/ic_max_preview.svg')" class="p-1" />
        <div class="bottom-bar-button-text p-1">fullscreen preview</div>
      </nuxt-link>

      <nuxt-link
        class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
        :to="toFullscreenMode"
      >
        <img :src="require('static/icons/ic_max_preview.svg')" class="p-1" />
        <div class="bottom-bar-button-text p-1">fullscreen mode</div>
      </nuxt-link>
      <nuxt-link
        v-if="selectedView === 'editing'"
        class="bottom-bar-button selected d-flex justify-content-center align-items-center text-uppercase"
        :to="toDetails"
      >
        <img :src="require('static/icons/ic_min_editing.svg')" class="p-1" />
        <div class="bottom-bar-button-text p-1">minimize editing</div>
      </nuxt-link>
      <nuxt-link
        v-else
        class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
        :to="toFullscreenEditing"
      >
        <img :src="require('static/icons/ic_max_editing.svg')" class="p-1" />
        <div class="bottom-bar-button-text p-1">fullscreen editing</div>
      </nuxt-link>
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
      id: this.$route.params.id,
      version: this.$route.params.version,
    };
  },
  computed: {
    snippet() {
      return this.$store.state.webcomponent.snippet;
    },
    attribs() {
      if (!this.$store.state.webcomponent.configuration) {
        return '';
      }

      return this.$store.getters['webcomponent/transportString'];
    },
    toFullscreen() {
      if (this.version) {
        return this.localePath({
          name: 'webcomponent-id-version-fullscreen',
          params: { id: this.id, version: this.version },
          query: { attribs: this.attribs },
        });
      } else {
        return this.localePath({
          name: 'webcomponent-id-fullscreen',
          params: { id: this.id },
          query: { attribs: this.attribs },
        });
      }
    },
    toFullscreenMode() {
      if (this.version) {
        return this.localePath({
          name: 'webcomponent-id-version-fullscreen-mode',
          params: { id: this.id, version: this.version },
          query: { attribs: this.attribs },
        });
      } else {
        return this.localePath({
          name: 'webcomponent-id-fullscreen-mode',
          params: { id: this.id },
          query: { attribs: this.attribs },
        });
      }
    },
    toFullscreenEditing() {
      if (this.version) {
        return this.localePath({
          name: 'webcomponent-id-version-fullscreen-editing',
          params: { id: this.id, version: this.version },
        });
      } else {
        return this.localePath({
          name: 'webcomponent-id-fullscreen-editing',
          params: { id: this.id },
        });
      }
    },
    toDetails() {
      if (this.version) {
        return this.localePath({
          name: 'webcomponent-id-version',
          params: { id: this.id, version: this.version },
        });
      } else {
        return this.localePath({
          name: 'webcomponent-id',
          params: { id: this.id },
        });
      }
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

      const result = await this.$api.webcomponent.createCodeSandbox(
        this.snippet
      );

      if (result) {
        const url = 'https://codesandbox.io/s/' + result;
        window.open(url, '_blank').focus();
      }

      await this.$store.dispatch('loader/finishLoading');
    },
  },
};
</script>
