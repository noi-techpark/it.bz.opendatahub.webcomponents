package it.bz.opendatahub.webcomponents.crawlerservice.repository.impl;

import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.github.Commit;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.github.File;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.github.Ref;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.github.Tag;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.CommitEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRemote;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.TagEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.CrawlerException;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.MalformedURLException;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.NotFoundException;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.VcsException;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public List<TagEntry> listVersionTags(GitRemote gitRemote) {
        RepositoryMetadata metadata = extractRepositoryMetadata(gitRemote);

        ResponseEntity<List<Tag>> res = restTemplate.exchange(BASE_URI + "/repos/" + metadata.getOwnerName() + "/" + metadata.getRepositoryName() + "/tags", HttpMethod.GET, null, new ParameterizedTypeReference<List<Tag>>() {});

        if(res.hasBody()) {
            List<TagEntry> result = new ArrayList<>();
            for(Tag tag : res.getBody()) {
                TagEntry newEntry = new TagEntry();
                newEntry.setName(tag.getName());
                newEntry.setRevisionHash(tag.getCommit().getSha());

                result.add(newEntry);
            }
            return result;
        }

        return Collections.emptyList();
    }

    @Override
    public String getLatestRevisionHash(GitRemote gitRemote) {
        return getLatestRevisionHash(gitRemote, "master");
    }

    @Override
    public String getLatestRevisionHash(GitRemote gitRemote, String branch) {
        RepositoryMetadata metadata = extractRepositoryMetadata(gitRemote);

        try {
            ResponseEntity<Ref> res = restTemplate.exchange(BASE_URI + "/repos/" + metadata.getOwnerName() + "/" + metadata.getRepositoryName() + "/git/refs/heads/" + branch, HttpMethod.GET, null, Ref.class);

            if(res.hasBody()) {
                Ref ref = res.getBody();

                if(ref != null) {
                    return ref.getObject().getSha();
                }
            }
        }
        catch (HttpStatusCodeException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new NotFoundException(e);
            }

            throw new VcsException(e);
        }

        throw new CrawlerException();
    }

    @Override
    public ByteArrayOutputStream getFileContents(GitRemote gitRemote, String remotePathToFile) {
        return getFileContents(gitRemote, null, remotePathToFile);
    }

    @Override
    public ByteArrayOutputStream getFileContents(GitRemote gitRemote, String revisionHash, String remotePathToFile) {
        RepositoryMetadata metadata = extractRepositoryMetadata(gitRemote);

        String qs = "";
        if(revisionHash != null && !revisionHash.isEmpty()) {
            qs = "?ref="+revisionHash;
        }

        try {
            ResponseEntity<File> res = restTemplate.exchange(BASE_URI + "/repos/" + metadata.getOwnerName() + "/" + metadata.getRepositoryName() + "/contents/" + remotePathToFile + qs, HttpMethod.GET, null, File.class);

            if (res.hasBody()) {
                File file = res.getBody();

                if (file != null) {
                    byte[] data = Base64.getDecoder().decode(file.getContent().replace("\n", ""));

                    ByteArrayOutputStream result = new ByteArrayOutputStream();
                    result.write(data);

                    return result;
                }
            }
        }
        catch (HttpStatusCodeException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new NotFoundException(e);
            }

            throw new VcsException(e);
        }
        catch (IOException e) {
            throw new CrawlerException(e);
        }

        throw new CrawlerException();
    }

    @Override
    public CommitEntry getMetadataForCommit(GitRemote gitRemote, String ref) {
        RepositoryMetadata metadata = extractRepositoryMetadata(gitRemote);

        try {
            ResponseEntity<Commit> res = restTemplate.exchange(BASE_URI + "/repos/" + metadata.getOwnerName() + "/" + metadata.getRepositoryName() + "/commits/" + ref, HttpMethod.GET, null, Commit.class);

            if (res.hasBody()) {
                Commit commit = res.getBody();

                if (commit != null) {
                    CommitEntry commitEntry = new CommitEntry();
                    commitEntry.setSha(commit.getSha());
                    commitEntry.setDate(commit.getCommit().getCommitter().getDate());

                    return commitEntry;
                }
            }
        }
        catch (HttpStatusCodeException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new NotFoundException(e);
            }

            throw new VcsException(e);
        }

        throw new CrawlerException();
    }

    private RepositoryMetadata extractRepositoryMetadata(GitRemote gitRemote) {
        RepositoryMetadata metadata = new RepositoryMetadata();

        metadata.setUrl(gitRemote.getUrl());

        Pattern pattern = Pattern.compile("github.com\\/([^\\/]*)\\/([^\\.]*)\\.git");
        Matcher matcher = pattern.matcher(gitRemote.getUrl());

        if(matcher.find()) {
            metadata.setOwnerName(matcher.group(1));
            metadata.setRepositoryName(matcher.group(2));
        }
        else {
            throw new MalformedURLException(gitRemote.getUrl());
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
