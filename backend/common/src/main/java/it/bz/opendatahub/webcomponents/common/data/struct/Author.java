package it.bz.opendatahub.webcomponents.common.data.struct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author {
    private String name;

    private String email;

    private String organization;

    private String organizationUrl;
}
