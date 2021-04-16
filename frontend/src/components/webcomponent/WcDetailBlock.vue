<template>
  <div class="bg-light" style="display: flex; flex-direction: column">
    <div v-if="!isHeaderExpanded" class="header-gradient"></div>
    <div
      :class="{ 'detail-block': !isHeaderExpanded }"
      class="container-fluid extended p-2 pb-0 p-sm-5"
    >
      <div class="row">
        <div
          class="col-md-8 d-flex justify-content-between flex-column flex-sm-row w-100 pl-md-0"
        >
          <div style="margin-right: 35px">
            <nuxt-link
              :to="returnLink"
              class="btn-circle arrow-left filled-dark return-button"
            >
              <img src="/icons/ic_arrow.svg" class="return-icon" />
            </nuxt-link>
          </div>

          <div>
            <h1>#{{ component.title }}</h1>

            <div class="d-flex">
              <div class="full-width mr-2">
                <div>
                  {{ component.descriptionAbstract }}
                </div>
                <div class="text-muted mt-4">
                  {{ component.description }}
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-4 detail-border pl-md-5">
          <div class="d-table w-100 mr-2 ml-2 m-sm-0">
            <div class="d-table-row">
              <div class="d-table-cell pr-2">Author:</div>
              <div class="d-table-cell font-weight-bold">
                {{ component.authors.map((e) => e.name).join(', ') }}
              </div>
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
                <div
                  v-for="author in component.copyrightHolders"
                  :key="author.name"
                >
                  {{ author.name }}
                </div>
              </div>
            </div>
            <div class="d-table-row">
              <div class="d-table-cell pr-2">Category:</div>
              <div class="d-table-cell font-weight-bold">
                <div
                  v-for="tag in component.searchTags"
                  :key="tag"
                  class="text-capitalize"
                >
                  {{ tag }}
                </div>
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
            <div class="d-table-row">
              <div class="d-table-cell pr-2">First Published:</div>
              <div class="d-table-cell font-weight-bold">
                {{ $d(new Date(component.datePublished)) }}
              </div>
            </div>
            <div class="d-table-row">
              <div class="d-table-cell pr-2">Current Version:</div>
              <div class="d-table-cell font-weight-bold">
                {{ component.versions[0].versionTag }}
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
                  >{{ component.repositoryUrl }}</a
                >
              </div>
            </div>
            <div class="d-table-row">
              <div class="d-table-cell pr-2 text-nowrap">Component size:</div>
              <div class="d-table-cell font-weight-bold">#todo</div>
            </div>
            <div class="d-table-row">
              <div class="d-table-cell pr-2 text-nowrap">Performance:</div>
              <div class="d-table-cell font-weight-bold">#todo</div>
            </div>
            <div class="d-table-row">
              <div class="d-table-cell pr-2 text-nowrap">
                PageSpeed Insights:
              </div>
              <div class="d-table-cell font-weight-bold">
                <a
                  :href="pageSpeedInsightUrl"
                  target="_blank"
                  class="font-weight-bold text-underline"
                  >Open external</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="d-flex justify-content-center flex-row">
      <span
        v-if="!isHeaderExpanded"
        class="chevron bottom mr-2 expanding-button"
        @click="toggleHeaderHeight"
      ></span>
      <span
        v-else
        class="chevron top mr-2 expanding-button"
        @click="toggleHeaderHeight"
      ></span>
    </div>
    <div class="container-fluid extended pl-sm-5">
      <div
        class="row col-md-8 d-flex justify-content-start flex-column flex-md-row w-100"
      >
        <div style="width: 90px"></div>
        <div class="d-flex flex-row tab-buttons">
          <div
            :class="[showPreview ? 'tab-button' : 'tab-button-disabled']"
            class="text-uppercase"
            @click="$emit('set-show-preview', true)"
          >
            preview
          </div>
          <div
            :class="[showPreview ? 'tab-button-disabled' : 'tab-button']"
            class="text-uppercase"
            @click="$emit('set-show-preview', false)"
          >
            readme
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    component: {
      type: Object,
      default: () => {
        return {};
      },
    },
    returnLink: {
      type: String,
      default: '',
    },
    showPreview: {
      type: Boolean,
      default: true,
    },
    externalPreviewUrl: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      isHeaderExpanded: false,
    };
  },
  computed: {
    authors(authors) {
      return this.component.authors.map((e) => e.name).join(', ');
    },
    pageSpeedInsightUrl() {
      console.log(process.env.BASE_URL);
      console.log(this.externalPreviewUrl);
      return (
        'https://developers.google.com/speed/pagespeed/insights/?url=' +
        this.externalPreviewUrl
      );
    },
  },
  methods: {
    toggleHeaderHeight() {
      this.isHeaderExpanded = !this.isHeaderExpanded;
    },
  },
};
</script>
<style lang="scss">
@media (max-width: 576px) {
  .return-button {
    height: 35px;
    width: 35px;
  }

  .return-icon {
    height: 15px;
    width: 15px;
    transform: scale(0.6);
  }
}

@media (min-width: 768px) {
  .detail-content-left {
    padding-left: 105px;
  }
}

.detail-block {
  max-height: 200px;
  overflow: hidden;
}

.header-gradient {
  height: 70px;
  width: 100%;
  margin-top: 130px;
  position: absolute;
  background: linear-gradient(0deg, #e8ecf1, transparent);
  z-index: 1;
}

.expanding-button {
  cursor: pointer;
}
</style>
