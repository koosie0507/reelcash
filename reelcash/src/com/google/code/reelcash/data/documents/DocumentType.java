package com.google.code.reelcash.data.documents;

/**
 * Enumerates known document types.
 * @author cusi
 */
public enum DocumentType {

    /**
     * Represents a basic, unnamed document.
     */
    DEFAULT('D', "default"),
    /**
     * Represents an invoice.
     */
    INVOICE('I', "invoice"),
    /**
     * Represents a contract.
     */
    CONTRACT('C', "contract"),
    /**
     * Represents a receipt.
     */
    RECEIPT('R', "receipt"),
    /**
     * Represents a payment.
     */
    PAYMENT('P', "payment");
    
    private char name;
    private String description;

    private DocumentType(char name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getLocalizedDescription() {
        return DocumentResources.getString(description.concat("_type_description"));
    }

    public char getName() {
        return name;
    }

    @Override
    public String toString() {
        return getDescription();
    }


}
