package com.gumihoy.sql.basic.ast.expr.pseudorecords;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.correlation.ISQLCorrelationName;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.util.SQLUtils;

/**
 * xx.name
 *
 * @author kent on 2019-08-22.
 */
public abstract class AbstractSQLPseudoRecordName extends AbstractSQLExpr implements ISQLCorrelationName {

    protected ISQLIdentifier name;

    public AbstractSQLPseudoRecordName(String name) {
        this(SQLUtils.ofName(name));
    }

    public AbstractSQLPseudoRecordName(ISQLIdentifier name) {
        if (name == null) {
            throw new IllegalArgumentException("name is null.");
        }
        setName(name);
    }

    @Override
    public AbstractSQLPseudoRecordName clone() {
        throw new UnsupportedOperationException();
    }


    public ISQLIdentifier getName() {
        return name;
    }

    public void setName(ISQLIdentifier name) {
        setChildParent(name);
        this.name = name;
    }
}
