package it.bz.opendatahub.webcomponents.crawlerservice.data.model;

import it.bz.opendatahub.webcomponents.crawlerservice.data.model.id.OriginTagId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "origin_tag")
@IdClass(OriginTagId.class)
public class OriginTagModel {
    @Id
    private String originUuid;

    @Id
    private String tagName;

    private String hash;

    private Boolean deleted;
}
