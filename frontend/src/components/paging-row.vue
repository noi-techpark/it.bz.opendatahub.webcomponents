<template>
  <div class="d-flex justify-content-between align-items-center flex-wrap">
    <h1 class="components-title d-flex flex-column" style="min-width: 40%">
      <span>{{ currentPage.totalElements }} components</span>
      <span style="font-size: small"
        ><nuxt-link
          :to="
            localePath({
              query: {
                latest: true,
              },
            })
          "
          >&gt;show newest</nuxt-link
        ></span
      >
    </h1>
    <div>
      <Paginator
        :current-page="currentPage.number"
        :total-pages="currentPage.totalPages"
        @page-select="$emit('toPage', $event)"
      />
    </div>
    <div class="d-flex flex-row justify-content-end" style="min-width: 40%">
      <div
        :class="{ disabled: isFirst }"
        class="btn-circle arrow-left outline mr-2"
        @click="$emit('previousPage')"
      >
        <svg
          id="Ebene_1"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          x="0px"
          y="0px"
          width="48px"
          height="45px"
          viewBox="0 0 48 45"
          style="enable-background: new 0 0 48 45"
          xml:space="preserve"
        >
          <style type="text/css">
            .st0 {
              fill: #fff;
            }
          </style>
          <polygon
            class="st0"
            points="21.3,1.3 22.7,2.7 4.4,21 47,21 47,23 4.4,23 23.7,42.3 22.3,43.7 0.6,22 "
          />
        </svg>
      </div>
      <div
        :class="{ disabled: isLast }"
        class="btn-circle arrow-right outline"
        @click="$emit('nextPage')"
      >
        <svg
          id="Ebene_1"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          x="0px"
          y="0px"
          width="48px"
          height="45px"
          viewBox="0 0 48 45"
          style="enable-background: new 0 0 48 45"
          xml:space="preserve"
        >
          <style type="text/css">
            .st0 {
              fill: #fff;
            }
          </style>
          <polygon
            class="st0"
            points="21.3,1.3 22.7,2.7 4.4,21 47,21 47,23 4.4,23 23.7,42.3 22.3,43.7 0.6,22 "
          />
        </svg>
      </div>
    </div>
  </div>
</template>

<script>
import Paginator from '~/components/paginator.vue';
export default {
  name: 'PagingRow',
  components: { Paginator },
  props: {
    currentPage: {
      type: Object,
      default: null,
    },
    isFirst: {
      type: Boolean,
    },
    isLast: {
      type: Boolean,
    },
  },
};
</script>
