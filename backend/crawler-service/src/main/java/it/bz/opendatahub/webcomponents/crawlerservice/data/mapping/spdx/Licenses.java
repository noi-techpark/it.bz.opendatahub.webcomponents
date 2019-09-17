package it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.spdx;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Licenses {
    private String licenseListVersion;

    private List<License> licenses;

    @Getter
    @Setter
    public static class License {
        private String reference;

        private Boolean isDeprecatedLicenseId;

        private String detailsUrl;

        private String referenceNumber;

        private String name;

        private String licenseId;

        private List<String> seeAlso;

        private Boolean isOsiApproved;
    }
}
