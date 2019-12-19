package it.bz.opendatahub.webcomponents.common.data.rest;

import it.bz.opendatahub.webcomponents.common.data.Rest;
import it.bz.opendatahub.webcomponents.common.data.struct.Author;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WebcomponentEntry implements Rest {
    private String uuid;

    private String title;

    private String descriptionAbstract;

    private String licenseString;

    private SpdxLicense license;

    private String repositoryUrl;

    private List<Author> copyrightHolders;

    private String image;

    private List<Author> authors;

    private List<String> searchTags;

    private WebcomponentVersion currentVersion;
}
