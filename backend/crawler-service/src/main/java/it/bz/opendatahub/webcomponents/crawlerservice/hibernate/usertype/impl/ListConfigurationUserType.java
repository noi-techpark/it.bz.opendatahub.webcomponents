package it.bz.opendatahub.webcomponents.crawlerservice.hibernate.usertype.impl;

import it.bz.opendatahub.webcomponents.common.hibernate.usertype.AbstractUserType;
import it.bz.opendatahub.webcomponents.common.hibernate.usertype.ListUserType;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.Manifest;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public final class ListConfigurationUserType extends ListUserType {
    public static final String NAME = "ListConfigurationUserType";

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        final String s = rs.getString(names[0]);

        if(s == null) {
            return null;
        }

        try {
            return Arrays.asList(objectMapper.readValue(s, Manifest.Configuration[].class));
        }
        catch (IOException e) {
            throw new HibernateException(e);
        }
    }
}
