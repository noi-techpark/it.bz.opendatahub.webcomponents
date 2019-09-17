package it.bz.opendatahub.webcomponents.crawlerservice.data.struct;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommitEntry {
    public static CommitEntry of(String sha,
                                 Date date,
                                 String treeSha) {

        CommitEntry entry = new CommitEntry();

        entry.setSha(sha);
        entry.setDate(date);
        entry.setTreeSha(treeSha);

        return entry;
    }

    private String sha;

    private Date date;

    private String treeSha;
}
