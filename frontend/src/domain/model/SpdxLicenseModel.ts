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
