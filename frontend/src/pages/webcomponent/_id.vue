<template>
  <div v-if="component" class="mb-5">
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
                <div class="d-table-cell">
                  {{ component.versions[0].versionTag }}
                </div>
              </div>
              <div class="d-table-row">
                <div class="d-table-cell pr-2">License:</div>
                <div class="d-table-cell">{{ component.license }}</div>
              </div>
              <div class="d-table-row">
                <div class="d-table-cell pr-2">Published:</div>
                <div class="d-table-cell">
                  {{ $d(new Date(component.datePublished)) }}
                </div>
              </div>
              <div class="d-table-row">
                <div class="d-table-cell pr-2 text-nowrap">Last Update:</div>
                <div class="d-table-cell">
                  {{ $d(new Date(component.dateUpdated)) }}
                </div>
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
              <iframe
                id="tframe"
                class="full-height full-width"
                style="min-height: 350px;"
                frameborder="0"
              ></iframe>
            </b-card-text>

            <div slot="footer" class="text-right text-uppercase">
              <b-checkbox
                class="d-inline-block"
                v-model="autoUpdate"
              ></b-checkbox>
              auto update
              <span style="cursor: pointer;" @click="updatePreview"
                ><font-awesome-icon :icon="['fas', 'redo']" class="ml-4" />
                update preview</span
              >
            </div>
          </b-card>
        </div>

        <div class="col-4">
          <div class="text-uppercase font-weight-bold mb-2">configuration</div>
          <b-card id="widget-config" class="full-height">
            <b-card-text>
              <wcs-config-tool ref="cfig">{{
                config.configuration
              }}</wcs-config-tool>
            </b-card-text>

            <div slot="footer" class="text-right text-uppercase">
              <font-awesome-icon :icon="['fas', 'check']" /> apply
            </div>
          </b-card>
        </div>
      </div>
      <div class="row mt-5">
        <div class="col-12">
          <div class="text-uppercase font-weight-bold mb-2">code snippet</div>
          <b-card id="widget-codesnippet">
            <b-card-text>
              <textarea
                id="code-snippet"
                v-model="snipp"
                class="full-width full-height"
                style="border: 0; background-color: inherit;"
              ></textarea>
            </b-card-text>

            <div slot="footer" class="text-right text-uppercase">
              <a href="javascript:void(0);" @click="copySnippetToClipboard()"
                ><font-awesome-icon :icon="['far', 'copy']" /> COPY</a
              >
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
      snipp: '',
      show: false,
      component: null,
      config: { configuration: { tagName: '' } },
      autoUpdate: true
    };
  },
  mounted() {
    this.loadData();
    this.show = true;
    this.initEventListener();
  },

  methods: {
    async loadData() {
      this.component = await this.$api.webcomponent.getOneById(
        this.$route.params.id
      );

      this.config = await this.$api.webcomponent.getConfigById(
        this.$route.params.id
      );
    },
    updateSnippet(data) {
      console.log(data);
      this.snipp = data.detail[0] + '\n' + this.getDistIncludes().join('\n');

      if (this.autoUpdate) {
        this.updatePreview();
      }
    },
    copySnippetToClipboard() {
      const copyText = document.getElementById('code-snippet');

      copyText.select();

      document.execCommand('copy');
    },
    updatePreview() {
      document.getElementById('tframe').contentDocument.close();
      document.getElementById('tframe').contentDocument.write(this.snipp);
    },
    getDistIncludes() {
      const scripts = [];

      this.config.dist.files.forEach((item) => {
        scripts.push(
          '<script src="' +
            this.config.deliveryBaseUrl +
            '/' +
            this.config.dist.basePath +
            '/' +
            item +
            '"></scr' +
            'ipt>'
        );
      });

      return scripts;
    },
    initEventListener() {
      console.log(this.$refs.cfig);
      if (this.$refs.cfig === undefined) {
        setTimeout(this.initEventListener, 50);
        return;
      }
      this.$refs.cfig.addEventListener('snippet', this.updateSnippet);
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
