package it.bz.opendatahub.webcomponents.crawlerservice.data.api;

import it.bz.opendatahub.webcomponents.common.data.struct.Author;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Manifest {
    private String title;

    private String description;

    private String descriptionAbstract;

    private String image;

    private String license;

    private List<Author> authors = new ArrayList<>();

    private List<String> searchTags = new ArrayList<>();

    private List<String> dist = new ArrayList<>();

    private Object configuration; // TODO: implement configuration
}
