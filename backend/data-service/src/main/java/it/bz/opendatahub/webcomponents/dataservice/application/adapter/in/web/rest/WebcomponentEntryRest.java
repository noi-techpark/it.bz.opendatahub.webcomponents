package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest;

import it.bz.opendatahub.webcomponents.common.data.struct.Author;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WebcomponentEntryRest {
    private String uuid;

    private String shortName;

    private String title;

    private String descriptionAbstract;

    private String licenseString;

    private SpdxLicenseRest license;

    private String repositoryUrl;

    private List<Author> copyrightHolders;

    private String image;

    private List<Author> authors;

    private List<String> searchTags;

    private WebcomponentVersionRest currentVersion;
}
