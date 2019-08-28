package it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.github;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Blob {
    private String content;

    private String encoding;

    private String url;

    private String sha;

    private Integer size;
}
