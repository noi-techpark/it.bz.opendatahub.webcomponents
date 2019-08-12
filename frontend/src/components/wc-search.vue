<template>
  <div>
    <div>
      <div class="container container-extended p-4 pb-0">
        <div class="row">
          <div class="col-6">
            <div>
              <div
                id="widget-tags"
                style="border-bottom: 2px solid #000;cursor: pointer;"
                class="full-height d-flex justify-content-between font-large pb-2"
                :class="{
                  active:
                    userSelectedTags &&
                    userSelectedTags.length > 0 &&
                    userSelectedTags[0] !== 'any'
                }"
                v-b-toggle.tag-collapse
              >
                <span>Filter by categories</span>
                <span class="chevron bottom"></span>
              </div>
              <b-collapse
                id="tag-collapse"
                style="position: absolute;border-left: 1px solid #E8ECF1;border-bottom: 1px solid #E8ECF1;border-right: 1px solid #E8ECF1;"
              >
                <div class="d-flex flex-column">
                  <b-form-checkbox-group
                    v-if="show"
                    id="checkbox-group-2"
                    v-model="userSelectedTags"
                    name="flavour-2"
                    class="text-capitalize"
                    @input="tagsUpdated"
                  >
                    <b-form-checkbox
                      :value="tag"
                      v-for="tag in searchTags"
                      :key="tag"
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
                    type="text"
                    placeholder="Search custom elements"
                    style="outline: none;"
                    class="p-0 font-large full-width"
                    :onkeyup="termUpdated()"
                    v-model="searchTerm"
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
  props: ['selectedTags', 'term'],
  data() {
    return {
      searchTerm: this.term,
      oldSearchTerm: this.term,
      searchTags: [],
      userSelectedTags: this.selectedTags,
      show: false
    };
  },
  mounted() {
    this.loadSearchTags();
    this.$nextTick(() => {
      // this.show = true;
    });
  },
  methods: {
    async loadSearchTags() {
      this.searchTags = await this.$api.searchtag.listAll();
      this.show = true;
    },
    tagsUpdated() {
      console.log('XXX');
      this.$emit('term-updated', {
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
