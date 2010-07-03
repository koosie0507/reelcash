package com.google.code.reelcash.data.documents;

/**
 * Enumerates known document types.
 * @author cusi
 */
public enum DocumentState {

    /**
     * Represents a basic, unnamed document.
     */
    NEW('N', "new"),
    /**
     * Represents an invoice.
     */
    ISSUED('I', "issued"),
    /**
     * Represents a contract.
     */
    RECEIVED('R', "received");

    private char name;
    private String description;

    private DocumentState(char name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getLocalizedDescription() {
        return DocumentResources.getString(description.concat("_state_description"));
    }

    public char getName() {
        return name;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
