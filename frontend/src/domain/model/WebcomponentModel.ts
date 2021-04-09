import { Author } from '../struct/Author';
import { SpdxLicenseModel } from './SpdxLicenseModel';
import { WebcomponentVersionModel } from './WebcomponentVersionModel';

export interface WebcomponentModel {
  uuid: string;

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

  versions: WebcomponentVersionModel;

  datePublished: string;

  dateUpdated: string;
}
