// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

import { Dist } from '../struct/Dist';
import { Configuration } from '../struct/Configuration';

export interface WebcomponentConfigurationModel {
  webcomponentUuid: string;

  deliveryBaseUrl: string;

  dist: Dist;

  configuration: Configuration;

  scriptSources: Array<string>;
}
