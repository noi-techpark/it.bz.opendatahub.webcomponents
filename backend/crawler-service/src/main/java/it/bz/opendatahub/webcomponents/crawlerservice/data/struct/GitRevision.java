package it.bz.opendatahub.webcomponents.crawlerservice.data.struct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitRevision {
    public static GitRevision of(GitRemote gitRemote, TagEntry tagEntry) {
        GitRevision gitRevision = new GitRevision();

        gitRevision.setGitRemote(gitRemote);
        gitRevision.setTagEntry(tagEntry);

        return gitRevision;
    }

    private GitRemote gitRemote;

    private TagEntry tagEntry;
}
