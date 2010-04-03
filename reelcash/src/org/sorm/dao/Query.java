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

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * used to annotate DAO interface methods, and specify the JDBC mapping.
 * 
 * <p>
 * &#064;Query annotation allows you to quickly create the DAO objects, defining
 * for every method what SQL sentences have to execute. These SQL sentences must
 * contains special expressions witch will be replaced by the arguments for each
 * DAO method call.
 * 
 * <p>
 * SELECT sentences will return a single-entity or multiple-entities (mapped
 * into an entity, or an array of entities), and UPDATE/INSERT/DELETE sentences
 * will return an int value that represent the number of affected rows.
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
 * Note that @Query supports multiple SQL sentences, returning the result of the
 * last one:
 * 
 * <pre>
 * &#064;Query( { &quot;INSERT INTO CITY (NAME) VALUES ('$0.name')&quot;, &quot;SELECT ID_CITY,NAME FROM CITY WHERE NAME='$0.name'&quot; })
 * City create(City city);
 * </pre>
 * 
 * <p>
 * DAO objects are in fact, simple interface definitions, on witch each member
 * is annotated with @Query.
 * 
 * @author mhoms
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Query {

	String[] value();
}
