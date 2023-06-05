// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

import { API } from '../plugins/api';

// eslint-disable-next-line import/no-mutable-exports
let $api: API;

export function initializeApi(apiInstance: API) {
  $api = apiInstance;
}

export { $api };
