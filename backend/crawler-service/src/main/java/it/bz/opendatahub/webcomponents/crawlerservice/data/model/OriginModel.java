package it.bz.opendatahub.webcomponents.crawlerservice.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "origin")
public class OriginModel {
    @Id
    private String uuid;

    private String url;

    private String api;

    private Boolean deleted;
}
