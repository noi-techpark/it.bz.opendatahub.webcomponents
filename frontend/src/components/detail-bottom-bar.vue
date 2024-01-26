<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div class="bottom-bar-container d-flex justify-content-center">
    <div class="bottom-bar d-inline-flex justify-content-center">
        <nuxt-link
            class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
            :to="toFullscreen" target="_blank"
        >
            <img :src="require('static/icons/ic_max_preview.svg')" class="p-1" />
            <div class="bottom-bar-button-text p-1">open in new tab</div>
        </nuxt-link>

        <div
            class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
            @click="handleScroll('chooseRightSidebarTab',0)"
        >
            <img :src="require('static/icons/ic_max_editing.svg')" class="p-1" />
            <div class="bottom-bar-button-text p-1">configure</div>
        </div>
      
        <div
            class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
            @click="handleScroll('chooseRightSidebarTab',1)"
        >
            <img :src="require('static/icons/ic_copy.svg')" class="p-1" />
            <div class="bottom-bar-button-text p-1">share</div>
        </div>

        <div
            class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
            @click="handleScroll('chooseRightSidebarTab',2)"
        >
            <img :src="require('static/icons/information-outline.svg')" class="p-1" />
            <div class="bottom-bar-button-text p-1">about</div>
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
    // toFullscreenEditing() {
    //   if (this.version) {
    //     return this.localePath({
    //       name: 'webcomponent-id-version-fullscreen-editing',
    //       params: { id: this.id, version: this.version },
    //     });
    //   } else {
    //     return this.localePath({
    //       name: 'webcomponent-id-fullscreen-editing',
    //       params: { id: this.id },
    //     });
    //   }
    // },
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
    changeTabIndex(tabIndex) {
        this.$emit('changeTabIndex',tabIndex,true);
    },
    handleScroll(anchorId,tabIndex) {
        this.changeTabIndex(tabIndex)

        const anchor = document.querySelector(`#${anchorId}`)
        if (anchor) {
            window.scrollTo({
                top: anchor.getBoundingClientRect().top + window.pageYOffset
            })
        }
    },
    // copyLink() {
    //   clearInterval(this.intervalId);
    //   this.showPopover = true;
    //   this.intervalId = setInterval(
    //     function () {
    //       this.showPopover = false;
    //     }.bind(this),
    //     3000
    //   );
    //   this.$emit('copyCode');
    // },
    // copyCode() {
    //   clearInterval(this.intervalId);
    //   this.showEmbedPopover = true;
    //   this.intervalId = setInterval(
    //     function () {
    //       this.showEmbedPopover = false;
    //     }.bind(this),
    //     3000
    //   );
    //   this.$emit('copyCode');
    // },
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
