package it.bz.opendatahub.webcomponents.crawlerservice.data.mapping;

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

    private String repositoryUrl;

    private String image;

    private String license;

    private List<Author> authors = new ArrayList<>();

    private List<String> searchTags = new ArrayList<>();

    private List<String> dist = new ArrayList<>();

    private Configuration configuration;

    @Getter
    @Setter
    public static class Configuration {

        private String tagName;

        private List<Options> options;
    }

    @Getter
    @Setter
    public static class Options {
        private String key;

        private String type;

        private Object options;
    }
}
