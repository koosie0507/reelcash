package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.data.sql.QueryMediator;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * SQL mediator for documents.
 * @author cusi
 */
public class DocumentMediator extends QueryMediator {

    private static DocumentMediator instance;
    private final HashMap<DocumentState, Integer> stateIds;

    private DocumentMediator() {
        super(ReelcashDataSource.getInstance());
        stateIds = new HashMap<DocumentState, Integer>(3);
        stateIds.put(DocumentState.NEW, null);
        stateIds.put(DocumentState.ISSUED, null);
        stateIds.put(DocumentState.RECEIVED, null);
    }

    public static DocumentMediator getInstance() {
        if (null == instance) {
            instance = new DocumentMediator();
        }
        return instance;
    }

    public void ensureStates() {
        final String sql1 = "select id from document_states where name=?";
        final String sql2 = "insert into document_states(name, description) values(?, ?);";
        final String sql3 = "select last_insert_rowid();";
        try {
            beginTransaction();
            for (Entry<DocumentState, Integer> state : stateIds.entrySet()) {
                Integer stateId = (Integer) executeScalar(sql1, state.getKey().getName());
                if (null == stateId) {
                    if (0 < execute(sql2, state.getKey().getName(), state.getKey().getLocalizedDescription())) {
                        state.setValue((Integer) executeScalar(sql3));
                    }
                } else {
                    state.setValue(stateId);
                }
            }
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ReelcashException(e);
        }
    }

    public Integer getStateId(DocumentState state) {
        return stateIds.get(state);
    }
}
