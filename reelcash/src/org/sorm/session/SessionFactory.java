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

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.sorm.exception.SormException;

/**
 * org.sorm.session.SessionFactory implements a ThreadLocal pattern, allowing
 * for every threaded-user, a single instance of an org.sorm.session.Session
 * object.
 *
 * <p>
 * In order to use this factory, a static configuration is required:
 * org.sorm.session.SessionFactory encloses another ThreadLocal property, that
 * contains a DataSource reference. During application booting, this property
 * will be set statically with a propertly configured DataSource, or a
 * SormException will be thrown.
 *
 * <pre>
 * &lt;!-- SessionFactory static configuration --&gt;
 * &lt;bean class="org.sorm.session.SessionFactory"&gt;
 * 	&lt;property name="dataSource" ref="dataSource" /&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <p>
 * SessionFactory uses her DataSource reference to create the Session instances.
 *
 * @author mhoms
 */
public class SessionFactory {

    private final static Logger LOGGER = Logger.getLogger(SessionFactory.class.getName());
    /**
     * the session is stored in a thread-variable; every thread will have his
     * own {@link Session} instance.
     */
    private static ThreadLocal<Session> threadSession = new ThreadLocal<Session>();
    private static DataSource threadDataSource;

    /**
     * configures the factory with the supplied {@link DataSource}.
     *
     * @param dataSource
     */
    public static void setDataSource(final DataSource dataSource) {
        synchronized (dataSource) {
            SessionFactory.threadDataSource = dataSource;
            LOGGER.info("SessionFactory properly configured with DataSource: "
                    + dataSource.getClass().getName());
        }
    }

    /**
     * @return the current {@link Session} instance, or if it is inactive,
     *         creates a new one.
     */
    public static Session getCurrentSession() {
        Session session = threadSession.get();
        if (!isSessionActive()) {
            session = createNewSession();
            threadSession.set(session);
        }
        return session;
    }

    private static boolean isSessionActive() {
        return threadSession != null && threadSession.get() != null
                && threadSession.get().isTransactionActive();
    }

    /**
     * @return creates a new {@link Session} instance
     */
    private static Session createNewSession() {
        return new Session(threadDataSource);
    }
}
