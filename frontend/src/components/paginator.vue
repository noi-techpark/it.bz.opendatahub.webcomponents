<template>
  <div
    v-if="totalPages > 0"
    class="font-weight-bold d-flex justify-content-between"
  >
    <span class="text-secondary mr-2">Page</span>
    <div v-if="0 < currentPage - 5" class="mr-2">
      <span class="mr-2" style="cursor: pointer" @click="selectPage(0)">1</span>
      &mdash;
    </div>
    <div
      v-for="page in pagesToDisplay"
      :key="page"
      :class="{ 'text-secondary': page === currentPage }"
      style="cursor: pointer"
      class="ml-2 mr-2"
      @click="selectPage(page)"
    >
      {{ page + 1 }}
    </div>
    <div v-if="totalPages > currentPage + 5" class="ml-2">
      &mdash;
      <span
        class="ml-2"
        style="cursor: pointer"
        @click="selectPage(totalPages - 1)"
        >{{ totalPages }}</span
      >
    </div>
  </div>
</template>

<script>
export default {
  props: {
    currentPage: { default: 0, type: Number },
    totalPages: { default: 0, type: Number },
  },
  computed: {
    pagesToDisplay() {
      const start = Math.max(0, this.currentPage - 5);
      const end = Math.min(this.currentPage + 5, this.totalPages);

      const pages = [];

      for (let i = start; i < end; i++) {
        pages.push(i);
      }

      return pages;
    },
  },
  methods: {
    selectPage(page) {
      this.$emit('page-select', page);
    },
  },
};
</script>
