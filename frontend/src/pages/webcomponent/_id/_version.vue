<template>
  <div v-if="component" class="mb-5">
    <WcDetailBlock
      :component="component"
      :return-link="returnLink"
      :show-preview="showPreview"
      @set-show-preview="setShowPreview"
    ></WcDetailBlock>
    <div v-if="showPreview">
      <div class="container container-extended pt-4 pl-4 pr-4">
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

        <b-alert
          :show="!isLatestVersionActive"
          variant="danger"
          class="mt-4 mb-4"
          >You have not selected the latest version of this
          webcomponent.</b-alert
        >
      </div>
      <div class="container container-extended pb-4 pl-4 pr-4">
        <div class="row">
          <div class="col-md-8">
            <div class="text-uppercase font-weight-bold mb-2">preview</div>
            <b-card id="widget-preview" class="full-height">
              <b-card-text id="twrap" class="text-center">
                <iframe
                  id="tframe"
                  class="full-height full-width"
                  style="min-height: 800px;"
                  title="iframe-preview"
                ></iframe>
              </b-card-text>

              <div
                slot="footer"
                class="text-right text-uppercase d-flex flex-column flex-sm-row"
              >
                <span v-if="!editmode">
                  <b-checkbox
                    v-model="autoUpdate"
                    class="d-inline-block"
                  ></b-checkbox
                  >auto update
                </span>
                <span
                  @click="updatePreview"
                  class="mt-2 mt-sm-0 link_with_icon"
                  style="cursor: pointer;"
                >
                  <font-awesome-icon
                    :icon="['fas', 'redo']"
                    class="ml-4 mr-1"
                  />update preview
                </span>
                <a
                  :href="
                    previewBaseURL +
                      '/preview/' +
                      component.uuid +
                      '/' +
                      selectedVersion +
                      attribs
                  "
                  target="_blank"
                  class="mt-2 mt-sm-0 link_with_icon"
                >
                  <font-awesome-icon
                    :icon="['fas', 'rocket']"
                    class="ml-4 mr-1"
                  />external window
                </a>
              </div>
            </b-card>
          </div>

          <div v-show="!editmode" class="col-md-4 mt-5 mt-md-0">
            <div class="text-uppercase font-weight-bold mb-2">
              configuration
            </div>
            <b-card class="full-height widget-config">
              <b-card-text>
                <WCSConfigTool
                  :config="config.configuration"
                  @snippet="updateSnippet"
                ></WCSConfigTool>
              </b-card-text>

              <!--<div slot="footer" class="text-right text-uppercase">
                <font-awesome-icon :icon="['fas', 'check']" /> apply
              </div>-->
            </b-card>
          </div>
          <div v-show="editmode" class="col-md-4 mt-5 mt-md-0">
            <div class="text-uppercase font-weight-bold mb-2">
              configuration
            </div>
            <b-card
              class="full-height widget-config"
              style="background-color: #fafafa;"
            >
              <b-card-text
                >Configurator disabled. Manual configuration
                active.</b-card-text
              >

              <!--<div slot="footer" class="text-right text-uppercase">
                <font-awesome-icon :icon="['fas', 'check']" /> apply
              </div>-->
            </b-card>
          </div>
        </div>
        <div class="row mt-5">
          <div class="col-12">
            <div class="text-uppercase font-weight-bold mb-2">code snippet</div>
            <b-card
              id="widget-codesnippet"
              :class="{ white: editmode }"
              style="min-height: 250px;"
            >
              <b-card-text>
                <textarea
                  id="code-snippet"
                  v-model="snipp"
                  :readonly="!editmode"
                  class="full-width full-height code-snippet"
                  style="border: 0; background-color: inherit;font-family: 'Courier New', Courier, monospace"
                  rows="10"
                ></textarea>
              </b-card-text>

              <div slot="footer" class="text-right text-uppercase">
                <span
                  v-if="editmode"
                  @click="toggleEditMode()"
                  style="cursor: pointer"
                  class="mr-4"
                >
                  <font-awesome-icon :icon="['fas', 'times']" />RESET
                </span>
                <span
                  v-else
                  @click="toggleEditMode()"
                  style="cursor: pointer"
                  class="mr-4"
                >
                  <font-awesome-icon :icon="['far', 'edit']" />EDIT
                </span>
                <span @click="copySnippetToClipboard()" style="cursor: pointer">
                  <font-awesome-icon :icon="['far', 'copy']" />COPY
                </span>
              </div>
            </b-card>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <component-read-me></component-read-me>
    </div>
  </div>
</template>

<script>
import WCSConfigTool from 'odh-web-components-configurator/src/components/wcs-configurator'
import WcDetailBlock from '../../../components/webcomponent/WcDetailBlock'
import ComponentReadMe from '~/components/webcomponent/ComponentReadMe'

export default {
  components: {
    ComponentReadMe,
    WcDetailBlock,
    WCSConfigTool
  },
  data() {
    return {
      snipp: '',
      snippOriginal: '',
      attribs: '',
      editmode: false,
      component: null,
      config: { configuration: { tagName: '' } },
      autoUpdate: true,
      selectedVersion: null,
      previewBaseURL: '',
      showPreview: true
    }
  },
  computed: {
    returnLink() {
      if (this.$route.query.from) {
        return this.$route.query.from
      }

      return this.localePath('index')
    },
    isLatestVersionActive() {
      if (!this.component) {
        return false
      }
      return this.selectedVersion === this.component.versions[0].versionTag
    }
  },
  watch: {
    selectedVersion(newValue, oldValue) {
      if (oldValue !== null) {
        this.reloadConfig()
      }
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    setShowPreview(show) {
      this.showPreview = show
    },
    toggleEditMode() {
      this.editmode = !this.editmode
      if (this.editmode) {
        this.snippOriginal = this.snipp
      } else {
        this.snipp = this.snippOriginal
        if (this.autoUpdate) {
          this.updatePreview()
        }
      }
    },
    reloadConfig() {
      this.$router.push(
        this.localePath({
          name: 'webcomponent-id-version',
          params: { id: this.$route.params.id, version: this.selectedVersion }
        })
      )
    },
    async loadData() {
      this.component = await this.$api.webcomponent.getOneById(
        this.$route.params.id
      )

      if (this.$route.params.version) {
        this.component.versions.forEach((entry) => {
          if (entry.versionTag === this.$route.params.version) {
            this.selectedVersion = this.$route.params.version
          }
        })
      }

      if (!this.selectedVersion) {
        this.selectedVersion = this.component.versions[0].versionTag
      }

      this.config = await this.$api.webcomponent.getConfigById(
        this.$route.params.id,
        this.selectedVersion
      )

      this.previewBaseURL = this.$api.baseUrl
    },
    updateSnippet(data) {
      this.snipp = data + '\n' + this.getDistIncludes().join('\n')

      if (this.autoUpdate) {
        this.updatePreview()
      }
    },
    copySnippetToClipboard() {
      const copyText = document.getElementById('code-snippet')

      copyText.select()

      document.execCommand('copy')
    },
    updatePreview() {
      const oldElement = document.getElementById('tframe')

      oldElement.parentNode.removeChild(oldElement)

      const newElement = document.createElement('iframe')
      newElement.setAttribute('id', 'tframe')
      newElement.setAttribute('class', 'full-height full-width')
      newElement.setAttribute('style', 'min-height: 800px;')
      newElement.setAttribute('frameborder', '0')

      document.getElementById('twrap').appendChild(newElement)

      newElement.contentDocument.write(this.snipp)
      newElement.contentDocument.close()

      this.attribs = this.parseSnippetAttributes()
    },
    buildAttribute(rawKey, rawValue) {
      const key = rawKey.trim()
      const value = encodeURIComponent(rawValue.trim())
      return key + '="' + value + '";'
    },
    parseSnippetAttributes() {
      let pos = this.snipp.search(this.config.configuration.tagName)
      let result = ''

      if (pos < 0) {
        return result
      }

      pos += this.config.configuration.tagName.length

      let isKey = true
      let isValue = false
      let key = ''
      let value = ''
      let isQuoted = false
      for (let i = pos; i < this.snipp.length; i++) {
        const c = this.snipp.charAt(i)
        if (isKey) {
          switch (c) {
            case '=':
              isKey = false
              isValue = true
              break
            case '>':
              if (key.trim().length > 0) {
                result += key.trim() + ';'
              }
              return '?attribs=' + result
            case ' ':
              if (key.trim().length > 0) {
                result += key.trim() + ';'
              }
              break
            default:
              key += c
          }
        } else if (isValue) {
          switch (c) {
            case '"':
              if (isQuoted) {
                result += this.buildAttribute(key, value)
                isKey = true
                isValue = false
                isQuoted = false
                key = ''
                value = ''
              } else {
                isQuoted = true
              }
              break
            case ' ':
              if (isQuoted) {
                value += ' '
              } else {
                result += this.buildAttribute(key, value)
                isKey = true
                isValue = false
                key = ''
                value = ''
              }
              break
            case '>':
              if (isQuoted) {
                value += '>'
              } else {
                result += this.buildAttribute(key, value)
                return '?attribs=' + result
              }
              break
            default:
              value += c
          }
        }
      }
      return '?attribs=' + result
    },
    getDistIncludes() {
      const scripts = []

      // Wait until the async loadData method has finished
      if (this.config.hasOwnProperty('dist')) {
        if (
          this.config.dist.hasOwnProperty('scripts') &&
          this.config.dist.scripts.length > 0
        ) {
          this.config.dist.scripts.forEach((item) => {
            scripts.push(
              '<script ' +
                item.parameter +
                ' src="' +
                this.config.deliveryBaseUrl +
                '/' +
                this.config.dist.basePath +
                '/' +
                item.file +
                '"></scr' +
                'ipt>'
            )
          })
        } else {
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
            )
          })
        }
      }

      return scripts
    }
  }
}
</script>

<style lang="scss">
#widget-codesnippet {
  .card-body {
    background-color: #fafafa;
  }

  &.white .card-body {
    background-color: white;
  }

  .card-footer {
    background-color: inherit;
  }
}

.widget-config {
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
