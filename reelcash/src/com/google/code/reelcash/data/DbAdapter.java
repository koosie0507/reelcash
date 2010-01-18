/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import java.util.Hashtable;
import javax.swing.table.DefaultTableModel;

/**
 * Implement this interface in order to provide interaction with the database.
 * @author andrei.olar
 */
public interface DbAdapter {

	/**
	 * Creates a new record in the database.
	 * @param params data to populate the new record with.
	 * @return the primary key corresponding to the created record.
	 */
	public PrimaryKeyRow create(Object... params);

	/**
	 * Updates the record specified by the parameter key.
	 * @param key key by which we identify the record in the database.
	 * @param params update parameters
	 * @return true, if the update was successful.
	 */
	public boolean update(PrimaryKeyRow key, Object... params);

	/**
	 * Deletes the record identified by the key from the database.
	 * @param key identifies the record to delete.
	 * @return true, if the deletion was successful.
	 */
	public boolean delete(PrimaryKeyRow key);

	/**
	 * Deletes all records from the database.
	 * @return true, if clearing the container of the records was successful.
	 */
	public boolean clear();

	/**
	 * Populates a @see javax.swing.table.DefaultTableModel with data from a database table.
	 * @param model the model to fill.
	 * @return true, if no errors have occured.
	 */
	public boolean populateTableModel(DefaultTableModel model);

	/**
	 * Populates a table model with filtered data from the database.
	 * @param model table model to populate.
	 * @param mode filter mode for the query. only "ALL" is supported.
	 * @param filterExpressions valid boolean where conditions.
	 * @param params parameter values
	 * @return true, if the model has been populated.
	 */
	public boolean populateTableModel(DefaultTableModel model, FilterMode mode, String[] filterExpressions, Object... params);

	/**
	 * Returns the data of a record identified by the given key.
	 * @param key key which identifies a record.
	 * @return a hash table containing the record data.
	 */
	public Hashtable<String, Object> get(PrimaryKeyRow key);

	/**
	 * Returns an adapter which is responsible for working with the detail entity corresponding to the current
	 * adaptee entity.
	 * @return details adapter.
	 */
	public DbAdapter getDetailsAdapter();
}
