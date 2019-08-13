package it.bz.opendatahub.webcomponents.common.hibernate.usertype;

import java.util.List;

public abstract class ListUserType extends AbstractUserType {
    @Override
    public Class returnedClass() {
        return List.class;
    }
}
