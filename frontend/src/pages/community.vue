<template>
  <div>
    <div class="bg-light">
      <div class="container pt-4 pl-4 pr-4 pb-0 mb-5 font-larger">
        <h1>#Publishers #Contributors #Partners</h1>
        <div>
          Publishers have developed Web Components, that are published on the
          Store.
        </div>
        <div>
          Contributors have developed parts of the Open Data Hub Web Component
          Store itself.
        </div>
        <div>
          Partners worked together to establish the Open Data Hub Web Component
          Store.
        </div>
        <div
          class="row d-flex justify-content-start flex-column flex-md-row pt-5"
        >
          <div class="d-flex flex-row tab-buttons">
            <div
              @click="selectTab('publishers')"
              :class="[
                selectedTab === 'publishers'
                  ? 'tab-button'
                  : 'tab-button-disabled'
              ]"
              class="text-uppercase"
            >
              publishers
            </div>
            <div
              @click="selectTab('contributors')"
              :class="[
                selectedTab === 'contributors'
                  ? 'tab-button'
                  : 'tab-button-disabled'
              ]"
              class="text-uppercase"
            >
              contributors
            </div>
            <div
              @click="selectTab('partners')"
              :class="[
                selectedTab === 'partners'
                  ? 'tab-button'
                  : 'tab-button-disabled'
              ]"
              class="text-uppercase"
            >
              partners
            </div>
          </div>
        </div>
        <!--<p>
          All companies, institutions and experts that are cooperating in this
          project.
        </p>
        <p>
          <a v-scroll-to="'#partners'" href="#" class="button">Partners</a>
          <a v-scroll-to="'#publishers'" href="#" class="button">Publishers</a>
          <a v-scroll-to="'#contributors'" href="#" class="button"
            >Contributors</a
          >
        </p>
        <h2 id="partners">Partners</h2>
        <p>
          Partners worked together to establish the Open Data Hub Web Component
          Store.
        </p>
        <div class="logo_container">
          <a class="img_container" href="https://noi.bz.it" target="_blank">
            <img src="/NOI-logo.png" />
          </a>
          <a
            class="img_container"
            href="https://idm-suedtirol.com"
            target="_blank"
          >
            <img src="/IDM-logo.png" />
          </a>
        </div>
        <h2 id="publishers">Publishers</h2>
        <p>
          Publishers have developed Web Components, that are published on the
          Store.
        </p>
        See Author tags inside
        <router-link class="text-secondary" to="/">Dashboard</router-link>.
        <h2 id="contributors">Contributors</h2>
        <p>
          Contributors have developed parts of the Open Data Hub Web Component
          Store itself.
        </p>
        See
        <a
          target="_blank"
          class="text-secondary"
          href="https://github.com/noi-techpark/odh-web-components-store/graphs/contributors"
          >Contributors on GitHub</a
        >
        for further details.-->
      </div>
    </div>
    <div v-if="selectedTab === 'publishers'" class="container">
      <p>
        Publishers have developed Web Components, that are published on the
        Store.
      </p>
      See Author tags inside
      <router-link class="text-secondary" to="/">Dashboard</router-link>
    </div>
    <div v-else-if="selectedTab === 'contributors'" class="container">
      <p>
        Contributors have developed parts of the Open Data Hub Web Component
        Store itself.
      </p>
      See
      <a
        target="_blank"
        class="text-secondary"
        href="https://github.com/noi-techpark/odh-web-components-store/graphs/contributors"
        >Contributors on GitHub</a
      >
    </div>
    <div v-else-if="selectedTab === 'partners'" class="container">
      <p>
        Partners worked together to establish the Open Data Hub Web Component
        Store.
      </p>
      <div class="logo_container">
        <a class="img_container" href="https://noi.bz.it" target="_blank">
          <img src="/NOI-logo.png" />
        </a>
        <a
          class="img_container"
          href="https://idm-suedtirol.com"
          target="_blank"
        >
          <img src="/IDM-logo.png" />
        </a>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      selectedTab: 'publishers'
    }
  },
  methods: {
    selectTab(tabName) {
      this.selectedTab = tabName
    }
  }
}
</script>
