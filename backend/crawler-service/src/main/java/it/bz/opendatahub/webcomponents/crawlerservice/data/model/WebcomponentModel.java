package it.bz.opendatahub.webcomponents.crawlerservice.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "webcomponent")
public class WebcomponentModel {
    @Id
    private String uuid;

    private String title;

    private String description;

    private String descriptionAbstract;

    private String license;

    //private List<Author> authors;

    //private List<String> searchTags;

    //private Object configuration;
}
