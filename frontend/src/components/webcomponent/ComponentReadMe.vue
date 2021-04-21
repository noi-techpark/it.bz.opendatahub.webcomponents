<template>
  <div class="container-fluid extended p-2 pb-0 p-sm-5">
    <div class="row">
      <div
        class="col-md-8 d-flex justify-content-between flex-column flex-sm-row w-100 readme"
      >
        <div>
          <h2>#README</h2>

          <div class="d-flex">
            <markdown-page :content="readme"></markdown-page>
          </div>
        </div>
      </div>
      <div class="col-md-4 detail-border pl-md-5">
        <h2>#Author</h2>
        <div
          v-for="author in component.authors"
          :key="author"
          class="d-flex flex-row align-items-center bottom-border"
        >
          <div class="circle-icon"></div>
          <div class="pl-5">
            <div class="font-weight-bold">{{ author.name }}</div>
            <div class="font-weight-bold">{{ author.organization }}</div>
          </div>
        </div>
        <h2 class="pt-4">#Licenceagreeements</h2>
        <div class="d-flex">
          <markdown-page :content="licenceAgreement"></markdown-page>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MarkdownPage from '~/components/markdown-page';
export default {
  name: 'ComponentReadMe',
  components: { MarkdownPage },
  props: {
    component: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  computed: {
    readme() {
      return 'This is a web component that can be integrated in other software (for example websites) to show the activities and point of interests of South Tyrol. They are shown on a map and they can be filtered based on the category or a search term. Each point of interest/activity has a corresponding marker on the map and its details are displayed when it is clicked on it.';
    },
    licenceAgreement() {
      return 'Permissions of this strongest copyleft license are conditioned on making available complete source code of licensed works and modifications, which include larger works using a licensed work, under the same license. Copyright and license notices must be preserved. Contributors provide an express grant of patent rights. When a modified version is used to provide a service over a network, the complete source code of the modified version must be made available.';
    },
  },
};
</script>
<style lang="scss">
.readme {
  padding-left: 105px;
}

@media (max-width: 576px) {
  .readme {
    padding-left: 15px;
  }
}
</style>
