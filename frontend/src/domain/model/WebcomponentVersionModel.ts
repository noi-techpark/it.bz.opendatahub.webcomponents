// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

export interface WebcomponentVersionModel {
  versionTag: string;

  releaseTimestamp: string;

  distSizeTotalKb: number;

  lighthouseMobilePerformanceRating: number;

  lighthouseDesktopPerformanceRating: number;

  readMe: string;

  licenseAgreement: string;
}
