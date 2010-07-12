package com.google.code.reelcash.data.taxes;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.data.sql.QueryMediator;
import java.sql.SQLException;

/**
 * Provides custom operations for taxes/excises.
 * @author cusi
 */
public class TaxesMediator extends QueryMediator {

    private static final Object lockObj = new Object();
    private static TaxesMediator instance;

    private TaxesMediator() {
        super(ReelcashDataSource.getInstance());
    }

    /**
     * Creates and returns the only instance of this class.
     * 
     * @return the singleton instance of this class.
     */
    public static TaxesMediator getInstance() {
        synchronized (lockObj) {
            if (null == instance) {
                instance = new TaxesMediator();
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
    public void setVatIsDefault(Integer vatId, Boolean isDefault) {
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

    /**
     * Searches for the taxes which apply to a given good.
     *
     * @param goodId the good
     * @return an array of data rows conforming to the TaxTypeNode's field list.
     */
    public DataRow[] getAppliedTaxesForGood(Integer goodId) {
        try {
            return fetch(TaxTypeNode.getInstance().getFieldList(), "select tt.* from tax_types tt inner join good_taxes gt on gt.tax_type_id=tt.id where gt.good_id=?", goodId);
        } catch (SQLException e) {
            throw new ReelcashException(e);
        }
    }

    public DataRow[] getAppliedExcisesForGood(Integer goodId) {
        try {
            return fetch(ExciseTypeNode.getInstance().getFieldList(), "select et.* from excise_types et inner join good_excises ge on ge.excise_type_id = et.id where ge.good_id=?", goodId);
        } catch (SQLException e) {
            throw new ReelcashException(e);
        }
    }
}
