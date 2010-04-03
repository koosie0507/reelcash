package org.sorm.dao.queries;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.sorm.dao.mapper.ResultMapper;
import org.sorm.exception.SormException;
import org.sorm.session.SessionFactory;

/**
 * this object is responsible to execute the processed queries and to send them
 * to the proper JDBC driver manager.
 *
 * @author mhoms
 */
public class QueryExecutor {

	private final static Logger LOGGER = Logger.getLogger(QueryExecutor.class.getName());

        public Object invokeSimpleQuery(final String query, final Class<?> returnType, final Object[] args) {

		final String processedQuery = QueryProcessor.processQuery(query, args);

		if (isSelectStatement(processedQuery)) {
			LOGGER.fine("[SELECT]: " + processedQuery);
			return executeSelect(returnType, processedQuery);
		} else {
			LOGGER.fine("[UPDATE]: " + processedQuery);
			return executeUpdate(processedQuery);
		}
	}

	private Object executeUpdate(final String query) {
		final Connection con = SessionFactory.getCurrentSession().getConnection();
		Statement st;
		final int rowsAffected;
		try {
			st = con.createStatement();
			rowsAffected = st.executeUpdate(query);
			st.close();
		} catch (final SQLException e) {
			throw new SormException("throwed exception during update execution in query: " + query,
					e);
		}
		return rowsAffected;
	}

	private Object executeSelect(final Class<?> returnType, final String query) {
		final Connection con = SessionFactory.getCurrentSession().getConnection();
		try {
			if (returnType.isArray()) {
				final ResultMapper queryMapper = new ResultMapper(returnType.getComponentType());
				return queryMapper.queryForEntities(con, query);
			} else {
				final ResultMapper queryMapper = new ResultMapper(returnType);
				return queryMapper.queryForEntity(con, query);
			}
		} catch (final SQLException e) {
			throw new SormException("throwed exception mapping results from query: " + query, e);
		}
	}

	/**
	 * <p>
	 * determina si una sentència ja processada és de tipus Select (que
	 * retornarà el resultat de la consulta), o bé una de tipus
	 * update/insert/delete, doncs retornarè un enter indicant les tuples
	 * afectades.
	 * </p>
	 * <p>
	 * Veure que el tipus es determina per si la sentència processada comença
	 * per "select", així que la query no pot començar per altres caràcters com
	 * espais, tabuladors, comentaris, etc.
	 * </p>
	 *
	 * @param query
	 * @return
	 */
	private boolean isSelectStatement(final String query) {
		return query.substring(0, query.indexOf(' ')).equalsIgnoreCase("SELECT");
	}

}