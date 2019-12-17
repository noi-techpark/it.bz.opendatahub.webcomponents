package it.bz.opendatahub.webcomponents.crawlerservice.data.struct;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TagEntry {
    private String name;

    private String revisionHash;

    private Date revisionDate;
}
