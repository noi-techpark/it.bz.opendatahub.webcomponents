<template>
  <div>
    <div>
      <div class="container container-extended p-4 pb-0">
        <div class="row">
          <div class="col-6">
            <div class="dropdown">
              <div
                style="border-bottom: 2px solid #000;"
                class="full-height d-flex justify-content-between font-large pb-2"
                id="dropdownMenuButton"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
              >
                <span>Filter by categories</span>
                <span class="chevron bottom"></span>
              </div>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <a class="dropdown-item" href="#">Something else here</a>
              </div>
            </div>
          </div>
          <div class="col-6">
            <div
              id="widget-search"
              style="border-bottom: 2px solid #000;"
              class="full-height d-flex justify-content-between font-large pb-2"
            >
              <div class="full-width pr-2 search-input">
                <input
                  type="text"
                  placeholder="Search custom elements"
                  style="outline: none;"
                  class="p-0 font-large full-width"
                />
              </div>

              <svg
                style="margin-top: 0.15rem; height: 1.5rem;"
                xmlns="http://www.w3.org/2000/svg"
                width="31.414"
                height="32.214"
                viewBox="0 0 31.414 32.214"
                class="search-image"
              >
                <defs>
                  <style>
                    .a,
                    .b {
                      fill: none;
                      stroke: #2e3131;
                      stroke-miterlimit: 10;
                      stroke-width: 2px;
                    }
                    .b {
                      stroke-linecap: round;
                    }
                  </style>
                </defs>
                <g transform="translate(15.5 15.9)">
                  <g transform="translate(-14.5 -14.9)">
                    <circle class="a" cx="11.9" cy="11.9" r="11.9" />
                    <line
                      class="b"
                      x1="9"
                      y1="9"
                      transform="translate(20 20.8)"
                    />
                  </g>
                </g>
              </svg>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="bg-light">
      <div class="container container-extended p-4">
        <div class="float-right font-weight-bold">
          1 &mdash; {{ allList.length }}
        </div>
        <h1>Newest</h1>

        <div id="widget-componentcards" class="row">
          <div v-for="entry in allList" :key="entry.uuid" class="col-3 mb-4">
            <nuxt-link
              :to="'/component/' + entry.uuid"
              style="color:inherit;text-decoration: inherit;"
            >
              <b-card
                img-src="https://picsum.photos/600/300/?image=25"
                img-alt="Image"
                img-top
                class="full-height"
              >
                <b-card-title title-tag="div" class="h4">
                  <span class="text-secondary">#</span>{{ entry.title }}
                </b-card-title>

                <b-card-text class="text-muted">
                  {{ entry.descriptionAbstract }}
                </b-card-text>

                <div slot="footer" class="row font-small">
                  <div class="col-6">
                    <div>
                      Author:
                      <span class="font-weight-bold">
                        <span v-if="entry.authors.length > 0">{{
                          entry.authors[0].name
                        }}</span>
                        <span v-else>unknown</span>
                      </span>
                      <span v-if="entry.authors.length > 1"> et al.</span>
                    </div>
                    <div>
                      Category:
                      <span class="font-weight-bold"
                        ><span
                          v-for="tag in entry.searchTags"
                          :key="tag"
                          class="text-capitalize implode"
                          >{{ tag }}</span
                        ></span
                      >
                    </div>
                  </div>
                  <div class="col-6">
                    <div>
                      Version: <span class="font-weight-bold">3.4</span>
                    </div>
                    <div>
                      License:
                      <span class="font-weight-bold">{{ entry.license }}</span>
                    </div>
                  </div>
                </div>
              </b-card>
            </nuxt-link>
          </div>
        </div>
        <div class="text-center">
          <a
            v-if="currentPage < maxPage"
            href="javascript: void(0);"
            class="text-secondary d-flex flex-column text-decoration-none"
            @click="loadMore()"
          >
            <span>load more components</span>
            <span class="chevron bottom bold"></span>
          </a>
          <nuxt-link
            v-else
            to="#"
            class="text-secondary d-flex flex-column text-decoration-none"
          >
            <span
              >show all components
              <span class="chevron right bold ml-2"></span>
            </span>
          </nuxt-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pageSize: 8,
      currentPage: 0,
      maxPage: 0,
      allList: []
    };
  },
  mounted() {
    this.loadAll();
  },
  methods: {
    async loadAll() {
      const page = await this.$api.webcomponent.listAllPaged(
        this.currentPage,
        this.pageSize
      );

      this.maxPage = Math.min(1, page.totalPages - 1);

      this.allList = page.content;
    },
    async loadMore() {
      // this.currentPage++;

      const page = await this.$api.webcomponent.listAllPaged(
        ++this.currentPage,
        this.pageSize
      );

      this.allList = this.allList.concat(page.content);
    }
  }
};
</script>

<style></style>
