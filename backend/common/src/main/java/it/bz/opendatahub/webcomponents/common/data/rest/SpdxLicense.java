package it.bz.opendatahub.webcomponents.common.data.rest;

import it.bz.opendatahub.webcomponents.common.data.Rest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpdxLicense implements Rest {
    private String reference;

    private Boolean isDeprecatedLicenseId;

    private String detailsUrl;

    private String referenceNumber;

    private String name;

    private String licenseId;

    private List<String> seeAlso;

    private Boolean isOsiApproved;
}
