import { API } from '~/plugins/api';

declare module 'vue/types/vue' {
  interface Vue {
    $api: API;
  }
}
