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

/**
 * Wraps a T-typed instance named as "<tt>value</tt>". This object is used to
 * handle a single SQL result, such the result of an aggregate function, such as
 * <tt>COUNT(*)</tt>, <tt>MAX(*)</tt>, e.g.:
 * 
 * <pre>
 * &#064;Query(&quot;SELECT COUNT(*) AS VALUE FROM DOG&quot;)
 * SimpleResult&lt;Integer&gt; count();
 * </pre>
 * 
 * @author mhoms
 * @param <T> tipus de l'objecte a embolcallar
 */
public class SimpleResult<T> {

	public T value;

	public T getValue() {
		return value;
	}

	public void setValue(final T value) {
		this.value = value;
	}

}
