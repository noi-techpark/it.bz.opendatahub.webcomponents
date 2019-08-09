package it.bz.opendatahub.webcomponents.crawlerservice.data.api;

import it.bz.opendatahub.webcomponents.common.data.struct.Author;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Manifest {
    private String title;

    private String description;

    private String descriptionAbstract;

    private String image;

    private String license;

    private List<Author> authors;

    private List<String> searchTags;

    private List<String> dist;

    private Object configuration; // TODO: implement configuration
}
