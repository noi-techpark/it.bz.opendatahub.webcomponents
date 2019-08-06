package it.bz.opendatahub.webcomponents.crawlerservice.data.struct;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OriginSystemEntry {
    private String head;

    private Date lastCheck;
}
