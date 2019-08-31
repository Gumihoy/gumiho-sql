package com.gumihoy.sql.basic.ast.expr.pseudorecords;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * PARENT.name
 *
 * @author kent on 2019-08-22.
 */
public class SQLPseudoOldRecordName extends AbstractSQLPseudoRecordName {

    public SQLPseudoOldRecordName(String name) {
        super(name);
    }

    public SQLPseudoOldRecordName(ISQLIdentifier name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLPseudoOldRecordName clone() {
        throw new UnsupportedOperationException();
    }


}
