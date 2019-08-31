package com.gumihoy.sql.basic.ast.expr.pseudorecords;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * NEW.name
 *
 * @author kent on 2019-08-22.
 */
public class SQLPseudoNewRecordName extends AbstractSQLPseudoRecordName {


    public SQLPseudoNewRecordName(String name) {
        super(name);
    }

    public SQLPseudoNewRecordName(ISQLIdentifier name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLPseudoNewRecordName clone() {
        throw new UnsupportedOperationException();
    }


}
