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
package org.sorm.exception;

/**
 * internal SORM unchecked exception.
 *
 * <p>
 * This exception type offers a singular feature: overrides the
 * {@link RuntimeException#toString()} method to produce a brief description of
 * nested exception causes.
 *
 * <pre>
 * &#064;Test
 * public void testNestedBriefing() {
 * 	try {
 * 		throw new SormException(&quot;A&quot;, new RuntimeException(&quot;B&quot;, new NullPointerException(&quot;C&quot;)));
 * 	} catch (final SormException e) {
 * 		assertEquals(&quot;Nested exceptions trace: \n&quot; + &quot;----------\n&quot;
 * 				+ &quot;Cause:     org.sorm.exception.SormException: A\n&quot;
 * 				+ &quot;Caused by: java.lang.RuntimeException: B\n&quot;
 * 				+ &quot;Caused by: java.lang.NullPointerException: C\n&quot; + &quot;----------\n&quot;
 * 				+ &quot;org.sorm.exception.SormException: A&quot;, e.toString());
 * 	}
 * }
 * </pre>
 *
 * @author mhoms
 */
public class SormException extends RuntimeException {

	private static final long serialVersionUID = -3720661123481120443L;

	public SormException(final String msg) {
		super(msg);
	}

	public SormException(final String msg, final Throwable exc) {
		super(msg, exc);
	}

	/**
	 * shows a briefly resume of nested exceptions
	 *
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder strb = new StringBuilder(66);

		strb.append("Nested exceptions trace: \n----------\n");

		Throwable throwable = this;
		while (throwable != null) {
			if (throwable == this) {
				strb.append("Cause:     ");
			} else {
				strb.append("Caused by: ");
			}
			strb.append(throwable.getClass().getName())
				.append(": ")
				.append(throwable.getMessage())
				.append('\n');

			throwable = throwable.getCause();
		}
		return strb
			.append("----------\n")
			.append(super.toString())
			.toString();
	}

}
