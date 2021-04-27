<template>
  <div class="container-fluid extended p-2 pb-0 p-sm-5">
    <div class="row">
      <div class="col-md-8 readme">
        <h2>#README</h2>
        <vue-markdown class="w-100" :source="readMe"></vue-markdown>
      </div>
      <div class="col-md-4 detail-border pl-md-5">
        <h2>#Author</h2>
        <div
          v-for="author in component.authors"
          :key="author"
          class="d-flex flex-row align-items-center bottom-border"
        >
          <div class="circle-icon"></div>
          <div class="pl-5">
            <div class="font-weight-bold">{{ author.name }}</div>
            <div class="font-weight-bold">{{ author.organization }}</div>
          </div>
        </div>
        <template v-if="licenseAgreement !== null">
          <h2 class="pt-4">#Licenseagreeements</h2>
          <vue-markdown class="w-100" :source="licenseAgreement"></vue-markdown>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
import VueMarkdown from 'vue-markdown';

export default {
  name: 'ComponentReadMe',
  components: { VueMarkdown },
  computed: {
    component() {
      return this.$store.getters['webcomponent/currentWebcomponent'];
    },
    readMe() {
      return this.component.versions[0].readMe;
    },
    licenseAgreement() {
      return this.component.versions[0].licenseAgreement;
    },
  },
};
</script>
<style lang="scss">
.readme {
  padding-left: 105px;
}

@media (max-width: 992px) {
  .readme {
    padding-left: 15px;
  }
}
</style>
