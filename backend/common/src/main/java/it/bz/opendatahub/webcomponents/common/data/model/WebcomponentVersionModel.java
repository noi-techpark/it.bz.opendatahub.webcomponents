package it.bz.opendatahub.webcomponents.common.data.model;

import it.bz.opendatahub.webcomponents.common.data.model.id.WebcomponentVersionId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(WebcomponentVersionId.class)
@Table(name = "webcomponent_version")
public class WebcomponentVersionModel {
    @Id
    private String webcomponentUuid;

    @Id
    private String versionTag;

    private Date releaseTimestamp;
}
