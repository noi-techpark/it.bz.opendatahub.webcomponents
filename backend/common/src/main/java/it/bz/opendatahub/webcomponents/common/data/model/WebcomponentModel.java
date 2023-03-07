package it.bz.opendatahub.webcomponents.common.data.model;

import it.bz.opendatahub.webcomponents.common.data.struct.Author;
import it.bz.opendatahub.webcomponents.common.hibernate.usertype.impl.ListAuthorUserType;
import it.bz.opendatahub.webcomponents.common.hibernate.usertype.impl.ListStringUserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@TypeDef(name = ListAuthorUserType.NAME, typeClass = ListAuthorUserType.class)
@TypeDef(name = ListStringUserType.NAME, typeClass = ListStringUserType.class)

@Getter
@Setter
@Entity
@Table(name = "webcomponent")
public class WebcomponentModel {
    @Id
    private String uuid;

    private String shortName;

    private String title;

    private String description;

    private String descriptionAbstract;

    private String repositoryUrl;

    @Type(type = ListAuthorUserType.NAME)
    private List<Author> copyrightHolders;

    private String image;

    private String license;

    @Type(type = ListAuthorUserType.NAME)
    private List<Author> authors;

    @Type(type = ListStringUserType.NAME)
    private List<String> searchTags;

    private Boolean deleted;
}
