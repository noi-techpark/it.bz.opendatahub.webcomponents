<template>
  <div v-if="component" class="mb-5">
    <div class="bg-light">
      <div class="container p-5">
        <div class="row">
          <div class="col-1">
            <nuxt-link
              :to="returnLink"
              class="btn-circle arrow-left filled-dark"
            >
              <img src="/icons/ic_arrow.svg" />
            </nuxt-link>
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
                <div class="d-table-cell pr-2">License:</div>
                <div class="d-table-cell">{{ component.license.name }}</div>
              </div>
              <div class="d-table-row">
                <div class="d-table-cell pr-2">
                  First Published:
                </div>
                <div class="d-table-cell">
                  {{ $d(new Date(component.datePublished)) }}
                </div>
              </div>
              <div class="d-table-row">
                <div class="d-table-cell pr-2">Current Version:</div>
                <div class="d-table-cell">
                  {{ component.versions[0].versionTag }}
                </div>
              </div>
              <div class="d-table-row">
                <div class="d-table-cell pr-2 text-nowrap">Last Update:</div>
                <div class="d-table-cell">
                  {{ $d(new Date(component.dateUpdated)) }}
                </div>
              </div>
              <div v-if="component.repositoryUrl" class="d-table-row">
                <div class="d-table-cell pr-2 text-nowrap">Repository:</div>
                <div class="d-table-cell">
                  <a :href="component.repositoryUrl" target="_blank">open</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      v-if="component.uuid !== '226662ad-41c2-4e55-b11f-271d72d30bd4'"
      class="container container-extended pt-4 pl-4 pr-4"
    >
      <div class="text-right h3">
        Version
        <b-form-select v-model="selectedVersion" style="max-width:150px;">
          <option
            v-for="version in component.versions"
            :key="version.versionTag"
            >{{ version.versionTag }}</option
          >
        </b-form-select>
      </div>

      <b-alert :show="!isLatestVersionActive" variant="danger" class="mt-4 mb-4"
        >You have not selected the latest version of this webcomponent.</b-alert
      >
    </div>
    <div
      v-if="component.uuid !== '226662ad-41c2-4e55-b11f-271d72d30bd4'"
      class="container container-extended pb-4 pl-4 pr-4"
    >
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
                v-model="autoUpdate"
                class="d-inline-block"
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

            <!--<div slot="footer" class="text-right text-uppercase">
              <font-awesome-icon :icon="['fas', 'check']" /> apply
            </div>-->
          </b-card>
        </div>
      </div>
      <div class="row mt-5">
        <div class="col-12">
          <div class="text-uppercase font-weight-bold mb-2">code snippet</div>
          <b-card id="widget-codesnippet" style="min-height: 250px;">
            <b-card-text>
              <textarea
                id="code-snippet"
                v-model="snipp"
                class="full-width full-height"
                style="border: 0; background-color: inherit;font-family: 'Courier New', Courier, monospace"
                disabled
              ></textarea>
            </b-card-text>

            <div slot="footer" class="text-right text-uppercase">
              <span style="cursor: pointer" @click="copySnippetToClipboard()"
                ><font-awesome-icon :icon="['far', 'copy']" /> COPY</span
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
      autoUpdate: true,
      selectedVersion: null
    };
  },
  computed: {
    returnLink() {
      if (this.$route.query.from) {
        return this.$route.query.from;
      }

      return this.localePath('index');
    },
    isLatestVersionActive() {
      if (!this.component) {
        return false;
      }
      return this.selectedVersion === this.component.versions[0].versionTag;
    }
  },
  watch: {
    selectedVersion(newValue, oldValue) {
      if (oldValue !== null) {
        this.reloadConfig();
      }
    }
  },
  mounted() {
    this.loadData();
    this.show = true;
    this.initEventListener();
  },
  methods: {
    reloadConfig() {
      this.$router.push(
        this.localePath({
          name: 'webcomponent-id-version',
          params: { id: this.$route.params.id, version: this.selectedVersion }
        })
      );
    },
    async loadData() {
      this.component = await this.$api.webcomponent.getOneById(
        this.$route.params.id
      );

      if (this.$route.params.version) {
        this.component.versions.forEach((entry) => {
          if (entry.versionTag === this.$route.params.version) {
            this.selectedVersion = this.$route.params.version;
          }
        });
      }

      if (!this.selectedVersion) {
        this.selectedVersion = this.component.versions[0].versionTag;
      }

      this.config = await this.$api.webcomponent.getConfigById(
        this.$route.params.id,
        this.selectedVersion
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
      if (
        this.component &&
        this.component.uuid === '226662ad-41c2-4e55-b11f-271d72d30bd4'
      ) {
        return;
      }

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

.d-table {
  border-collapse: separate;
  border-spacing: 0 0.5rem;
}
</style>
