// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.common.hibernate.usertype;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * Common code of most UserTypes.
 */
public abstract class AbstractUserType implements UserType, ParameterizedType {
    protected static final ObjectMapper objectMapper = new ObjectMapper();
    protected static final TypeFactory typeFactory = objectMapper.getTypeFactory();

    private final JavaType returnType;

    protected AbstractUserType() {
        returnType = typeFactory.constructType(returnedClass());
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        final String s = resultSet.getString(strings[0]);

        if (s == null) {
            return null;
        }

        try {
            return objectMapper.readValue(s, returnType);
        }
        catch (IOException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        try {
            PGobject pgo = new PGobject();
            pgo.setType("json");
            pgo.setValue(objectMapper.writeValueAsString(o));

            preparedStatement.setObject(i, pgo);
        }
        catch (IOException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public int[] sqlTypes() {
        return new int[] { Types.VARCHAR };
    }

    @Override
    public boolean equals(Object o, Object o1) {
        if(o == null || o1 == null) {
            return false;
        }
        else {
            return o.equals(o1);
        }
    }

    @Override
    public int hashCode(Object o) {
        if(o == null) {
            throw new HibernateException("hashCode() => null");
        }
        return o.hashCode();
    }

    @Override
    public Object deepCopy(Object o) {
        return o;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) {
        return serializable;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) {
        return o;
    }

    @Override
    public void setParameterValues(Properties properties) {

    }
}
