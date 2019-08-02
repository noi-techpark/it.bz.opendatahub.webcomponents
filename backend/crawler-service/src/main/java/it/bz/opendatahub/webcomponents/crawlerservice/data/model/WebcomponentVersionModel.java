package it.bz.opendatahub.webcomponents.crawlerservice.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "webcomponent_version")
public class WebcomponentVersionModel {
    @Id
    private String webcomponentUuid;

    private String versionTag;

    private Date releaseTimestamp;
}
