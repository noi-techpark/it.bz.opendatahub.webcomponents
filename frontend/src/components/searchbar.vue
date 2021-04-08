<template>
  <div>
    <div>
      <div class="container container-extended p-4 pb-0">
        <div class="row">
          <div class="col-6">
            <div class="">
              <div
                id="widget-tags"
                :class="{
                  active:
                    userSelectedTags &&
                    userSelectedTags.length > 0 &&
                    userSelectedTags[0] !== 'any'
                }"
                @click="searchTagsVisible = !searchTagsVisible"
                style="cursor: pointer;"
                class="full-height d-flex justify-content-between font-large pb-2 align-items-center"
              >
                <span class="filter-text">Filter by categories</span>
                <span class="chevron bottom mr-2"></span>
              </div>
              <b-collapse
                id="tag-collapse"
                v-model="searchTagsVisible"
                style="position: absolute;border-left: 1px solid #E8ECF1;border-bottom: 1px solid #E8ECF1;border-right: 1px solid #E8ECF1;"
              >
                <div class="m-4">
                  <b-form-checkbox-group
                    id="checkbox-group-2"
                    @input="tagsUpdated"
                    v-model="userSelectedTags"
                    name="flavour-2"
                    class="text-capitalize d-flex flex-column"
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
                    :onkeyup="termUpdated()"
                    v-model="internalSearchTerm"
                    type="text"
                    placeholder="Search elements"
                    style="outline: none;"
                    class="p-0 full-width search-text"
                  />
                </div>

                <svg
                  @click="termSubmitted()"
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
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { searchtagsStore } from '~/utils/store-accessor'

export default {
  props: {
    selectedTags: {
      default: () => {
        return []
      },
      type: Array
    },
    searchTerm: { default: '', type: String }
  },
  data() {
    return {
      internalSearchTerm: this.searchTerm,
      oldSearchTerm: this.searchTerm,
      userSelectedTags: this.selectedTags,
      searchTagsVisible: false
    }
  },
  computed: {
    availableSearchTags() {
      return searchtagsStore.getSearchtags
    }
  },
  mounted() {
    this.focusInput()
  },
  methods: {
    focusInput() {
      this.$refs.searchTermInput.focus()
    },
    tagsUpdated() {
      this.$emit('tags-updated', {
        tags: this.userSelectedTags,
        term: this.internalSearchTerm
      })
    },
    termUpdated() {
      if (this.isNewSearchTerm()) {
        this.oldSearchTerm = this.internalSearchTerm
        this.$emit('term-updated', {
          tags: this.userSelectedTags,
          term: this.internalSearchTerm
        })
      }
    },
    termSubmitted() {
      this.oldSearchTerm = this.internalSearchTerm
      this.$emit('term-submitted', {
        tags: this.userSelectedTags,
        term: this.internalSearchTerm
      })
    },
    isNewSearchTerm() {
      return this.internalSearchTerm !== this.oldSearchTerm
    }
  }
}
</script>

<style>
#tag-collapse {
  background-color: white;
  /*padding: 1.5rem;*/
  width: 100%;
  font-size: large;
  z-index: 100;
}

.custom-checkbox {
  padding-bottom: 8px;
}
</style>
