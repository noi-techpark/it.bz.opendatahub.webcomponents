package it.bz.opendatahub.webcomponents.crawlerservice.data.api.github;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class File {
    private String type;

    private String encoding;

    private Long size;

    private String name;

    private String path;

    private String content;

    private String sha;

    private String url;

    private String git_url;

    private String html_url;

    private String download_url;
}
