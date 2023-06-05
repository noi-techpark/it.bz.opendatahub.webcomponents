// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

import { Author } from '../struct/Author';
import { SpdxLicenseModel } from './SpdxLicenseModel';
import { WebcomponentVersionModel } from './WebcomponentVersionModel';

export interface WebcomponentModel {
  uuid: string;

  shortName: string;

  title: string;

  description: string;

  descriptionAbstract: string;

  licenseString: string;

  license: SpdxLicenseModel;

  repositoryUrl: string;

  copyrightHolders: Array<Author>;

  image: string;

  authors: Array<Author>;

  searchTags: Array<string>;

  versions: Array<WebcomponentVersionModel>;

  datePublished: string;

  dateUpdated: string;
}
