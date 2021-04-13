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
    <b-form ref="theForm" class="pt-3 pb-3" @submit.prevent="onSubmit">
      <b-overlay :show="showOverlay" @click="showOverlay = false">
        <div class="container p-4 contact">
          <div class="row d-flex justify-content-between">
            <div class="col-lg-5">
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
                    type="tel"
                    placeholder=""
                  ></b-form-input>
                </b-form-group>
              </div>
            </div>
            <div class="col-lg-7">
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
                  required
                ></b-form-textarea>
              </b-form-group>
            </div>
          </div>
          <div class="row">
            <div class="col-lg-5">
              <vue-recaptcha
                ref="recaptcha"
                :sitekey="captchaSiteKey"
                :load-recaptcha-script="true"
                @verify="onVerify"
                @expired="onTokenExpired"
              ></vue-recaptcha>
            </div>
            <div
              class="col-lg-7 d-flex justify-content-end align-items-center pt-3 pt-lg-0"
            >
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
                :disabled="!recaptchaChecked"
                >SUBMIT</b-button
              >
            </div>
          </div>
        </div>
        <template #overlay>
          <img v-if="!error" src="/icons/sending_ok.svg" />
          <b-alert variant="danger" :show="error" class="d-block">
            An error has occurred. Please try again!
          </b-alert>
        </template>
      </b-overlay>
    </b-form>
    <div class="bg-light">
      <div class="container contact p-4 pb-5">
        <span class="noi-name font-weight-bold">NOI Techpark</span>
        <span class="noi-name">Tech Transfer - Unit Digital</span>
        <table class="entity mt-2">
          <tr>
            <td class="align-baseline">Phone</td>
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
<script lang="ts">
import Vue from 'vue';
import VueRecaptcha from 'vue-recaptcha';
import { ContactFormRequest } from '../../domain/request/ContactFormRequest';
export default Vue.extend({
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
      showOverlay: false,
      error: false,
    };
  },
  computed: {
    captchaSiteKey(): string {
      return this.$env.RECAPTCHA_PUBLIC_KEY;
    },
  },
  methods: {
    onVerify(recaptchaToken: string): void {
      if (recaptchaToken !== null) {
        this.recaptchaChecked = true;
      }
    },
    onTokenExpired(): void {
      this.recaptchaChecked = false;
      this.$refs.recaptcha.reset();
    },
    onSubmit(): void {
      this.error = false;

      if (this.recaptchaChecked) {
        this._sendForm();
      }
    },
    async _sendForm() {
      const request: ContactFormRequest = {
        category: this.category,
        email: this.email,
        nameFirst: this.firstname,
        nameLast: this.lastname,
        phone: this.telephone,
        text: this.message,
      };

      await this.$api.contact.send(request, () => {
        this.error = true;
      });

      if (!this.error) {
        this.$refs.theForm.reset();
        this.$refs.recaptcha.reset();
      }

      this.showOverlay = true;
    },
  },
});
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

.noi-name {
  font-size: 18px;
}
</style>
