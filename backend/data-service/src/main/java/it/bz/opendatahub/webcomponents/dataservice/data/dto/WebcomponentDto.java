package it.bz.opendatahub.webcomponents.dataservice.data.dto;

import it.bz.opendatahub.webcomponents.common.data.struct.Author;
import it.bz.opendatahub.webcomponents.common.hibernate.usertype.impl.ListAuthorUserType;
import it.bz.opendatahub.webcomponents.common.hibernate.usertype.impl.ListStringUserType;
import it.bz.opendatahub.webcomponents.dataservice.data.Dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WebcomponentDto implements Dto {
    private String uuid;

    private String title;

    private String description;

    private String descriptionAbstract;

    private String license;

    private List<Author> authors;

    private List<String> searchTags;
}
