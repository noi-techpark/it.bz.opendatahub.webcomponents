<template>
  <div class="mb-5" v-if="component">
    <div class="bg-light">
      <div class="container p-5">
        <div class="row">
          <div class="col-1">
            <div
              class="btn-circle arrow-left filled-dark"
              @click="$router.push('/')"
            >
              <img src="/icons/ic_arrow.svg" />
            </div>
          </div>
          <div class="col-8">
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
          <div class="col-3 pb-5" style="border-left: 2px solid #d9d9d9;">
            <div class="d-table">
              <div class="d-table-row">
                <div class="d-table-cell pr-2">Author:</div>
                <div class="d-table-cell">
                  <div v-for="author in component.authors" :key="author.name">
                    {{ author.name }}
                  </div>
                </div>
              </div>
              <div class="d-table-row">
                <div class="d-table-cell pr-2">Category:</div>
                <div class="d-table-cell">
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
                <div class="d-table-cell pr-2">Version:</div>
                <div class="d-table-cell"></div>
              </div>
              <div class="d-table-row">
                <div class="d-table-cell pr-2">License:</div>
                <div class="d-table-cell">{{ component.license }}</div>
              </div>
              <div class="d-table-row">
                <div class="d-table-cell pr-2">Published:</div>
                <div class="d-table-cell"></div>
              </div>
              <div class="d-table-row">
                <div class="d-table-cell pr-2">Last Update:</div>
                <div class="d-table-cell"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container container-extended p-4">
      <div class="row">
        <div class="col-8">
          <div class="text-uppercase font-weight-bold mb-2">preview</div>
          <b-card id="widget-preview" class="full-height">
            <b-card-text class="text-center">
              <img
                src="https://static.anychart.com/images/gallery/v8/line-charts-line-chart.png"
              />
            </b-card-text>

            <div slot="footer" class="text-right text-uppercase">
              [] auto update [] update preview
            </div>
          </b-card>
        </div>

        <div class="col-4">
          <div class="text-uppercase font-weight-bold mb-2">configuration</div>
          <b-card id="widget-config" class="full-height">
            <b-card-text>
              Config...
            </b-card-text>

            <div slot="footer" class="text-right text-uppercase">
              [] apply
            </div>
          </b-card>
        </div>
      </div>
      <div class="row mt-5">
        <div class="col-12">
          <div class="text-uppercase font-weight-bold mb-2">code snippet</div>
          <b-card id="widget-codesnippet">
            <b-card-text>
              Code
            </b-card-text>

            <div slot="footer" class="text-right text-uppercase">
              [] COPY
            </div>
          </b-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      component: null
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    async loadData() {
      this.component = await this.$api.webcomponent.getOneById(
        this.$route.params.id
      );
    }
  }
};
</script>

<style lang="scss">
#widget-codesnippet {
  .card-body {
    background-color: #f1f1f1;
  }

  .card-footer {
    background-color: inherit;
  }
}

#widget-config {
  .card-footer {
    background-color: inherit;
  }
}

#widget-preview {
  .card-footer {
    background-color: inherit;
  }
}
</style>
