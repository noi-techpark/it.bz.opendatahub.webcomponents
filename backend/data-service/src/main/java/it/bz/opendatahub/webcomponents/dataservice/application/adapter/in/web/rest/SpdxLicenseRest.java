package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpdxLicenseRest {
    private String reference;

    private Boolean isDeprecatedLicenseId;

    private String detailsUrl;

    private String referenceNumber;

    private String name;

    private String licenseId;

    private List<String> seeAlso;

    private Boolean isOsiApproved;
}
