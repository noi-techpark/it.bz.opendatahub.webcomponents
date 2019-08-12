package it.bz.opendatahub.webcomponents.crawlerservice.repository.impl;

import it.bz.opendatahub.webcomponents.crawlerservice.data.api.github.File;
import it.bz.opendatahub.webcomponents.crawlerservice.data.api.github.Ref;
import it.bz.opendatahub.webcomponents.crawlerservice.data.api.github.Tag;
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
import java.util.Base64;
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

        return null;
    }

    @Override
    public byte[] getFileContentsForCommitHash(String repoUrl, String commitHash, String pathToFile) {
        RepositoryMetadata metadata = getMetadataFromRepositoryUrl(repoUrl);

        String qs = "";
        if(commitHash != null && !commitHash.isEmpty()) {
            qs = "?ref="+commitHash;
        }

        try {
            ResponseEntity<File> res = restTemplate.exchange(BASE_URI + "/repos/" + metadata.getOwnerName() + "/" + metadata.getRepositoryName() + "/contents/" + pathToFile + qs, HttpMethod.GET, null, File.class);

            if (res.hasBody()) {
                File file = res.getBody();

                if (file != null) {
                    return Base64.getDecoder().decode(file.getContent().replace("\n", ""));
                }
            }
        }
        catch (Exception e) {

        }

        return new byte[0];
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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TagEntry {
        private String name;

        private String commitSha;
    }
}
