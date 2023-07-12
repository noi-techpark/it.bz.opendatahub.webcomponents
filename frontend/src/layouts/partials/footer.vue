<!--
SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>

SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
  <div>
    <div class="bg py-3">
      <div class="container">
        <div class="row text-black text-semi-bold">
          <div class="col-12 col-xl-4">
            <a href="/">
              <div class="card h-100 p-3 d-flex flex-row align-items-center">
                <div class="flex-shrink-1">
                  <img src="/icons/footer/open-data-hub.svg" alt="NOI Techpark">
                </div>
                <div class="mx-2 align-self-stretch border-black border-right"></div>
                <ToolBadge class="mr-2" />
                <span class="text-nowrap">Tool to access<br />open data</span>
              </div>
            </a>
          </div>
          <div class="col-12 col-xl-4 mt-2 mt-xl-0">
            <a href="https://opendatahub.com" target="_blank">
              <div class="card h-100 p-3 d-flex flex-row align-items-center">
                <div class="flex-shrink-1">
                  <img class="mr-3" src="/icons/footer/open-data-hub.svg" alt="NOI Techpark">
                </div>
                <span>Place where data and<br />tech are shared</span>
              </div>
            </a>
          </div>
          <div class="col-12 col-xl-4 mt-2 mt-xl-0">
            <a href="https://noi.bz.it" target="_blank">
              <div class="card h-100 p-3 d-flex flex-row align-items-center">
                <div class="flex-shrink-1">
                  <img class="mr-3" src="/icons/footer/noi.svg" alt="NOI Techpark">
                </div>
                <span>Innovation hub in<br />Südtirol/ Alto Adige</span>
              </div>
            </a>
          </div>
        </div>
        <div class="hr-bold border-bottom border-black my-3"></div>
        <div class="row">
          <div class="col-12 col-lg-3">
            <div class="d-flex flex-column">
              <strong class="mb-1">Relevant links</strong>
              <a href="/">Web Components</a>
              <a href="/how-to">How To</a>
              <a href="/validator">Validator</a>
              <a href="https://opendatahub.com/contact" target="_blank">Contact</a>
            </div>
          </div>
          <div class="col-12 col-lg-3 mt-3 mt-lg-0">
            <div class="d-flex flex-column">
              <strong class="mb-1">Quickstart</strong>
              <a href="https://opendatahub.com" target="_blank">Open Data Hub Website</a>
              <a class="text-decoration-underline" href="/">Web Components</a>
              <a href="https://databrowser.opendatahub.com" target="_blank">Databrowser</a>
            </div>
          </div>
          <div class="col-12 col-lg-3 mt-3 mt-lg-0">
            <div class="d-flex flex-column">
              <strong class="mb-1">Contributors</strong>
              <span>The Data Browser is the result of a great collaboration between many awesome people! <a class="text-decoration-underline" href="https://opendatahub.com/community/contributors?project=wcs" target="_blank"> Check them out!</a></span>
            </div>
          </div>
          <div class="col-12 col-lg-3 mt-3 mt-lg-0">
            <div class="d-flex flex-column">
              <strong class="mb-1">Social Media</strong>
              <a href="https://github.com/noi-techpark" target="_blank" class="d-flex align-items-center">
                <img class="mr-2" src="/icons/footer/github.svg" alt="GitHub">
                GitHub
              </a>
              <a href="https://t.me/OpenDataHub" target="_blank" class="d-flex align-items-center">
                <img class="mr-2" src="/icons/footer/telegram.svg" alt="Telegram">
                Telegram
              </a>
              <a href="https://app.element.io/#/room/#opendatahub:matrix.org" target="_blank" class="d-flex align-items-center">
                <img class="mr-2" src="/icons/footer/element.svg" alt="element.io">
                element.io
              </a>
              <a href="https://www.facebook.com/opendatahub/" target="_blank" class="d-flex align-items-center">
                <img class="mr-2" src="/icons/footer/facebook.svg" alt="Facebook">
                Facebook
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="py-3 text-center">
      <span class="text-muted text-small">
        @Open Data Hub | <a class="text-decoration-underline" target="_blank" href="https://noi.bz.it/en/privacy">Privacy</a> | <a class="text-decoration-underline" href="/imprint">Imprint</a>
      </span>
    </div>
  </div>
</template>

<script>
import ToolBadge from '~/components/menu/tool-badge.vue';

export default {
  name: "Footer",
  components: { ToolBadge }
}
</script>

<style scoped>
  * {    
    font-family: "Source Sans Pro", "Open Sans", arial, sans-serif;
    font-size: 20px;
    line-height: 28px;
    color: #3C4043;
  }

  .bg {
    background-color: #F4F8F9;
  }

  .text-muted,
  .text-muted * {
    color: #687182 !important;
  }

  .text-black,
  .text-black * {
    color: black !important;
  }

  .text-decoration-underline {
      text-decoration: underline !important;
  }

  .text-small,
  .text-small * {
    font-size: 14px;
  }

  .text-semi-bold,
  .text-semi-bold * {
    font-weight: 600;
  }

  .border-black {
    border-color: black !important;
  }
</style>
