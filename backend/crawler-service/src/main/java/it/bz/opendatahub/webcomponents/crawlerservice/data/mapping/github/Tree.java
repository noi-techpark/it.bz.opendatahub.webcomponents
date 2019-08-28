package it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.github;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Tree {
    private String sha;

    private String url;

    private List<TreeEntry> tree = new ArrayList<>();

    @Getter
    @Setter
    public static class TreeEntry {
        private String path;

        private String mode;

        private String type;

        private String sha;

        private Integer size;

        private String url;
    }
}
