<template>
  <div>
    <div>
      <div class="container container-extended p-4 pb-0">
        <div class="row">
          <div class="col-6">
            <div>
              <div
                id="widget-tags"
                v-b-toggle.tag-collapse
                style="border-bottom: 2px solid #000;cursor: pointer;"
                class="full-height d-flex justify-content-between font-large pb-2"
                :class="{
                  active:
                    userSelectedTags &&
                    userSelectedTags.length > 0 &&
                    userSelectedTags[0] !== 'any'
                }"
              >
                <span>Filter by categories</span>
                <span class="chevron bottom"></span>
              </div>
              <b-collapse
                id="tag-collapse"
                style="position: absolute;border-left: 1px solid #E8ECF1;border-bottom: 1px solid #E8ECF1;border-right: 1px solid #E8ECF1;"
              >
                <div class="">
                  <b-form-checkbox-group
                    v-if="isLoaded"
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
          <div class="col-6">
            <form @submit.prevent="termSubmitted()">
              <div
                id="widget-search"
                style="border-bottom: 2px solid #000;"
                class="full-height d-flex justify-content-between font-large pb-2"
                :class="{ active: searchTerm && searchTerm !== '' }"
              >
                <div class="full-width pr-2 search-input">
                  <input
                    v-model="searchTerm"
                    type="text"
                    placeholder="Search custom elements"
                    style="outline: none;"
                    class="p-0 font-large full-width"
                    :onkeyup="termUpdated()"
                  />
                </div>

                <svg
                  style="margin-top: 0.15rem; height: 1.5rem;"
                  xmlns="http://www.w3.org/2000/svg"
                  width="31.414"
                  height="32.214"
                  viewBox="0 0 31.414 32.214"
                  class="search-image"
                  @click="termSubmitted()"
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
export default {
  props: {
    selectedTags: {
      default: () => {
        return [];
      },
      type: Array
    },
    searchTerm: { default: '', type: String }
  },
  data() {
    return {
      oldSearchTerm: this.searchTerm,
      availableSearchTags: [],
      userSelectedTags: this.selectedTags,
      isLoaded: false
    };
  },
  mounted() {
    console.log(this.selectedTags, this.searchTerm);

    this.loadSearchTags();
  },
  methods: {
    async loadSearchTags() {
      this.availableSearchTags = await this.$api.searchtag.listAll();
      this.isLoaded = true;
    },
    tagsUpdated() {
      console.log('XXX');
      this.$emit('tags-updated', {
        tags: this.userSelectedTags,
        term: this.searchTerm
      });
    },
    termUpdated() {
      if (this.isNewSearchTerm()) {
        this.oldSearchTerm = this.searchTerm;
        this.$emit('term-updated', {
          tags: this.userSelectedTags,
          term: this.searchTerm
        });
      }
    },
    termSubmitted() {
      // if (this.isNewSearchTerm()) {
      this.oldSearchTerm = this.searchTerm;
      this.$emit('term-submitted', {
        tags: this.userSelectedTags,
        term: this.searchTerm
      });
      // }
    },
    isNewSearchTerm() {
      return this.searchTerm !== this.oldSearchTerm;
    }
  }
};
</script>

<style>
#tag-collapse {
  background-color: white;
  padding: 1.5rem;
  width: 100%;
  font-size: large;
  z-index: 100;
}
</style>
