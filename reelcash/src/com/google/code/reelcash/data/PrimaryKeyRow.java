/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import com.google.code.reelcash.Log;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;

/**
 * Provides a means of getting a generic primary key value.
 * 
 * @author andrei.olar
 */
public class PrimaryKeyRow {

	private Hashtable<String, Object> data;

	/**
	 * Creates a new primary key value and specifies the names of the columns and the types of the values on those columns.
	 * @param columnNames names of columns which make up the primary key.
	 */
	public PrimaryKeyRow(String... columnNames) {
		data = new Hashtable<String, Object>(columnNames.length);
		int i = 0;
		while (i < columnNames.length) {
			data.put(columnNames[i], new Object());
			i++;
		}
	}

	public void set(String columnName, Object value) {
		data.put(columnName, value);
	}

	public Object get(String columnName) {
		return data.get(columnName);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PrimaryKeyRow other = (PrimaryKeyRow) obj;

		if (this.data != other.data || this.data == null)
			return false;

		if (this.data.keySet().size() != other.data.keySet().size())
			return false;

		Enumeration<String> cols1 = this.data.keys();
		Enumeration<String> cols2 = other.data.keys();
		boolean areEqual = true;
		while (cols1.hasMoreElements() && areEqual) {
			String col1 = cols1.nextElement();
			String col2 = cols2.nextElement();
			areEqual = col1.equalsIgnoreCase(col2);

			Object val1 = this.data.get(col1);
			Object val2 = this.data.get(col2);
			areEqual = ((val1 == val2) || (val1 != null && val1.equals(val2)));
		}

		return areEqual;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		Enumeration<String> columns = data.keys();
		while (columns.hasMoreElements()) {
			Object value = data.get(columns.nextElement());
			hash = 11 * hash + (null != value ? value.hashCode() : 0);
		}
		return hash;
	}
}
