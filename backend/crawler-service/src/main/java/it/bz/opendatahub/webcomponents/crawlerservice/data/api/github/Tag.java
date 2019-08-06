package it.bz.opendatahub.webcomponents.crawlerservice.data.api.github;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tag {
    private String name;

    private String zipball_url;

    private String tarball_url;

    private String node_id;

    private Commit commit;

    @Getter
    @Setter
    public static class Commit {
        private String sha;

        private String url;
    }
}
