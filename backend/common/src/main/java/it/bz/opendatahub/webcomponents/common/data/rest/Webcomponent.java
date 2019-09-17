package it.bz.opendatahub.webcomponents.common.data.rest;

import it.bz.opendatahub.webcomponents.common.data.Rest;
import it.bz.opendatahub.webcomponents.common.data.struct.Author;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Webcomponent implements Rest {
    private String uuid;

    private String title;

    private String description;

    private String descriptionAbstract;

    private SpdxLicense license;

    private String repositoryUrl;

    private String image;

    private List<Author> authors;

    private List<String> searchTags;

    private List<WebcomponentVersion> versions;

    private Date datePublished;

    private Date dateUpdated;
}
