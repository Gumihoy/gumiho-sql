package com.gumihoy.sql.basic.ast.expr.correlation;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.util.SQLUtils;

/**
 * :NEW.name
 * :OLD.name
 * :PARENT.name
 *
 * @author kent on 2019-08-22.
 */
public abstract class AbstractSQLCorrelationName extends AbstractSQLExpr implements ISQLCorrelationName {

    protected ISQLIdentifier name;

    public AbstractSQLCorrelationName(String name) {
        setName(SQLUtils.ofName(name));
    }

    public AbstractSQLCorrelationName(ISQLIdentifier name) {
        setName(name);
    }

    @Override
    public AbstractSQLCorrelationName clone() {
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
