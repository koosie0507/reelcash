/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.sql;

import com.google.code.reelcash.data.layout.fields.BigDecimalField;
import com.google.code.reelcash.data.layout.fields.BooleanField;
import com.google.code.reelcash.data.layout.fields.DateField;
import com.google.code.reelcash.data.layout.fields.DoubleField;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * Renders various field types to SQL strings.
 *
 * @author andrei.olar
 */
public abstract class SqlFieldRenderer {

    private final Field renderedInstance;

    /**
     * Initializes the class with the given field.
     * @param rendered the field to render.
     */
    protected SqlFieldRenderer(final Field rendered) {
        this.renderedInstance = rendered;
    }

    protected Field getRenderedInstance() {
        return renderedInstance;
    }

    public abstract String renderType();

    public String renderName() {
        String name = getRenderedInstance().getName();
        return "`".concat(name).concat("`");
    }

    public String renderNull() {
        return (getRenderedInstance().isMandatory() ? "NOT NULL" : "");
    }

    /**
     * Creates a new SQL renderer for the specified field.
     *
     * @param f the field to render.
     * @return a new SQL renderer.
     */
    public static SqlFieldRenderer newInstance(Field f) {
        if (f instanceof BigDecimalField)
            return new BigDecimalRenderer(f);
        if (f instanceof BooleanField)
            return new BooleanRenderer(f);
        if (f instanceof DateField)
            return new DateRenderer(f);
        if (f instanceof DoubleField)
            return new DoubleRenderer(f);
        if (f instanceof IntegerField)
            return new IntegerRenderer(f);

        return new StringRenderer(f);
    }
}

class BigDecimalRenderer extends SqlFieldRenderer {

    BigDecimalRenderer(Field f) {
        super(f);
    }

    @Override
    public String renderType() {
        BigDecimalField field = (BigDecimalField) getRenderedInstance();
        StringBuilder sql = new StringBuilder("DECIMAL(");
        sql.append(field.getScale());
        sql.append(",");
        sql.append(field.getPrecision());
        sql.append(")");
        return sql.toString();
    }
}

class BooleanRenderer extends SqlFieldRenderer {

    BooleanRenderer(Field f) {
        super(f);
    }

    @Override
    public String renderType() {
        return "BIT";
    }
}

class DateRenderer extends SqlFieldRenderer {

    DateRenderer(Field f) {
        super(f);
    }

    @Override
    public String renderType() {
        return "DATETIME";
    }
}

class DoubleRenderer extends SqlFieldRenderer {

    DoubleRenderer(Field f) {
        super(f);
    }

    @Override
    public String renderType() {
        return "DOUBLE";
    }
}

class IntegerRenderer extends SqlFieldRenderer {

    IntegerRenderer(Field f) {
        super(f);
    }

    @Override
    public String renderType() {
        return "INT";
    }
}

class StringRenderer extends SqlFieldRenderer {

    StringRenderer(Field rendered) {
        super(rendered);
    }

    @Override
    public String renderType() {
        StringField rendered = (StringField) getRenderedInstance();
        if (rendered.getMaxSize() > 0) {
            StringBuilder sql = new StringBuilder("NVARCHAR(");
            sql.append(rendered.getMaxSize());
            sql.append(")");
            return sql.toString();
        }
        else
            return "TEXT";
    }
}
