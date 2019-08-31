package com.gumihoy.sql.basic.ast.expr.correlation;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * :PARENT.name
 *
 * @author kent on 2019-08-22.
 */
public class SQLCorrelationOldName extends AbstractSQLCorrelationName {

    public SQLCorrelationOldName(String name) {
        super(name);
    }

    public SQLCorrelationOldName(ISQLIdentifier name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLCorrelationOldName clone() {
        throw new UnsupportedOperationException();
    }


}
