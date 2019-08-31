package com.gumihoy.sql.basic.ast.expr.correlation;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * :OLD.name
 *
 * @author kent on 2019-08-22.
 */
public class SQLCorrelationParentName extends AbstractSQLCorrelationName {

    public SQLCorrelationParentName(String name) {
        super(name);
    }

    public SQLCorrelationParentName(ISQLIdentifier name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLCorrelationParentName clone() {
        throw new UnsupportedOperationException();
    }


}
