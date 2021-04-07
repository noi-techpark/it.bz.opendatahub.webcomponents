<template>
  <div class="d-flex justify-content-between contributor-item pt-2 pb-2">
    <div class="d-flex align-items-center">
      <div class="circle-icon"></div>
      <div class="d-flex flex-column pl-5">
        <div class="description">Author</div>
        <div class="description">Organization</div>
      </div>
      <div class="d-flex flex-column pl-5">
        <div class="value">Vorname Nachname/Nickname</div>
        <div class="value">Company name</div>
      </div>
    </div>
    <div class="d-flex align-items-center">
      <div class="d-flex flex-column">
        <div class="description">Commits</div>
        <div class="description">Repositories</div>
      </div>
      <div class="d-flex flex-column pl-5">
        <div class="value">21</div>
        <div class="value">2</div>
      </div>
    </div>
    <div class="d-flex align-items-center">
      <a
        target="_blank"
        class="text-secondary"
        href="https://github.com/noi-techpark/odh-web-components-store/graphs/contributors"
        >Check GitHub data</a
      >
    </div>
  </div>
</template>

<script>
export default {
  name: 'ContributorItem'
}
</script>

<style scoped></style>
