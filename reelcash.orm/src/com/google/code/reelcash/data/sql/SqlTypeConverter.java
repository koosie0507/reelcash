/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.sql;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Types;

/**
 * Provides conversions between system types and SQL data types.
 *
 * @author andrei.olar
 */
public class SqlTypeConverter {

    private static final Object[][] data;

    static {
        data = new Object[6][3];
        data[0][0] = Integer.class;
        data[0][1] = Types.INTEGER;
        data[0][2] = "INTEGER";

        data[1][0] = BigDecimal.class;
        data[1][1] = Types.DECIMAL;
        data[1][2] = "DECIMAL";

        data[2][0] = Boolean.class;
        data[2][1] = Types.BIT;
        data[2][2] = "BIT";

        data[3][0] = Date.class;
        data[3][1] = Types.DATE;
        data[3][2] = "DATE";

        data[4][0] = Double.class;
        data[4][1] = Types.DOUBLE;
        data[4][2] = "DOUBLE";

        data[5][0] = String.class;
        data[5][1] = Types.NVARCHAR;
        data[5][2] = "TEXT";
    }

    private static int indexOf(Class<?> valueType) {
        for (int idx = data.length - 1; idx > -1; idx--)
            if (data[idx][0].equals(valueType))
                return idx;
        return -1;
    }

    public static String getTypeName(Class<?> valueType) {
        int idx = indexOf(valueType);
        return (idx > -1) ? (String) data[idx][2] : "INT";
    }
}
