// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

export interface BvSelectOption {
  value: boolean | number | string | null;
  text?: string;
  disabled?: boolean;
}

export type BvSelectOptionArray = Array<BvSelectOption>;
