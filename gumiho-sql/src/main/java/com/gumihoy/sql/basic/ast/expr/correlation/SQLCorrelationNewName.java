package com.gumihoy.sql.basic.ast.expr.correlation;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * :NEW.name
 *
 * @author kent on 2019-08-22.
 */
public class SQLCorrelationNewName extends AbstractSQLCorrelationName {

    public SQLCorrelationNewName(String name) {
        super(name);
    }

    public SQLCorrelationNewName(ISQLIdentifier name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLCorrelationNewName clone() {
        throw new UnsupportedOperationException();
    }


}
