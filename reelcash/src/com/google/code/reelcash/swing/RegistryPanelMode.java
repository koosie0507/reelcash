package com.google.code.reelcash.swing;

/**
 * Enumerates the possible modes of operation of a registry panel instance.
 *
 * @author andrei.olar
 */
public enum RegistryPanelMode {

    /**
     * This is the default mode. No one is in the process of creating data. This
     * is the only mode in which data sorting and filtering is allowed. Also, this
     * is the only mode when deleting data is allowed.
     */
    DEFAULT,
    /**
     * In insertion mode, new data is added. The panel is in insert mode when the
     * data edited with its item component will be added to the database upon a
     * press on the item component's save button.
     */
    INSERT,
    /**
     * In update mode, a selected row is edited. This means no insertions, no read
     * related operations, no selection changes and no subsequent updates are allowed.
     */
    UPDATE
}
