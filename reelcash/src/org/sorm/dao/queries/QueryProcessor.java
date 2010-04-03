package org.sorm.dao.queries;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sorm.exception.SormException;

/**
 * this object is responsible of evaluating the {@link Query}'s expressions
 * (e.g. <tt>$0.class.name</tt>) and to replace them for his corresponding
 * value.
 *
 * @author mhoms
 */
final class QueryProcessor {

	private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[] {};
	private static final String[] EMPTY_STRING_ARRAY = new String[] {};

	private final static Pattern EXP_PATTERN = Pattern.compile("\\$(\\d+)((\\.\\w+)*)");

	private QueryProcessor() {

	}

	/**
	 * evaluates the expressions found in the query, replacin them by the
	 * resulting values
	 *
	 * Group index:
	 * <ul>
	 * <li>#0: the entire expression matching: $NUMBER{.PROP}</li>
	 * <li>#1: the number of a parameter</li>
	 * <li>#2: accessor chain {.PROP}</li>
	 * </ul>
	 *
	 * @param query
	 * @param args
	 * @return
	 */
	public static String processQuery(final String query, final Object[] args) {

		final Matcher matcher = EXP_PATTERN.matcher(query);
		final StringBuffer strb = new StringBuffer();

		while (matcher.find()) {
			final String expressionResult;
			try {
				expressionResult = tokenize(matcher.group(1), matcher.group(2), args);
			} catch (final Exception exc) {
				throw new SormException("exception parsing expression: " + matcher.group(), exc);
			}

			matcher.appendReplacement(strb, expressionResult);
		}
		matcher.appendTail(strb);

		// escapa "$$" per "$"
		return strb.toString().replaceAll("\\$\\$", "\\$");
	}

	private static String tokenize(final String numArg, final String accessorList, final Object[] args) {
		final Object value = args[Integer.valueOf(numArg)];
		String[] properties = null;

		if (accessorList.length() > 1) {
			properties = accessorList.substring(1).split("\\.");
		} else {
			properties = EMPTY_STRING_ARRAY;
		}

		return evaluateExpression(value, properties);
	}

	private static String evaluateExpression(final Object argValue, final String[] properties) {

		Object value = argValue;
		Method getter;

		for (final String property : properties) {
			try {
				getter = value.getClass().getMethod(
						"get" + Character.toUpperCase(property.charAt(0)) + property.substring(1),
						EMPTY_CLASS_ARRAY);
			} catch (final Exception exc) {
				throw new SormException("getter method not found, for property: "
						+ value.getClass().getSimpleName() + "." + property, exc);
			}
			try {
				value = getter.invoke(value, ((Object[]) null));
			} catch (final Exception exc) {
				throw new SormException("error invoking getter method: " + getter.toString(), exc);
			}
		}

		return value.toString().replaceAll("'", "''");
	}

}
