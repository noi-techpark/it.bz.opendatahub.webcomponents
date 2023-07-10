<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div class="card border-black px-2 align-self-stretch d-flex flex-row align-items-center">
    <span class="text-nowrap">WEB<br />COMPONENTS</span>
  </div>
</template>

<script>
export default {
  name: "ToolBadge"
}
</script>

<style scoped>
  * {
    font-family: "Source Sans Pro", "Open Sans", arial, sans-serif;
    font-weight: 600 !important;
    font-size: 17px !important;
    line-height: 17px !important;
    color: black;
  }

  .border-black {
    border-color: black !important;
  }

  .card {
    min-width: fit-content;
  }
</style>