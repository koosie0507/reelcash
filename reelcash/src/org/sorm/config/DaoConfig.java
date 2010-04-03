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
package org.sorm.config;

import org.sorm.dao.DaoFactory;

/**
 * This object is sintactic sugar for {@link DaoFactory} configurations, that
 * always create a fresh instance, that is, always working in a
 * <tt>scope="prototype"</tt> mode. Any other scope configuration will not takes
 * effect.
 * 
 * <p>
 * By implementing the {@link FactoryBean} interface, this objects becomes a
 * Spring bean factory, that can be defined directly in Spring configuration.
 * 
 * <p>
 * You can configure a DAO object in Spring context as follows:
 * 
 * <pre>
 * &lt;bean id="personDao" class="org.sorm.config.DaoConfig"&gt;
 *     &lt;constructor-arg value="org.sorm.example.dao.PersonDao" /&gt;
 * &lt;/bean&gt;
 * </pre>
 * <p>
 * , instead of
 * 
 * <pre>
 * &lt;bean id="personDao" class="org.sorm.dao.DaoFactory" factory-method="createInstance" scope="prototype"&gt;
 *     &lt;constructor-arg value="org.sorm.example.dao.PersonDao" type="java.lang.Class" /&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * <p>
 * Note that this factory needs a DAO class to be constructed.
 * 
 * @see DaoFactory
 * @author mhoms
 */
public class DaoConfig extends DaoFactory {

	protected Class<?> factoryType;

	public DaoConfig(final Class<?> factoryType) {
		super();
		this.factoryType = factoryType;
	}

	/**
	 * returns a new fresh proxy instace.
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	public Object getObject() {
		return super.createInstance(factoryType);
	}

	/**
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	public Class<?> getObjectType() {
		return factoryType;
	}

	/**
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	public boolean isSingleton() {
		return false;
	}

}
