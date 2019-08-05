package it.bz.opendatahub.webcomponents.crawlerservice.hibernate.usertype.impl;

import it.bz.opendatahub.webcomponents.common.hibernate.usertype.AbstractUserType;
import it.bz.opendatahub.webcomponents.common.data.struct.Author;

public final class AuthorUserType extends AbstractUserType {
    public static final String NAME = "AuthorUserType";

    @Override
    public Class returnedClass() {
        return Author.class;
    }
}
