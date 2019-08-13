package it.bz.opendatahub.webcomponents.crawlerservice.data.model;

import it.bz.opendatahub.webcomponents.common.data.Model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "origin")
public class OriginModel implements Model {
    @Id
    private String uuid;

    private String url;

    private String api;
}
