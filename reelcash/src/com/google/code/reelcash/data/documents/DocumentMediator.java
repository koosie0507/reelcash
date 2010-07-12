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
        stateIds.put(DocumentState.NEW, new Integer(1));
        stateIds.put(DocumentState.ISSUED, new Integer(2));
        stateIds.put(DocumentState.RECEIVED, new Integer(3));
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

    public DocumentState getState(Integer stateId) {
        for (Entry<DocumentState, Integer> entry : stateIds.entrySet()) {
            if (stateId.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        throw new ReelcashException(DocumentResources.getString("unknown_state_id"));
    }

    /**
     * Sets the state of a document to the given value.
     *
     * @param documentId the ID of the modified document.
     * @param state the new state of the document.
     */
    public void setState(Integer documentId, DocumentState state) {
        if (!stateIds.containsKey(state)) {
            throw new ReelcashException(DocumentResources.getString("unknown_state"));
        }
        try {
            beginTransaction();
            execute("update documents set state_id = ? where id = ?",
                    stateIds.get(state), documentId);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ReelcashException(e);
        }
    }
}
