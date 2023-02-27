<template>
  <div id="tag-container">
    <div class="container title">
      <div class="title pt-2">
        <h1>Tag aliases</h1>
        <p>Can't remember the long links of webcomponents?</p>
        <p>Use the tag with /tag/webcomp-name</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: 'Tag',
  mounted() {
    const paths = this.$route.path.split('/');
    const tag = paths.pop();
    console.log(tag);
    this.$api.webcomponent.search(tag).then(function (result) {
      console.log(result);
      if (result.content && result.content.length > 0) {
        const id = result.content[0].uuid;
        console.log(id);
        window.location.href = '../webcomponent/' + id;
      }
    });
  },
};
</script>
