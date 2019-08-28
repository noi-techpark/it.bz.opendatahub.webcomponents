package it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.github;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Commit {
    private String url;

    private String sha;

    private String node_id;

    private String html_url;

    private String comments_url;

    private CommitData commit;

    @Getter
    @Setter
    public static class CommitData {
        private String url;

        private PersonEntry author;

        private PersonEntry committer;

        private String message;

        private Tree tree;
    }

    @Getter
    @Setter
    public static class PersonEntry {
        private String name;

        private String email;

        private Date date;
    }

    @Getter
    @Setter
    public static class Tree {
        private String url;

        private String sha;
    }
}
