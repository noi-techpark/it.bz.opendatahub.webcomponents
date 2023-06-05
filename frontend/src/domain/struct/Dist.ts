// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

import { DistSource } from './DistSource';

export interface Dist {
  basePath: string;

  files: Array<string>;
  scripts: Array<DistSource>;
}
