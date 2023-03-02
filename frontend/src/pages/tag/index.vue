<template>
  <div id="tag-container">
    <div class="container title-container">
      <div class="title pt-2">
        <h1>Tag aliases</h1>
        <p>
          Can't remember the long links of webcomponents? Use the tag with
          /tag/webcomp-name
        </p>
        <h2>Examples</h2>
        <a href="/tag/carsharing">/tag/carsharing</a>
        <br />
        <a href="/tag/flight">/tag/flight</a>
        <br />
        <a href="/tag/flight">/tag/flight</a>
        <br />
        <a href="/tag/events">/tag/events</a>
        <br />
        <p>Note: You can also use the title</p>
        <a href="/tag/Mobility E-Charging Map">/tag/Mobility E-Charging Map</a>

        <br />
        <h2>Warning</h2>
        <p>
          Links might change and not work in the future, if for example the
          title is used and the title gets changed.
        </p>
        <p>
          Keep this in ind when sharing URLs with the tag and for critical
          occasions please use the normal always valid URL you et when opening a
          webcomponent with the uuid.
        </p>
      </div>
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
