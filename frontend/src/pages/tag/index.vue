<template>
  <div>
    <div class="container title-container">
      <div class="title pt-2">
        <h1>Tag aliases</h1>
        <p>Can't remember the long links of webcomponents?</p>
        <p>Use the tag with /tag/webcomp-name</p>
      </div>
    </div>
    <div class="bg-light ml-auto">
      <div class="container p-4 mb-5"></div>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: 'Tag',
  mounted() {
    console.log('TAG');
    console.log(this.$route); // true
  },
  methods: {
    parseTag() {},
  },
};
</script>
