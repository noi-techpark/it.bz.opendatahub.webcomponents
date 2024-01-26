<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div class="wc-detail" style="display: flex; flex-direction: column">
    <div class="container-fluid container-extended p-2 pb-0 pt-1 pt-sm-1 pl-0 pr-0">
      <div class="row">
        <div
          class="
            col-lg-12
            d-flex
            justify-content-between
            flex-column flex-sm-row
            w-100
            pl-0
          "
        >
          <div style="flex-grow: 1">
            <div class="d-flex">
              <div class="full-width mr-2">

                {{ component.description }}

                
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-12 pl-0 pt-3">
          
          <div>
            Contact us at
            <a href="mailto:help@opendatahub.com">help@opendatahub.com</a>
          </div>
          <div
            v-if="
              component.copyrightHolders &&
              component.copyrightHolders.length > 0
            "
            class="d-table-row"
          >
            <div class="d-table-cell pr-2">Copyright holder:</div>
            <div class="d-table-cell font-weight-bold">
              <span
                v-if="!detailedCopyright"
                class="cursor-pointer text-underline"
                @click="detailedCopyright = true"
                >{{ copyrightHolders }}</span
              >
              <div v-else>
                <div
                  v-for="author in component.copyrightHolders"
                  :key="'c::' + author"
                >
                  <div>
                    {{ author.name }}
                    <span v-if="author.email" class="font-weight-normal">{{
                      author.email
                    }}</span>
                  </div>
                  <div>
                    {{ author.organization }}
                    <a
                      v-if="author.organizationUrl"
                      :href="author.organizationUrl"
                      target="_blank"
                      class="font-weight-normal"
                      >{{ author.organizationUrl }}</a
                    >
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="d-table w-100 mr-2 ml-2 m-sm-0">
            <div class="d-table-row">
              <div class="d-table-cell pr-2">Author:</div>
              <div class="d-table-cell font-weight-bold">
                <span
                  v-if="!detailedAuthors"
                  class="cursor-pointer text-underline"
                  @click="detailedAuthors = true"
                  >{{ authors }}</span
                >
                <div v-else>
                  <div
                    v-for="author in component.authors"
                    :key="'a::' + author"
                  >
                    <div>
                      {{ author.name }}
                      <span v-if="author.email" class="font-weight-normal">{{
                        author.email
                      }}</span>
                    </div>
                    <div>
                      {{ author.organization }}
                      <a
                        v-if="author.organizationUrl"
                        :href="author.organizationUrl"
                        target="_blank"
                        class="font-weight-normal"
                        >{{ author.organizationUrl }}</a
                      >
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="d-table-row">
              <div class="d-table-cell pr-2">Category:</div>
              <div class="d-table-cell font-weight-bold text-capitalize">
                {{ searchTags }}
              </div>
            </div>
            <div class="d-table-row">
              <div class="d-table-cell pr-2">License:</div>
              <div class="d-table-cell font-weight-bold">
                <a
                  v-if="component.license && component.license.seeAlso"
                  :href="component.license.seeAlso[0]"
                  :title="component.license.name"
                  target="_blank"
                  >{{ component.license.licenseId }}</a
                >
                <div v-else-if="component.license" class="font-weight-bold">
                  {{ component.license.licenseId }}
                </div>
                <div v-else class="font-weight-bold">
                  {{ component.licenseString }}
                </div>
              </div>
            </div>
            
          </div>
        </div>
        <div class="col-lg-12  pl-0">
            <div class="d-table w-100 mr-2 ml-2 m-sm-0">
            <div class="d-table-row">
              <div class="d-table-cell pr-2">First Published:</div>
              <div class="d-table-cell font-weight-bold">
                {{ $d(new Date(component.datePublished)) }}
              </div>
            </div>
            <div class="d-table-row">
              <div class="d-table-cell pr-2">Latest Version:</div>
              <div class="d-table-cell font-weight-bold">
                <template v-if="component.versions.length > 0">
                  {{ component.versions[1].versionTag }}
                </template>
                <template v-else> n/a </template>
              </div>
            </div>
            <div class="d-table-row">
              <div class="d-table-cell pr-2 text-nowrap">Last Update:</div>
              <div class="d-table-cell font-weight-bold">
                {{ $d(new Date(component.dateUpdated)) }}
              </div>
            </div>
            <div v-if="component.repositoryUrl" class="d-table-row">
              <div class="d-table-cell pr-2 text-nowrap">Repository:</div>
              <div class="d-table-cell">
                <a
                  :href="component.repositoryUrl"
                  target="_blank"
                  class="font-weight-bold text-underline"
                  >Go to repository</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container-fluid container-extended pl-4 pr-4">
      <div
        class="
          col-md-8
          d-flex
          justify-content-start
          flex-column flex-md-row
          w-100
          p-0
        "
      >
        <div style="width: 105px"></div>
        <div class="d-flex flex-row tab-buttons">
          <!-- <div
            :class="[showPreview ? 'tab-button' : 'tab-button-disabled']"
            class="text-uppercase"
            @click="$emit('set-show-preview', true)"
          >
            preview
          </div> -->
          <!-- <div
            :class="[showPreview ? 'tab-button-disabled' : 'tab-button']"
            class="text-uppercase"
            @click="$emit('set-show-preview', false)"
          >
            readme
          </div> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { WebcomponentModel } from '~/domain/model/WebcomponentModel';

export default Vue.extend({
  components: {},
  props: {
    returnLink: {
      type: String,
      default: '',
    },
    // showPreview: {
    //   type: Boolean,
    //   default: true,
    // },
  },
  data() {
    return {
        showPreview:true,
        detailedCopyright: false,
        detailedAuthors: false,
    };
  },
  computed: {
    component(): WebcomponentModel {
      return this.$store.state.webcomponent.webcomponent;
    },
    searchTags(): string {
      return this.component.searchTags.join(', ');
    },
    hasReadme(): boolean {
      return (
        this.component.versions &&
        this.component.versions.length > 0 &&
        this.component.versions[0].readMe !== null
      );
    },
    
    authors(): string {
      return this.component.authors.map((e) => e.name).join(', ');
    },
    copyrightHolders(): string {
      return this.component.copyrightHolders.map((e) => e.name).join(', ');
    },
    externalPreviewBaseUrl(): string {
      return (
        (this as any).$api.baseUrl +
        this.$store.getters['webcomponent/externalPreviewBaseUrl']
      );
    },
  },
  methods: {

  },
});
</script>
<style lang="scss">
// @media (min-width: 992px) {
//   .detail-content-left {
//     padding-left: 120px;
//   }
// }

.detail-block {
  max-height: 200px;
  overflow: hidden;
}

.header-gradient {
  height: 70px;
  width: 100%;
  margin-top: 131px;
  position: absolute;
//   background: linear-gradient(0deg, #e8ecf1, transparent);
  z-index: 1;
}

.performance-col {
  display: flex;
  flex-direction: column;
  width: 33%;
  justify-content: center;
  align-items: center;
  font-size: 14px;
}
</style>
