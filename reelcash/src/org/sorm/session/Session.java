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
package org.sorm.session;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Stack;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.sorm.exception.SormException;

/**
 * a Session implementation that wraps a {@link DataSource} reference; when the
 * session starts ({@link Session#beginTransaction()}), a fresh
 * {@link Connection} is retrieved, and mantained along the transaction
 * life-cycle, that dies when a {@link #commit()} or {@link #rollback()} call is
 * performed.
 * <p>
 * The {@link Connection} instance is exposed to the framework via
 * {@link #getConnection()}.
 * 
 * <p>
 * Note that nested transactions are supported, in order that the JDBC driver
 * manager supports the <tt>SavePoint</tt>s feature (see
 * {@link Connection#setSavepoint()}, etc.}.
 * 
 * @see SessionFactory
 * @author mhoms
 */
public class Session {

	private final static Logger LOGGER = Logger.getLogger(Session.class.getName());

	private final DataSource dataSource;
	private Connection connection;
	private final Stack<Savepoint> txStack;

	/**
	 * C'tor
	 * 
	 * @param dataSource the {@link DataSource} reference
	 */
	public Session(final DataSource dataSource) {
		super();
		this.dataSource = dataSource;
		txStack = new Stack<Savepoint>();
	}

	/**
	 * starts a (nested) transaction
	 * 
	 * @return the current {@link Connection} instance that holds the current
	 *         transaction
	 */
	public Connection beginTransaction() {
		if (isTransactionActive()) {
			final String savePointName = String.valueOf(txStack.size());
			try {
				final Savepoint savePoint = connection.setSavepoint(savePointName);
				txStack.push(savePoint);
				LOGGER.finer("tx begin: " + savePointName);
			} catch (final SQLException exc) {
				throw new SormException(
						"throwed exception attempting to establish a transaction savepoint: savePointName="
								+ savePointName, exc);
			}
		} else {
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(false);
				LOGGER.finer("tx begin");
			} catch (final SQLException exc) {
				throw new SormException("throwed exception attempting to obtain a fresh connection", exc);
			}
		}
		return connection;
	}

	/**
	 * exposes the current {@link Connection} reference.
	 * 
	 * @return the current {@link Connection} instance that holds the current
	 *         transaction
	 */
	public Connection getConnection() {
		if (connection == null) {
			throw new SormException("business operation are ocurred outside of a transaction bounds");
		}
		return connection;
	}

	/**
	 * Kills the current {@link Connection} that holds the current (nested)
	 * transaction, commiting the performed changes to the JDBC driver manager.
	 */
	public void commit() {
		if (txStack.isEmpty()) {
			try {
				connection.commit();
				LOGGER.finer("tx commit");
				close();
			} catch (final SQLException exc) {
				throw new SormException("throwed exception during transaction commit", exc);
			}
		} else {
			String savePointName = null;
			try {
				final Savepoint savePoint = txStack.pop();
				savePointName = savePoint.getSavepointName();
				connection.releaseSavepoint(savePoint);
				LOGGER.finer("tx commit" + savePointName);
			} catch (final SQLException exc) {
				throw new SormException(
						"throwed exception during transaction releaseSavepoint; savePointName:"
								+ savePointName, exc);
			}
		}
	}

	/**
	 * Kills the current {@link Connection} that holds the current (nested)
	 * transaction, rollbacking the performed changes to the JDBC driver
	 * manager.
	 */
	public void rollback() {
		if (txStack.isEmpty()) {
			try {
				connection.rollback();
				LOGGER.finer("tx rollback");
				close();
			} catch (final SQLException exc) {
				throw new SormException("throwed exception during transaction rollback", exc);
			}
		} else {
			String savePointName = null;
			try {
				final Savepoint savePoint = txStack.pop();
				savePointName = savePoint.getSavepointName();
				connection.rollback(savePoint);
				LOGGER.finer("tx rollback: " + savePointName);
			} catch (final SQLException exc) {
				throw new SormException("throwed exception during transaction rollback. savePointName="
						+ savePointName, exc);
			}
		}
	}

	/**
	 * closes the current {@link Connection} instance, due the transaction is
	 * closed.
	 */
	private void close() {
		try {
			connection.close();
		} catch (final SQLException exc) {
			throw new SormException("throwed exception closing connection", exc);
		}
		LOGGER.finer("tx closed");
	}

	/**
	 * @return <tt>true</tt> if there are an active transaction
	 */
	public boolean isTransactionActive() {
		try {
			return connection != null && !connection.isClosed();
		} catch (final SQLException e) {
			throw new SormException("throwed exception reading status from a connection", e);
		}
	}

}
