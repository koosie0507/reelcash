/*
 * Copyright (C) 2009, 2010 M. Homs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sorm.dao.mapper;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sorm.exception.SormException;

/**
 * requests an execution of a SQL Query to the JDBC driver, mapping the
 * resulting rows onto the entity object, or an array of entity objects.
 * <p>
 * The column/property mapping follows the CoC principle: the column names will
 * be converted to a lower-camel-case format (e.g. the column "ID_DOG" will be
 * mapped into the "idDog" property).
 * <p>
 * Properties and his mutators (setter methods) will be found using a reflect
 * introspections, storing these results in a cache, in order to increase the
 * performance of mapping multiple rows.
 *
 * @see org.sorm.dao.DaoFactory
 * @author mhoms
 */
public class ResultMapper {

    private static final char SQL_SEPARATOR = '_';
    private final Class<?> entityClass;
    private final Map<String, Method> methodsCache;

    /**
     * @param entityClass type of the DTO entity where the query results will be
     *            mapped
     */
    public ResultMapper(final Class<?> entityClass) {
        super();
        this.entityClass = entityClass;
        this.methodsCache = new HashMap<String, Method>();
    }

    /**
     * executes a JDBC query and maps the first row into the specified entity.
     *
     * @param con an active database connection
     * @param query SQL query that the JDBC driver manager will execute
     * @return a single entity instance
     * @throws SormException if query produces no results
     * @throws SQLException if a JDBC error ocurred
     *
     */
    public Object queryForEntity(final Connection con, final String query) throws SQLException {

        final Statement st = con.createStatement();
        final ResultSet rs = st.executeQuery(query);

        try {
            if (!rs.next()) {
                throw new SormException("query produces no results: " + query);
            }
            return mapRow(rs);
        } finally {
            rs.close();
            st.close();
        }
    }

    /**
     * executes a JDBC query and maps all resulting rows into an array of
     * entities.
     *
     * @param con an active database connection
     * @param query SQL query that the JDBC driver manager will execute
     * @return an array of entities
     * @throws SQLException if a JDBC error ocurred
     */
    public Object[] queryForEntities(final Connection con, final String query) throws SQLException {

        final Statement st = con.createStatement();
        final ResultSet rs = st.executeQuery(query);

        try {
            final List<Object> list = new ArrayList<Object>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }

            final Object[] result = (Object[]) Array.newInstance(entityClass, list.size());
            for (int i = 0; i < list.size(); i++) {
                result[i] = list.get(i);
            }

            return result;
        } finally {
            rs.close();
            st.close();
        }
    }

    private Object mapRow(final ResultSet rs) throws SQLException {

        Object entity;
        try {
            if (entityClass.isPrimitive()) {
                entity = Array.get(Array.newInstance(entityClass, 1), 0);
            } else {
                entity = entityClass.newInstance();
            }
        } catch (final Exception exc) {
            throw new SormException("error instantiating " + entityClass.getName()
                    + ", has a public default constructor?", exc);
        }

        final ResultSetMetaData meta = rs.getMetaData();
        final int numColumns = meta.getColumnCount();

        for (int i = 1; i <= numColumns; i++) {
            final String colName = meta.getColumnName(i);
            final Object colValue = rs.getObject(i);
            set(entity, camelizeLow(colName), colValue);
        }

        return entity;
    }

    private void set(final Object entity, final String propertyName, final Object value) {

        Method setterMethod = methodsCache.get(propertyName);

        if (setterMethod == null) {
            final Method[] methods = entity.getClass().getMethods();
            final String methodName = setterNameOf(propertyName);
            for (final Method m : methods) {
                if (m.getName().equals(methodName)) {
                    setterMethod = m;
                    break;
                }
            }
            if (setterMethod == null) {
                throw new SormException("mapping error; setter method not found for the property: "
                        + propertyName + " [" + entity.getClass().getName() + "."
                        + setterNameOf(propertyName) + "(" + value.getClass() + ")]");
            }
            methodsCache.put(propertyName, setterMethod);
        }

        try {
            setterMethod.invoke(entity, value);
        } catch (final Exception exc) {
            throw new SormException("error invoking setter method: " + setterMethod.toString()
                    + " with argument: " + value.getClass().getName(), exc);
        }
    }

    private String setterNameOf(final String propertyName) {
        return "set" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
    }

    /**
     * @param columnName "<tt>BENDITO_SEAS_HIJO_MIO</tt>"
     * @return "<tt>benditoSeasHijoMio</tt>"
     */
    public static String camelizeLow(final String columnName) {

        final StringBuilder strb = new StringBuilder(columnName.length());

        strb.append(Character.toLowerCase(columnName.charAt(0)));
        int length = columnName.length();
        int beginIdx = 1;
        int endIdx = -1;
        while (true) {
            if (0 < endIdx && beginIdx > 1 && beginIdx < length) {
                strb.append(Character.toUpperCase(columnName.charAt(beginIdx++)));
            }
            endIdx = columnName.indexOf(SQL_SEPARATOR, beginIdx);
            String appended = (0 > endIdx)
                    ? columnName.substring(beginIdx)
                    : columnName.substring(beginIdx, endIdx);

            strb.append(appended.toLowerCase());
            if (0 > endIdx) {
                break;
            }
            beginIdx = endIdx + 1;
        }

        return strb.toString();
    }
}
