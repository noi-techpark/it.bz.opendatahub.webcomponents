package it.bz.opendatahub.webcomponents.dataservice.data.dto;

import it.bz.opendatahub.webcomponents.common.data.struct.Author;
import it.bz.opendatahub.webcomponents.common.data.struct.Configuration;
import it.bz.opendatahub.webcomponents.dataservice.data.Dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WebcomponentDto implements Dto {
    private String uuid;

    private String title;

    private String description;

    private String descriptionAbstract;

    private String license;

    private String repositoryUrl;

    private String image;

    private List<Author> authors;

    private List<String> searchTags;

    private List<String> dist;

    private Configuration configuration;
}
