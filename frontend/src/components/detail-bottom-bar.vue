<template>
  <div class="bottom-bar">
    <div class="d-flex justify-content-center">
      <div
        v-if="selectedView === 'preview'"
        class="bottom-bar-button selected d-flex justify-content-center align-items-center text-uppercase"
        @click="
          $router.push({
            path: '/webcomponent/' + $route.params.id,
          })
        "
      >
        <img :src="require('static/icons/ic_min_preview.svg')" />
        <div class="bottom-bar-button-text p-1">minimize preview</div>
      </div>
      <div
        v-else
        class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
        @click="
          $router.push('/webcomponent/' + $route.params.id + '/fullscreen')
        "
      >
        <img :src="require('static/icons/ic_max_preview.svg')" />
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
        <img :src="require('static/icons/ic_min_editing.svg')" />
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
        <img :src="require('static/icons/ic_max_editing.svg')" />
        <div class="bottom-bar-button-text p-1">fullscreen editing</div>
      </div>
      <div
        class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
        @click="$emit('copyCode')"
      >
        <img :src="require('static/icons/ic_copy.svg')" />
        <div class="bottom-bar-button-text p-1">copy code</div>
      </div>
      <div
        class="bottom-bar-button d-flex justify-content-center align-items-center text-uppercase"
        @click="$emit('updatePreview')"
      >
        <img :src="require('static/icons/ic_update.svg')" />
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
};
</script>
