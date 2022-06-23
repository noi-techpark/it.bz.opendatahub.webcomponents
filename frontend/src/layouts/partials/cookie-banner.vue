<template>
  <div>
    <div v-show="!cookieIsSet" class="alert-cookie">
      <script></script>
      <p>
        <b>Cookies</b>
      </p>
      <p>
        This site or third-party tools used by this make use of cookies
        necessary for the operation and useful for the purposes described in the
        <a href="https://noi.bz.it/en/privacy-cookie-policy"
          >cookie&nbsp;policy</a
        >. If you want to learn more or opt out of all or some cookies, consult
        the cookie policy. By closing this banner, scrolling this page, clicking
        on a link or continuing navigation in any other way, you consent to the
        use of cookies.
      </p>
      <div class="cookie-button-wrapper">
        <a class="button cookie-accept" @click="acceptCookies">Accept</a>
        <a class="button cookie-decline" @click="declineCookies">Decline</a>
      </div>
    </div>
  </div>
</template>

<script>
const cookieValue = document.cookie.replace(
  /(?:(?:^|.*;\s*)banner\s*=\s*([^;]*).*$)|^.*$/,
  '$1'
);

if (cookieValue === 'accepted') matomoTrackingScript();

function matomoTrackingScript() {
  const _paq = (window._paq = window._paq || []);
  /* tracker methods like "setCustomDimension" should be called before "trackPageView" */
  _paq.push(['trackPageView']);
  _paq.push(['enableLinkTracking']);
  (function () {
    const u = 'https://digital.matomo.cloud/';
    _paq.push(['setTrackerUrl', u + 'matomo.php']);
    _paq.push(['setSiteId', '6']);
    const d = document;
    const g = d.createElement('script');
    const s = d.getElementsByTagName('script')[0];
    g.async = true;
    g.src = '//cdn.matomo.cloud/digital.matomo.cloud/matomo.js';
    s.parentNode.insertBefore(g, s);
  })();
}

export default {
  name: 'CookieBanner',
  data() {
    return {
      cookieIsSet: cookieValue === 'accepted' || cookieValue === 'declined',
    };
  },
  methods: {
    acceptCookies() {
      const bannerDiv = document.querySelector('.alert-cookie');
      bannerDiv.style.display = 'none';
      document.cookie = 'banner=accepted; max-age=' + 60 * 60 * 24 * 365;
      matomoTrackingScript();
    },
    declineCookies() {
      const bannerDiv = document.querySelector('.alert-cookie');
      bannerDiv.style.display = 'none';
      document.cookie = 'banner=declined; max-age=' + 60 * 60 * 24 * 365;
    },
  },
};
</script>

<style scoped>
.alert-cookie {
  position: fixed;
  border: solid 1px #6c757d7a;
  bottom: 20px;
  right: 20px;
  z-index: 9999;
  border-radius: 5px;
  background-color: white;
  max-width: 580px;
  width: 50vw;
  padding: 30px;
  box-shadow: 0 0 0.5rem rgb(0 0 0 / 8%) !important;
}

@media screen and (max-width: 768px) {
  .alert-cookie {
    width: auto !important;
    left: 20px;
  }
}

.alert-cookie:first-child {
  color: #2e3131;
}

.alert-cookie p {
  margin-bottom: 10px;
  color: #6c757d;
}

.alert-cookie p a {
  color: #98bd0d !important;
}

.alert-cookie p a:hover {
  color: #7e9e0c !important;
}

.alert-cookie .cookie-button-wrapper {
  display: flex;
  flex-direction: row;
  gap: 20px;
}

.alert-cookie .button {
  margin-top: 20px;
  color: white;
  clear: both;
  display: block;
  padding: 10px 20px;
  width: fit-content;
  background-color: #98bd0d;
  border-radius: 5px;
  cursor: pointer;
  font-weight: 400;
  border: none;
  text-transform: none;
  font-size: medium;
}

.alert-cookie .button:hover {
  text-decoration: underline;
}
</style>
