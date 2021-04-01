<template>
  <footer>
    <div class="p-5 text-center font-larger container">
      <div class="text-uppercase h1">#Publishers #Contributors #Partners</div>
      <hr style="width: 50px; background-color: white" />
      <b-row class="quote-row">
        <b-col cols="6" lg="3" md="6" sm="6" style="display: flex">
          <quote-item
            title="Contributing"
            quote="Often the best way to get them is to put our hands into starting new
            projects or contributing to existing ones."
          ></quote-item>
        </b-col>
        <b-col cols="6" lg="3" md="6" sm="6" style="display: flex">
          <quote-item
            title="Sharing"
            quote="There are several reasons you might want to contribute to open source software."
          ></quote-item>
        </b-col>
        <b-col cols="6" lg="3" md="6" sm="6" style="display: flex">
          <quote-item
            title="Knowledge"
            quote="It's useful regardless of whether or not you're a student."
          ></quote-item>
        </b-col>
        <b-col cols="6" lg="3" md="6" sm="6" style="display: flex">
          <quote-item
            title="Networking"
            quote="The fulfilling feeling of giving back and contributing to society is unparalleled."
          ></quote-item>
        </b-col>
      </b-row>
      <a href="https://noi.bz.it/en/privacy-cookie-policy" class="text-muted"
        >learn more about or community</a
      >
    </div>
    <div class="get-in-touch">
      <div class="p-5 text-center font-larger">
        <div class="text-uppercase h1 footer-text">get in touch</div>
        <hr style="width: 50px; background-color: white" />
        <div class="h1 footer-text">
          If you would like to become a member of the Open Data Hub Community as
          a Data Provider, Data Consumer, Web Component User or as a
          Contributer, you can meet us at our events, or get in touch with us
          through e-mail or contact form.
        </div>
      </div>
    </div>
    <div class="bg-light">
      <div class="container container-extended p-5">
        <div id="widget-imprint" class="text-center text-uppercase">
          <nuxt-link to="/imprint" class="text-muted">imprint</nuxt-link>
          <a
            href="https://noi.bz.it/en/privacy-cookie-policy"
            class="text-muted"
            >PRIVACY & COOKIE POLICY</a
          >
        </div>
        <div class="d-flex justify-content-between mt-5">
          <div class="font-smaller">
            <div class="font-weight-bold">
              Open Data Hub,
              <a href="mailto:info@opendatahub.bz.it">info@opendatahub.bz.it</a>
            </div>
            <a href="https://konverto.eu" target="_blank" class="text-muted"
              >web by KONVERTO</a
            >
          </div>
          <div>
            <div v-scroll-to="'#__nuxt'" class="btn-circle arrow-up filled">
              <img src="/icons/ic_arrow.svg" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<style lang="scss">
@import 'src/assets/styles/bootstrap/_variables.scss';
#widget-imprint {
  a {
    margin-left: 1rem;
    margin-right: 1rem;
  }
}

.get-in-touch {
  background-color: $secondary;
}

.footer-text {
  color: white;
  margin-left: 10%;
  margin-right: 10%;
}

.quote-row {
  margin-top: 30px;
  margin-bottom: 50px;
}
</style>
<script>
import QuoteItem from '~/components/quote-item'
export default {
  components: { QuoteItem }
}
</script>
