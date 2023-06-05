// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

import { Plugin } from '@nuxt/types';
import { initializeApi } from '../utils/api-accessor';
import api from './api';

const accessor: Plugin = (ctx, inject) => {
  const instance = api(ctx);

  inject('api', instance);

  initializeApi(instance);
};

export default accessor;
