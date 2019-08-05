package it.bz.opendatahub.webcomponents.dataservice.data.model;

import it.bz.opendatahub.webcomponents.common.data.struct.Author;
import it.bz.opendatahub.webcomponents.common.hibernate.usertype.impl.ListAuthorUserType;
import it.bz.opendatahub.webcomponents.common.hibernate.usertype.impl.ListStringUserType;
import it.bz.opendatahub.webcomponents.dataservice.data.Model;
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
public class WebcomponentModel implements Model {
    @Id
    private String uuid;

    private String title;

    private String description;

    private String descriptionAbstract;

    private String license;

    @Type(type = ListAuthorUserType.NAME)
    private List<Author> authors;

    @Type(type = ListStringUserType.NAME)
    private List<String> searchTags;

    //private Object configuration;
}
