package it.bz.opendatahub.webcomponents.common.hibernate.usertype;

import it.bz.opendatahub.webcomponents.common.hibernate.usertype.AbstractUserType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public abstract class ListUserType extends AbstractUserType {
    @Override
    public Class returnedClass() {
        return List.class;
    }
}
