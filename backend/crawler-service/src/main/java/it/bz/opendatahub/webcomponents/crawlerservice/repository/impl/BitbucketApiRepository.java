package it.bz.opendatahub.webcomponents.crawlerservice.repository.impl;

import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.CommitEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRemote;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.TagEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Repository
public class BitbucketApiRepository implements VcsApiRepository {
    @Override
    public List<TagEntry> listVersionTags(GitRemote gitRemote) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLatestRevisionHash(GitRemote gitRemote) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLatestRevisionHash(GitRemote gitRemote, String branchName) {
        throw new UnsupportedOperationException();
    }

    /*@Override
    public ByteArrayOutputStream getFileContents(GitRemote gitRemote, String remotePathToFile) {
        throw new UnsupportedOperationException();
    }*/

    @Override
    public ByteArrayOutputStream getFileContents(GitRemote gitRemote, String revisionHash, String remotePathToFile) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CommitEntry getMetadataForCommit(GitRemote gitRemote, String ref) {
        throw new UnsupportedOperationException();
    }
}
