package com.google.code.reelcash.data;

/**
 * Represents the state in which a row may be.
 *
 * @author andrei.olar
 */
public enum RowState {

    /**
     * The row has been inserted
     */
    INSERTED((byte) 0),
    /**
     * The row has been modified.
     */
    MODIFIED((byte) 1),
    /**
     * The row has been deleted.
     */
    DELETED((byte) 2);
    private final byte status;

    /**
     * Creates a new state for the given row.
     *
     * @param status the row state.
     */
    RowState(byte status) {
        this.status = status;
    }

    /**
     * Returns a string representation for the row state.
     *
     * @return a string representation for a row state. 
     */
    @Override
    public String toString() {
        switch (status) {
            case 0:
                return "new";
            case 1:
                return "modified";
            case 2:
                return "deleted";
        }
        return "unknown";
    }
}
