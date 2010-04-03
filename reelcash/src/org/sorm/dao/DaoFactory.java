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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

import org.sorm.dao.mapper.ResultMapper;
import org.sorm.dao.queries.QueryExecutor;
import org.sorm.exception.SormException;

/**
 * This object is a DAO factory that proxies a {@link Query}-annotated
 * interfaces. It handles the SQL queries defined in {@link Query} annotations,
 * processing the contained value expressions, and sending it to the JDBC driver
 * manager, returning a mapped entity or an array of mapped entities.
 *
 * <p>
 * Spring configuration example:
 *
 * <pre>
 * &lt;bean id="personDao" class="org.sorm.dao.DaoFactory" factory-method="createInstance" scope="prototype"&gt;
 *     &lt;constructor-arg value="org.sorm.example.dao.PersonDao" type="java.lang.Class" /&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <p>
 * For a less verbose Spring configuration, see
 * {@link org.sorm.config.DaoConfig} class.
 *
 * <p>
 * SELECT sentence example:
 *
 * <pre>
 * &#064;Query(&quot;SELECT ID_DOG,NAME,AGE FROM DOG&quot;)
 * Dog[] loadAll();
 * </pre>
 *
 * UPDATE/INSERT/DELETE sentence example:
 *
 * <pre>
 * &#064;Query(&quot;UPDATE DOG SET NAME='$0.name', AGE=$0.age WHERE ID_DOG=$0.idDog&quot;)
 * int update(Dog dog);
 * </pre>
 *
 * <p>
 * Note that &#64;Query supports multiple SQL sentences, returning the result of
 * the last one:
 *
 * <pre>
 * &#064;Query( { &quot;INSERT INTO CITY (NAME) VALUES ('$0.name')&quot;, &quot;SELECT ID_CITY,NAME FROM CITY WHERE NAME='$0.name'&quot; })
 * City create(City city);
 * </pre>
 *
 * @see org.sorm.dao.Query
 * @see org.sorm.config.DaoConfig
 * @see ResultMapper
 * @author mhoms
 */
public class DaoFactory extends QueryExecutor implements InvocationHandler {

	private final static Logger LOGGER = Logger.getLogger(DaoFactory.class.getName());

	protected DaoFactory() {
		// non-visible constructor
		super();
	}

	/**
	 * creates a new proxy instance that follows the 'daoInterface' interface,
	 * and his behaviour configured by the {@link Query} annotations.
	 *
	 * @param daoInterface DAO interface to prox
	 * @return a new proxied instance
	 */
	public static Object createInstance(final Class<?> daoInterface) {

		if (!daoInterface.isInterface()) {
			throw new SormException(DaoFactory.class.getSimpleName()
					+ " only admits interfaces, not classes: " + daoInterface.getName());
		}

		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class<?>[] { daoInterface }, new DaoFactory());
	}

	/**
	 * <p>
	 * proxy method-interceptor.
	 *
	 * <ul>
	 * <li>gets the {@link Query} JDBC query, and replaces the found expressions
	 * for the properly values.
	 * <li>sends the processed query to the JDBC driver manager in order to
	 * execute.
	 * <li>maps the resulting row(s) to the entity or array of entities.
	 * </ul>
	 *
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(final Object proxy, final Method method, final Object[] args) {
		LOGGER.fine("execing DAO method: " + method.getName());

		// obté l'anotació @Query
		final Query annoQuery = method.getAnnotation(Query.class);
		if (annoQuery == null) {
			throw new SormException("invoking DAO method that is not annotated with @Query: "
					+ method.toString());
		}
		final String[] queries = annoQuery.value();

		// processa i executa cada query
		Object lastResult = null;
		for (final String query : queries) {
			try {
				lastResult = invokeSimpleQuery(query, method.getReturnType(), args);
			} catch (final Exception e) {
				throw new SormException("error in DAO proxy, with query: " + query, e);
			}
		}

		return lastResult;
	}

}
