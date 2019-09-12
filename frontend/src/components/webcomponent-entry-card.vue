<template>
  <nuxt-link :to="returnPath" style="color:inherit;text-decoration: inherit;">
    <b-card
      img-src="https://picsum.photos/600/300/?image=25"
      img-alt="Image"
      img-top
      class="full-height"
    >
      <b-card-title title-tag="div" class="h4">
        <span class="text-secondary">#</span>{{ entry.title }}
      </b-card-title>

      <b-card-text class="text-muted">
        {{ entry.descriptionAbstract }}
      </b-card-text>

      <div slot="footer" class="row font-small">
        <div class="col-6">
          <div>
            Author:
            <span class="font-weight-bold">
              <span v-if="entry.authors.length > 0">{{
                entry.authors[0].name
              }}</span>
              <span v-else>unknown</span>
            </span>
            <span v-if="entry.authors.length > 1"> et al.</span>
          </div>
          <div>
            Category:
            <span class="font-weight-bold"
              ><span
                v-for="tag in entry.searchTags"
                :key="tag"
                class="text-capitalize implode"
                >{{ tag }}</span
              ></span
            >
          </div>
        </div>
        <div class="col-6">
          <div>
            Version:
            <span class="font-weight-bold">{{
              entry.currentVersion.versionTag
            }}</span>
          </div>
          <div>
            License:
            <span class="font-weight-bold">{{ entry.license.name }}</span>
          </div>
        </div>
      </div>
    </b-card>
  </nuxt-link>
</template>

<script>
export default {
  props: {
    entry: {
      default: null,
      type: Object
    },
    returnTo: {
      default: null,
      type: String
    }
  },
  computed: {
    returnPath() {
      if (this.returnTo === null) {
        return this.localePath({
          name: 'webcomponent-id',
          params: { id: this.entry.uuid }
        });
      }

      return this.localePath({
        name: 'webcomponent-id',
        params: { id: this.entry.uuid },
        query: { from: this.returnTo }
      });
    }
  }
};
</script>
