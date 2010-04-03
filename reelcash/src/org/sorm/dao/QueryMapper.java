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
package org.sorm.dao;

import org.sorm.dao.queries.QueryExecutor;

/**
 * This object is a really simple facade that offers a simple mechanism to
 * implement the DAO objects, accessing programmatically to the internals of the
 * query processor. This is the easiest way to send SQL queries to the JDBC
 * driver manager, mapping automatically the returning results.
 *
 * <pre>
 * public City loadById(final Integer idCity) {
 * 	return execute(&quot;SELECT ID_CITY,NAME FROM CITY WHERE ID_CITY=$0&quot;, City.class, idCity);
 * }
 *</pre>
 *
 * @author mhoms
 */
public class QueryMapper {

	private final static QueryExecutor executor = new QueryExecutor();

	@SuppressWarnings("unchecked")
	public static <T> T execute(final String query, final Class<T> returnType, final Object... args) {

		return (T) executor.invokeSimpleQuery(query, returnType, args);
	}

}
