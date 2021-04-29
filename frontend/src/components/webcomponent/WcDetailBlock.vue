<template>
  <div class="bg-light wc-detail" style="display: flex; flex-direction: column">
    <div v-if="!isHeaderExpanded" class="header-gradient"></div>
    <div
      :class="{ 'detail-block': !isHeaderExpanded }"
      class="container-fluid container-extended p-2 pb-0 pt-3 pt-sm-5 pl-4 pr-4"
    >
      <div class="row">
        <div
          class="col-lg-8 d-flex justify-content-between flex-column flex-sm-row w-100 pl-0"
        >
          <div style="margin-right: 35px">
            <nuxt-link
              :to="returnLink"
              class="btn-circle arrow-left filled-dark return-button"
            >
              <img src="/icons/ic_arrow.svg" class="return-icon" />
            </nuxt-link>
          </div>

          <div style="flex-grow: 1">
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
        <div class="col-lg-4 detail-border pl-lg-5">
          <div class="d-flex flex-row pb-3">
            <div class="performance-col">
              <circular-chart
                :circle-value="versionSizeChartPercent"
                :color="versionSizeChartColor"
              ></circular-chart>
              <div>
                Size
                <span class="font-weight-bold">{{ versionSizeKb }}</span> (kB)
              </div>
            </div>
            <div class="performance-col">
              <circular-chart
                :circle-value="mobileRating"
                :color="mobileRatingChartColor"
              >
                <div
                  class="d-flex justify-content-center align-items-center"
                  style="width: 36px; height: 36px"
                >
                  {{ mobileRating }}
                </div>
              </circular-chart>
              <a
                :href="pageSpeedInsightUrl"
                target="_blank"
                class="text-underline"
                >Mobile perf.</a
              >
            </div>
            <div class="performance-col">
              <circular-chart
                :circle-value="desktopRating"
                :color="desktopRatingChartColor"
              >
                <div
                  class="d-flex justify-content-center align-items-center"
                  style="width: 36px; height: 36px"
                >
                  {{ desktopRating }}
                </div>
              </circular-chart>
              <a
                :href="pageSpeedInsightUrl"
                target="_blank"
                class="text-underline"
                >Desktop perf.</a
              >
            </div>
          </div>
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
            <div class="d-table-row">
              <div class="d-table-cell pr-2">First Published:</div>
              <div class="d-table-cell font-weight-bold">
                {{ $d(new Date(component.datePublished)) }}
              </div>
            </div>
            <div class="d-table-row">
              <div class="d-table-cell pr-2">Current Version:</div>
              <div class="d-table-cell font-weight-bold">
                <template v-if="component.versions.length > 0">
                  {{ component.versions[0].versionTag }}
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
                  >{{ component.repositoryUrl }}</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      class="container-fluid container-extended d-flex justify-content-center flex-row"
    >
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
    <div class="container-fluid container-extended pl-4 pr-4">
      <div
        class="col-md-8 d-flex justify-content-start flex-column flex-md-row w-100 p-0"
      >
        <div style="width: 105px"></div>
        <div class="d-flex flex-row tab-buttons">
          <div
            :class="[showPreview ? 'tab-button' : 'tab-button-disabled']"
            class="text-uppercase"
            @click="$emit('set-show-preview', true)"
          >
            preview
          </div>
          <div
            v-if="hasReadme"
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

<script lang="ts">
import Vue from 'vue';
import { WebcomponentModel } from '../../domain/model/WebcomponentModel';
import CircularChart from '~/components/circular-chart';

export default Vue.extend({
  components: { CircularChart },
  props: {
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
    component(): WebcomponentModel {
      return this.$store.getters['webcomponent/currentWebcomponent'];
    },
    searchTags() {
      return this.component.searchTags.join(', ');
    },
    hasReadme() {
      return (
        this.component.versions &&
        this.component.versions.length > 0 &&
        this.component.versions[0].readMe !== null
      );
    },
    mobileRatingChartColor() {
      if (this.mobileRating < 80) {
        return 'red';
      }

      return 'green';
    },
    desktopRatingChartColor() {
      if (this.desktopRating < 80) {
        return 'red';
      }

      return 'green';
    },
    versionSizeChartColor() {
      if (this.versionSizeChartPercent > 80) {
        return 'red';
      }

      return 'green';
    },
    versionSizeChartPercent() {
      return Math.ceil((100 * Math.min(500, this.versionSizeKb)) / 500);
    },
    mobileRating() {
      if (!this.component.versions || this.component.versions.length === 0) {
        return 0;
      }
      return this.component.versions[0].lighthouseMobilePerformanceRating;
    },
    desktopRating() {
      if (!this.component.versions || this.component.versions.length === 0) {
        return 0;
      }
      return this.component.versions[0].lighthouseDesktopPerformanceRating;
    },
    versionSizeKb() {
      if (!this.component.versions || this.component.versions.length === 0) {
        return 0;
      }
      return this.component.versions[0].distSizeTotalKb;
    },
    authors(authors) {
      return this.component.authors.map((e) => e.name).join(', ');
    },
    pageSpeedInsightUrl() {
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
});
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

@media (min-width: 992px) {
  .detail-content-left {
    padding-left: 120px;
  }
}

.detail-block {
  max-height: 200px;
  overflow: hidden;
}

.header-gradient {
  height: 70px;
  width: 100%;
  margin-top: 131px;
  position: absolute;
  background: linear-gradient(0deg, #e8ecf1, transparent);
  z-index: 1;
}

.expanding-button {
  cursor: pointer;
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
