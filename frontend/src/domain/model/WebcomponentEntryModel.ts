// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

import { Author } from '../struct/Author';
import { WebcomponentVersionModel } from './WebcomponentVersionModel';
import { SpdxLicenseModel } from './SpdxLicenseModel';

export interface WebcomponentEntryModel {
  uuid: string;

  shortName: string;

  title: string;

  descriptionAbstract: string;

  licenseString: string;

  license: SpdxLicenseModel;

  repositoryUrl: string;

  copyrightHolders: Array<Author>;

  image: string;

  authors: Array<Author>;

  searchTags: Array<string>;

  currentVersion: WebcomponentVersionModel;
}
