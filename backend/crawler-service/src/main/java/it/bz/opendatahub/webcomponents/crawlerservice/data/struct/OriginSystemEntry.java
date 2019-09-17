package it.bz.opendatahub.webcomponents.crawlerservice.data.struct;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OriginSystemEntry {
    public static OriginSystemEntry of(String commitHash, Date date) {
        OriginSystemEntry entry = new OriginSystemEntry();

        entry.setHead(commitHash);
        entry.setLastCheck(date);

        return entry;
    }

    private String head;

    private Date lastCheck;
}
