package com.google.code.reelcash.data.taxes;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.data.sql.QueryMediator;
import java.sql.SQLException;

/**
 * Provides custom operations for value added tax.
 * @author cusi
 */
public class VatMediator extends QueryMediator {

    private static final Object lockObj = new Object();
    private static VatMediator instance;

    private VatMediator() {
        super(ReelcashDataSource.getInstance());
    }

    /**
     * Creates and returns the only instance of this class.
     * 
     * @return the singleton instance of this class.
     */
    public static VatMediator getInstance() {
        synchronized (lockObj) {
            if (null == instance) {
                instance = new VatMediator();
            }
        }
        return instance;
    }

    /**
     * Gets the data row which contains the information for the first default
     * value added tax found in the database.
     *
     * @return a data row containing the information for the first default value
     * added tax in the database.
     */
    public DataRow getDefaultVatType() {
        final DataRow ret;
        try {
            DataRow[] result = fetch(VatTypeNode.getInstance().getFieldList(), "select id, code, name, percent, is_default from vat_types where is_default=1;");
            if (result.length > 0) {
                ret = result[0];
            } else {
                ret = null;
            }
        } catch (SQLException e) {
            throw new ReelcashException(e);
        }
        return ret;
    }

    /**
     * Marks the vat type with the given ID as default.
     * 
     * @param vatId the ID of the VAT type.
     * @param isDefault whether the VAT type will be default or not.
     */
    public void markDefault(Integer vatId, Boolean isDefault) {
        try {
            Integer defCount = (Integer) executeScalar("select count(id) from vat_types where is_default=1;");
            if (isDefault.booleanValue() && 0 < defCount) {
                execute("update vat_types set is_default=0 where is_default=1;");
            }

            execute("update vat_types set is_default=? where id=?;", isDefault, vatId);
        } catch (SQLException e) {
            throw new ReelcashException(e);
        }
    }
}
