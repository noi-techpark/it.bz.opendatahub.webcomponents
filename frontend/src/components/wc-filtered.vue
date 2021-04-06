<template>
  <div>
    <div v-if="isLoaded" class="bg-light">
      <div class="container container-extended p-4">
        <div
          class="d-flex justify-content-between align-items-center pb-2 flex-wrap"
        >
          <h1 class="components-title">
            {{ currentPage.totalElements }} components
          </h1>
          <div>
            <Paginator
              :current-page="currentPage.number"
              :total-pages="currentPage.totalPages"
              @page-select="toPage($event)"
            />
          </div>
          <div class="d-flex flex-row">
            <div
              @click="previousPage()"
              :class="{ disabled: isFirst }"
              class="btn-circle arrow-left outline mr-2"
            >
              <svg
                id="Ebene_1"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                x="0px"
                y="0px"
                width="48px"
                height="45px"
                viewBox="0 0 48 45"
                style="enable-background:new 0 0 48 45;"
                xml:space="preserve"
              >
                <style type="text/css">
                  .st0 {
                    fill: #ffffff;
                  }
                </style>
                <polygon
                  class="st0"
                  points="21.3,1.3 22.7,2.7 4.4,21 47,21 47,23 4.4,23 23.7,42.3 22.3,43.7 0.6,22 "
                />
              </svg>
            </div>
            <div
              @click="nextPage()"
              :class="{ disabled: isLast }"
              class="btn-circle arrow-right outline"
            >
              <svg
                id="Ebene_1"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                x="0px"
                y="0px"
                width="48px"
                height="45px"
                viewBox="0 0 48 45"
                style="enable-background:new 0 0 48 45;"
                xml:space="preserve"
              >
                <style type="text/css">
                  .st0 {
                    fill: #ffffff;
                  }
                </style>
                <polygon
                  class="st0"
                  points="21.3,1.3 22.7,2.7 4.4,21 47,21 47,23 4.4,23 23.7,42.3 22.3,43.7 0.6,22 "
                />
              </svg>
            </div>
          </div>
        </div>

        <div id="widget-componentcards" v-if="hasContent" class="row">
          <div
            v-for="entry in currentPage.content"
            :key="entry.uuid"
            class="col-sm-6 col-md-4 col-lg-3 mb-4"
          >
            <WebcomponentEntryCard :entry="entry" :return-to="returnTo" />
          </div>
        </div>
        <div v-else class="container text-center h1">
          Your search came up empty.
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Paginator from '~/components/paginator.vue'
import WebcomponentEntryCard from '~/components/webcomponent-entry-card.vue'

export default {
  components: {
    Paginator,
    WebcomponentEntryCard
  },
  props: {
    tags: {
      default: () => {
        return []
      },
      type: Array
    },
    term: { default: '', type: String },
    returnTo: {
      default: null,
      type: String
    }
  },
  data() {
    return {
      isLoaded: false,
      pageSize: 24,
      currentPageNumber: 0,
      currentPage: null,
      timer: null
    }
  },
  computed: {
    hasContent() {
      return !this.currentPage.empty
    },
    isFirst() {
      return this.currentPage.first
    },
    isLast() {
      return this.currentPage.last
    }
  },
  watch: {
    term(newVal, oldVal) {
      if (this.timer) {
        clearTimeout(this.timer)
        this.timer = null
      }

      this.timer = setTimeout(this.update, 350)
    },
    tags(newVal, oldVal) {
      if (this.timer) {
        clearTimeout(this.timer)
        this.timer = null
      }

      this.timer = setTimeout(this.update, 350)
    }
  },
  fetch() {},
  mounted() {
    this.loadPage(this.currentPageNumber, this.pageSize)
  },
  methods: {
    toPage(page) {
      this.currentPageNumber = page
      this.loadPage(this.currentPageNumber, this.pageSize)
    },
    nextPage() {
      console.log('next page')
      if (this.isLast === false) {
        this.loadPage(++this.currentPageNumber, this.pageSize)
      }
    },
    previousPage() {
      if (this.isFirst === false) {
        this.loadPage(--this.currentPageNumber, this.pageSize)
      }
    },
    update() {
      this.currentPageNumber = 0

      this.loadPage(this.currentPageNumber, this.pageSize)
    },
    async loadPage(pageNumber, pageSize) {
      let term = ''
      if (this.term) {
        term = this.term
      }

      let tags = ''
      let filtered = []
      if (Array.isArray(this.tags)) {
        filtered = this.tags.filter((elem) => {
          return elem !== 'any'
        })
        tags = filtered.join(',')
      }
      this.currentPage = await this.$api.webcomponent.findAllPaged(
        pageNumber,
        pageSize,
        tags,
        term
      )

      this.isLoaded = true
    }
  }
}
</script>
<style lang="scss">
@media (max-width: 768px) {
  .components-title {
    width: 100%;
  }
}
</style>
