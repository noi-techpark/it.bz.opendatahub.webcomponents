// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

import { API } from '~/plugins/api';

declare module 'vue/types/vue' {
  interface Vue {
    $api: API;
  }
}
