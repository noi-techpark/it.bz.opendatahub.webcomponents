<template>
  <div class="bg-light">
    <div class="container p-4 mb-5 font-larger">
      <h1>Community</h1>
      <p>
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
      for further details.
    </div>
  </div>
</template>
