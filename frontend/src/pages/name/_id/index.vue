<template>
  <div id="tag-container">
    <div class="container title-container">
      <div class="title pt-2">
        <h1 v-if="!this.tagNotFound">Tag aliases</h1>
        <h1 v-if="this.tagNotFound">404 tag not found</h1>
        <p>
          Can't remember the long links of webcomponents?<br />
          Use the tag with <strong>/tag/name</strong>
        </p>
        <h2>Examples</h2>
        <strong><a href="/tag/carsharing">/tag/carsharing</a></strong>
        <br />
        <strong><a href="/tag/flight">/tag/flight</a></strong>
        <br />
        <p>
          You can also use the <strong>title</strong> of the webcomponent:<br />
          <strong
            ><a href="/tag/Mobility E-Charging Map"
              >/tag/Mobility E-Charging Map</a
            ></strong
          >
        </p>
        <h2>Warning</h2>
        <p>
          Links might change and <strong>not work in the future</strong>, for
          example if the title is used as a tag and gets changed. Keep this in
          mind when sharing URLs with the tag.
          <br />
          For critical occasions, please use the normal
          <strong>always valid URL</strong> with the unique id you get when
          opening a webcomponent.
        </p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: 'Tag',
  data() {
    return {
      tagNotFound: false,
    };
  },
  mounted() {
    this.getEvents();
  },
  methods: {
    async getEvents() {
      const paths = this.$route.path.split('/');
      if (paths.length < 3) return;

      const name = paths.pop();
      const notFound = await this.$api.webcomponent
        .search(name)
        .then(function (result) {
          if (result.content && result.content.length > 0) {
            const id = result.content[0].uuid;
            console.log(id);
            window.location.href = '../webcomponent/' + id;
            return false;
          } else {
            return true;
          }
        });
      this.tagNotFound = notFound;
    },
  },
};
</script>
