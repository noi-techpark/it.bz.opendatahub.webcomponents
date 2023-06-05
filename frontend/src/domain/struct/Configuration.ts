// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

export interface Options {
  key: string;
  type: string;
  required: boolean;
  label: string;
  options: any;
}

export interface Configuration {
  tagName: string;
  options: Array<Options>;
}
