package it.bz.opendatahub.webcomponents.common.data.model;

import it.bz.opendatahub.webcomponents.common.data.model.id.WebcomponentVersionId;
import it.bz.opendatahub.webcomponents.common.data.struct.Configuration;
import it.bz.opendatahub.webcomponents.common.data.struct.Dist;
import it.bz.opendatahub.webcomponents.common.hibernate.usertype.impl.ConfigurationUserType;
import it.bz.opendatahub.webcomponents.common.hibernate.usertype.impl.DistUserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@TypeDef(name = ConfigurationUserType.NAME, typeClass = ConfigurationUserType.class)
@TypeDef(name = DistUserType.NAME, typeClass = DistUserType.class)

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

    @Type(type = DistUserType.NAME)
    private Dist dist;

    @Type(type = ConfigurationUserType.NAME)
    private Configuration configuration;

    private Boolean deleted;

    private Integer distSizeTotalKb;

	private String lighthouseMetricsMobileData;

	private LocalDateTime lighthouseMetricsMobileDatetime;

	private Integer lighthouseMobilePerformanceRating;

	private String lighthouseMetricsDesktopData;

	private LocalDateTime lighthouseMetricsDesktopDatetime;

	private Integer lighthouseDesktopPerformanceRating;

	private Boolean lighthouseUpdateRequired;
}
