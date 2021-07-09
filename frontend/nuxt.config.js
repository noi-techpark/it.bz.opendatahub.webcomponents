// eslint-disable-next-line nuxt/no-cjs-in-config
module.exports = {
  ssr: false,

  srcDir: 'src/',

  /*
   ** Headers of the page
   */
  head: {
    title: 'ODH Webcomponents',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      {
        hid: 'description',
        name: 'description',
        content: 'ODH Webcomponents',
      },
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }],
  },
  /*
   ** Customize the progress-bar color
   */
  loading: { color: '#fff' },
  /*
   ** Global CSS
   */
  css: [
    '@/assets/styles/styles.scss',
    'vue-loading-overlay/dist/vue-loading.css',
    'vue-plyr/dist/vue-plyr.css',
    'prismjs/themes/prism.css',
    'vue-prism-editor/dist/prismeditor.min.css',
    'vue-multiselect/dist/vue-multiselect.min.css',
  ],

  bootstrapVue: {
    bootstrapCSS: false,
    bootstrapVueCSS: false,
  },

  /*
   ** Plugins to load before mounting the App
   */
  plugins: [
    '~/plugins/api-accessor-plugin.ts',
    '~/plugins/tooltip.js',
    '~/plugins/env.ts',
    '~/plugins/vue-loading-overlay.js',
    { src: '~/plugins/vue-plyr', mode: 'client' },
  ],
  /*
   ** Nuxt.js dev-modules
   */
  buildModules: [
    '@nuxt/typescript-build',
    // Doc: https://github.com/nuxt-community/eslint-module
    '@nuxtjs/eslint-module',
    '@nuxtjs/stylelint-module',
    // Doc: https://github.com/nuxt-community/analytics-module
    [
      '@nuxtjs/google-analytics',
      {
        id: process.env.GOOGLE_ANALYTICS_ID,
        debug: {
          enabled: process.env.GOOGLE_ANALYTICS_DEBUG || false,
          sendHitTask: process.env.GOOGLE_ANALYTICS_DEBUG || false,
        },
      },
    ],
  ],
  /*
   ** Nuxt.js modules
   */
  modules: [
    // Doc: https://bootstrap-vue.js.org/docs/
    'bootstrap-vue/nuxt',
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/axios',
    'nuxt-i18n',
    ['vue-scrollto/nuxt', { duration: 500 }],
  ],

  i18n: {
    locales: [
      {
        code: 'en',
        file: 'en-US.js',
      },
    ],
    defaultLocale: 'en',
    lazy: true,
    langDir: 'assets/locales/',
    detectBrowserLanguage: {
      useCookie: true,
      cookieKey: 'i18n_redirected',
    },
    strategy: 'prefix_except_default',
    vuex: false,
  },

  /*
   * Needed to access it inside templates to generate static links
   */
  env: {
    API_LOCATION: process.env.API_BASE_URL || 'http://localhost:9030',
    RECAPTCHA_PUBLIC_KEY: process.env.RECAPTCHA_PUBLIC_KEY || '',
  },

  /*
   ** Axios module configuration
   ** See https://axios.nuxtjs.org/options
   */
  axios: {
    baseURL: process.env.API_BASE_URL || 'http://localhost:9030',
  },

  /*
   ** Build configuration
   */
  build: {
    /*
     ** You can extend webpack config here
     */
    extend(config, ctx) {
      config.module.rules.push({
        enforce: 'pre',
        test: /\.md$/,
        loader: 'raw-loader',
        exclude: /(node_modules)/,
      });
    },
  },
};
