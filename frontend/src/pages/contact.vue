<template>
  <div>
    <div class="bg-light">
      <div class="container contact p-4 font-larger">
        <h1>
          #ContactOPENDATAHUB #becomeamember #DataProvider #Data Consumer
          #WebComponent User #Contributer
        </h1>
        <p>
          If you would like to become a member of the Open Data Hub Community as
          a Data Provider, Data Consumer, Web Component User or as a
          Contributer, you can meet us at our events, or get in touch with us
          through e-mail. If you find a bug in a web component or like to share
          your ideas on how to improve them, have a look at the provided
          repository links and drop a comment their, or write us an e-mail.
        </p>
        <p>
          <a class="text-secondary" href="mailto:help@opendatahub.bz.it"
            >help@opendatahub.bz.it</a
          >
        </p>
      </div>
    </div>
    <b-form @submit="onSubmit">
      <div class="container p-4 contact">
        <div class="row d-flex justify-content-between" style="height: 250px">
          <div class="col-md-5">
            <b-form-group
              id="input-group-category"
              label="Select category"
              label-for="input-category"
            >
              <b-form-select
                v-model="category"
                :options="categories"
              ></b-form-select>
            </b-form-group>
            <div class="row">
              <b-form-group
                id="input-group-firstname"
                label="Firstname"
                label-for="input-firstname"
                class="pr-1 col-6"
              >
                <b-form-input
                  id="input-firstname"
                  v-model="firstname"
                  type="text"
                  placeholder=""
                  required
                ></b-form-input>
              </b-form-group>
              <b-form-group
                id="input-group-lastname"
                label="Lastname"
                label-for="input-lastname"
                class="col-6"
              >
                <b-form-input
                  id="input-lastname"
                  v-model="lastname"
                  type="tel"
                  placeholder=""
                  required
                ></b-form-input>
              </b-form-group>
            </div>
            <div class="row">
              <b-form-group
                id="input-group-email"
                label="E-mail"
                label-for="input-email"
                class="pr-1 col-6"
              >
                <b-form-input
                  id="input-email"
                  v-model="email"
                  type="email"
                  placeholder=""
                  required
                ></b-form-input>
              </b-form-group>
              <b-form-group
                id="input-group-telephone"
                label="Tel"
                label-for="input-telephone"
                class="col-6"
              >
                <b-form-input
                  id="input-telephone"
                  v-model="telephone"
                  type="phone"
                  placeholder=""
                ></b-form-input>
              </b-form-group>
            </div>
          </div>
          <div class="col-md-7">
            <b-form-group
              id="input-group-textarea"
              label="Message"
              label-for="textarea"
              style="height: 100%"
            >
              <b-form-textarea
                id="textarea"
                v-model="message"
                placeholder="Enter something..."
                style="height: 197px"
              ></b-form-textarea>
            </b-form-group>
          </div>
        </div>
        <div class="row">
          <div class="col-md-5">
            <vue-recaptcha
              ref="recaptcha"
              sitekey=""
              :load-recaptcha-script="true"
              @verify="onVerify"
              @expired="onTokenExpired"
            ></vue-recaptcha>
          </div>
          <div class="col-md-7 d-flex justify-content-end align-items-center">
            <b-button
              type="reset"
              variant="outline-primary"
              class="form-button mr-3"
              >RESET</b-button
            >
            <b-button
              type="submit"
              variant="primary"
              class="form-button"
              @click="onSubmit"
              >SUBMIT</b-button
            >
          </div>
        </div>
      </div>
    </b-form>
    <div class="bg-light">
      <div class="container contact p-4 pb-5">
        <h2>NOI Techpark</h2>
        <h3>Tech Transfer - Unit Digital</h3>
        <table class="entity">
          <tr>
            <td>Phone</td>
            <td>+39 0471 066 600</td>
          </tr>
          <tr>
            <td>Web Page</td>
            <td>
              <a class="text-secondary" href="https://noi.bz.it"
                >https://noi.bz.it</a
              >
            </td>
          </tr>
          <tr>
            <td>Email</td>
            <td>
              <a class="text-secondary" href="mailto:info@noi.bz.it"
                >info@noi.bz.it</a
              >
            </td>
          </tr>
          <tr>
            <td>PEC</td>
            <td>
              <a class="text-secondary" href="mailto:noi@pec.noi.bz.it"
                >noi@pec.noi.bz.it</a
              >
            </td>
          </tr>
          <tr>
            <td>Address</td>
            <td>Volta str., 13/A, I-39100 Bolzano, Italy</td>
          </tr>
          <tr>
            <td>VAT No.</td>
            <td>IT 02595720216</td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>
<script>
import VueRecaptcha from 'vue-recaptcha';
export default {
  components: {
    VueRecaptcha,
  },
  data() {
    return {
      firstname: '',
      lastname: '',
      email: '',
      telephone: '',
      message: '',
      category: '#becomeamember',
      categories: ['#becomeamember'],
      recaptchaChecked: false,
    };
  },
  methods: {
    onVerify(recaptchaToken) {
      console.log(recaptchaToken);
      if (recaptchaToken !== null) {
        this.recaptchaChecked = true;
      }
    },
    onTokenExpired() {
      this.recaptchaChecked = false;
      this.$refs.recaptcha.reset();
    },
    onSubmit(event) {
      event.preventDefault();
      console.log(this.recaptchaChecked);
    },
  },
};
</script>
<style>
@media (min-width: 1400px) {
  .contact {
    max-width: 1300px;
  }
}

@media (min-width: 1650px) {
  .contact {
    max-width: 1500px;
  }
}

.form-button {
  width: 280px;
}
</style>
