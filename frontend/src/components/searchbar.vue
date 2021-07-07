<template>
  <div>
    <div>
      <div class="container container-extended p-4 pb-0">
        <div class="row">
          <div class="col-6">
            <div id="tags-zone">
              <div
                id="widget-tags"
                :class="{
                  active:
                    userSelectedTags &&
                    userSelectedTags.length > 0 &&
                    userSelectedTags[0] !== 'any',
                }"
                style="cursor: pointer"
                class="full-height d-flex justify-content-between font-large pb-2 align-items-center"
                @click="searchTagsVisible = !searchTagsVisible"
              >
                <span v-if="userSelectedTags.length === 0" class="filter-text"
                  >Filter by categories</span
                >
                <span v-else class="filter-text-light text-capitalize">{{
                  userSelectedTags.join(', ')
                }}</span>
                <div style="height: 100%">
                  <img
                    v-if="selectedTags.length > 0"
                    src="/icons/close_black.svg"
                    class="clear-icon"
                    style="margin-right: 15px"
                    @click="clearTags"
                  />
                  <span style="padding-top: 15px">
                    <span
                      class="chevron semi-bold mr-2"
                      :class="[searchTagsVisible ? 'top' : 'bottom']"
                    ></span>
                  </span>
                </div>
              </div>
              <b-collapse
                id="tag-collapse"
                v-model="searchTagsVisible"
                style="
                  position: absolute;
                  border-left: 1px solid #e8ecf1;
                  border-bottom: 1px solid #e8ecf1;
                  border-right: 1px solid #e8ecf1;
                  max-height: 500px;
                  overflow-y: scroll;
                "
                class="shadow"
              >
                <div class="m-4">
                  <b-form-checkbox-group
                    id="checkbox-group-2"
                    v-model="userSelectedTags"
                    name="flavour-2"
                    class="text-capitalize d-flex flex-column"
                    @input="tagsUpdated"
                  >
                    <b-form-checkbox
                      v-for="tag in availableSearchTags"
                      :key="tag"
                      :value="tag"
                      >{{ tag }}</b-form-checkbox
                    >
                  </b-form-checkbox-group>
                </div>
              </b-collapse>
            </div>
          </div>
          <div class="col-6 mt-0">
            <form @submit.prevent="termSubmitted()">
              <div
                id="widget-search"
                :class="{ active: searchTerm && searchTerm !== '' }"
                class="full-height d-flex justify-content-between font-large pb-2"
              >
                <div class="full-width pr-2 search-input">
                  <input
                    ref="searchTermInput"
                    v-model="internalSearchTerm"
                    :onkeyup="termUpdated()"
                    type="text"
                    placeholder="Search elements"
                    style="outline: none"
                    class="p-0 full-width search-text"
                  />
                </div>

                <img
                  v-if="searchTerm !== ''"
                  src="/icons/close_black.svg"
                  class="clear-icon cursor-pointer"
                  @click="clearSearch"
                />
                <svg
                  v-else
                  style="margin-top: 0.15rem; height: 1.5rem"
                  xmlns="http://www.w3.org/2000/svg"
                  width="22"
                  height="22"
                  viewBox="0 0 22 22"
                  class="search-image"
                  @click="termSubmitted()"
                >
                  <defs>
                    <style>
                      .a,
                      .b {
                        fill: none;
                      }
                      .b {
                        stroke: #2f2f2f;
                        stroke-miterlimit: 10;
                        stroke-width: 2px;
                      }
                    </style>
                  </defs>
                  <g transform="translate(13.391 13.845)">
                    <g transform="translate(-12.5 -12.845)">
                      <g transform="translate(-0.891 -1)">
                        <g transform="translate(0.929 0.767)">
                          <ellipse
                            class="b"
                            cx="7.535"
                            cy="7.535"
                            rx="7.535"
                            ry="7.535"
                            transform="translate(0.891 1)"
                          />
                          <line
                            class="b"
                            x1="5.699"
                            y1="5.699"
                            transform="translate(13.555 14.171)"
                          />
                        </g>
                      </g>
                    </g>
                  </g>
                </svg>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    selectedTags: {
      default: () => {
        return [];
      },
      type: Array,
    },
    searchTerm: { default: '', type: String },
    focusSearch: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      internalSearchTerm: this.searchTerm,
      oldSearchTerm: this.searchTerm,
      userSelectedTags: this.selectedTags,
      searchTagsVisible: false,
    };
  },
  computed: {
    availableSearchTags() {
      return this.$store.getters['searchtags/getSearchtags'];
    },
  },
  mounted() {
    this.loadSearchTags();

    if (this.focusSearch) {
      this.focusInput();
    }

    window.addEventListener('click', this.tagCollapseListener);
  },
  methods: {
    tagCollapseListener(e) {
      if (!document.getElementById('tags-zone').contains(e.target)) {
        this.searchTagsVisible = false;
      }
    },
    async loadSearchTags() {
      this.availableSearchTags = await this.$api.searchtag.listAll();
      this.isLoaded = true;
    },
    focusInput() {
      this.$refs.searchTermInput.focus();
    },
    tagsUpdated() {
      this.$emit('tags-updated', {
        tags: this.userSelectedTags,
        term: this.internalSearchTerm,
      });
    },
    termUpdated() {
      if (this.isNewSearchTerm()) {
        this.oldSearchTerm = this.internalSearchTerm;
        this.$emit('term-updated', {
          tags: this.userSelectedTags,
          term: this.internalSearchTerm,
        });
        this.$refs.searchTermInput.focus();
      }
    },
    termSubmitted() {
      this.oldSearchTerm = this.internalSearchTerm;
      this.$emit('term-submitted', {
        tags: this.userSelectedTags,
        term: this.internalSearchTerm,
      });
    },
    isNewSearchTerm() {
      return this.internalSearchTerm !== this.oldSearchTerm;
    },
    clearSearch() {
      this.internalSearchTerm = '';
    },
    clearTags() {
      this.userSelectedTags = [];
    },
  },
};
</script>

<style>
#tag-collapse {
  background-color: white;
  width: 100%;
  font-size: large;
  z-index: 100;
}

.custom-checkbox {
  padding-bottom: 8px;
}

.clear-icon {
  margin-bottom: 5px;
}
</style>
