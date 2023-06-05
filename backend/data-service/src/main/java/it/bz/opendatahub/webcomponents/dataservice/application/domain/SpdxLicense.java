// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpdxLicense {
    private String reference;

    private Boolean isDeprecatedLicenseId;

    private String detailsUrl;

    private String referenceNumber;

    private String name;

    private String licenseId;

    private List<String> seeAlso;

    private Boolean isOsiApproved;
}
