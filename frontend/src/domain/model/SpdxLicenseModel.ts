// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

export interface SpdxLicenseModel {
  reference: string;

  isDeprecatedLicenseId: boolean;

  detailsUrl: string;

  referenceNumber: string;

  name: string;

  licenseId: string;

  seeAlso: Array<string>;

  isOsiApproved: boolean;
}
