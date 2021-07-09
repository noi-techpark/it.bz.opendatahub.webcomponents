<template>
  <div class="container-fluid container-extended pb-4 p-2 pl-4 pr-4 pt-sm-2">
    <div class="row">
      <div class="col-md-8 readme">
        <h2>#README</h2>
        <vue-markdown class="w-100" :source="readMe"></vue-markdown>
      </div>
      <div class="col-md-4 detail-border pl-md-5">
        <h2>#Author</h2>
        <div
          v-for="author in component.authors"
          :key="'a::' + author.toString()"
          class="d-flex flex-row align-items-center bottom-border"
        >
          <div class="circle-icon d-none"></div>
          <div class="pl-4">
            <div class="font-weight-bold">
              {{ author.name }}
              <span v-if="author.email" class="font-weight-normal"
                >&lt;{{ author.email }}&gt;</span
              >
            </div>
            <div class="font-weight-bold">
              {{ author.organization }}
              <a
                v-if="author.organizationUrl"
                :href="author.organizationUrl"
                target="_blank"
                class="font-weight-normal"
                >&lt;{{ author.organizationUrl }}&gt;</a
              >
            </div>
          </div>
        </div>
        <h2 class="mt-2">#Copyright holder</h2>
        <div
          v-for="author in component.copyrightHolders"
          :key="'c::' + author.toString()"
          class="d-flex flex-row align-items-center bottom-border"
        >
          <div class="circle-icon d-none"></div>
          <div class="pl-4">
            <div class="font-weight-bold">
              {{ author.name }}
              <span v-if="author.email" class="font-weight-normal"
                >&lt;{{ author.email }}&gt;</span
              >
            </div>
            <div class="font-weight-bold">
              {{ author.organization }}
              <a
                v-if="author.organizationUrl"
                :href="author.organizationUrl"
                target="_blank"
                class="font-weight-normal"
                >&lt;{{ author.organizationUrl }}&gt;</a
              >
            </div>
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

<script lang="ts">
import Vue from 'vue';
import VueMarkdown from 'vue-markdown';
import { WebcomponentModel } from '~/domain/model/WebcomponentModel';

export default Vue.extend({
  name: 'ComponentReadMe',
  components: { VueMarkdown },
  computed: {
    component(): WebcomponentModel {
      return this.$store.state.webcomponent.webcomponent;
    },
    readMe(): string {
      return this.component.versions[0].readMe;
    },
    licenseAgreement(): string {
      return this.component.versions[0].licenseAgreement;
    },
  },
});
</script>
<style lang="scss">
.readme {
  padding-left: 120px;
}

@media (max-width: 992px) {
  .readme {
    padding-left: 15px;
  }
}
</style>
