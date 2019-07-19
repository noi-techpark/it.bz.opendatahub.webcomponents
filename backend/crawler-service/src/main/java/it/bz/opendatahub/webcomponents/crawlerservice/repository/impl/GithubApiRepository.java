package it.bz.opendatahub.webcomponents.crawlerservice.repository.impl;

import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class GithubApiRepository implements VcsApiRepository {
    private static final String BASE_URI = "https://api.github.com";

    private RestTemplate restTemplate;

    @Autowired
    public GithubApiRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<TagEntry> listVersions(String repoUrl) {
        RepositoryMetadata metadata = getMetadataFromRepositoryUrl(repoUrl);

        ResponseEntity<List<Tag>> res = restTemplate.exchange(BASE_URI + "/repos/" + metadata.getOwnerName() + "/" + metadata.getRepositoryName() + "/tags", HttpMethod.GET, null, new ParameterizedTypeReference<List<Tag>>() {});

        if(res.hasBody()) {
            List<TagEntry> result = new ArrayList<>();
            for(Tag tag : res.getBody()) {
                result.add(new TagEntry(tag.getName(), tag.getCommit().getSha()));
            }
            return result;
        }

        return Collections.emptyList();
    }

    @Override
    public String getLatestCommitHash(String repoUrl, String branch) {
        RepositoryMetadata metadata = getMetadataFromRepositoryUrl(repoUrl);

        ResponseEntity<Ref> res = restTemplate.exchange(BASE_URI + "/repos/" + metadata.getOwnerName() + "/" + metadata.getRepositoryName() + "/git/refs/heads/" + branch, HttpMethod.GET, null, Ref.class);

        if(res.hasBody()) {
            Ref ref = res.getBody();

            if(ref != null) {
                return ref.getObject().getSha();
            }
        }

        return null;//Collections.emptyList();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TagEntry {
        private String name;

        private String commitSha;
    }

    @Getter
    @Setter
    public static class Tag {
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

    @Getter
    @Setter
    public static class Ref {
        private String ref;

        private String node_id;

        private String url;

        private RefObject object = new RefObject();

        @Getter
        @Setter
        public static class RefObject {
            private String type;

            private String sha;

            private String url;
        }
    }

    private RepositoryMetadata getMetadataFromRepositoryUrl(String repoUrl) {
        RepositoryMetadata metadata = new RepositoryMetadata();

        metadata.setUrl(repoUrl);

        Pattern pattern = Pattern.compile("github.com\\/([^\\/]*)\\/([^\\.]*)\\.git");
        Matcher matcher = pattern.matcher(repoUrl);

        if(matcher.find()) {
            metadata.setOwnerName(matcher.group(1));
            metadata.setRepositoryName(matcher.group(2));
        }
        else {
            throw new RuntimeException();
        }

        return metadata;
    }

    @Getter
    @Setter
    private static class RepositoryMetadata {
        private String url;

        private String ownerName;

        private String repositoryName;
    }
}
