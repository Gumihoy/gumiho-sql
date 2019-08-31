package com.gumihoy.sql.basic.ast.expr.pseudorecords;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * OLD.name
 *
 * @author kent on 2019-08-22.
 */
public class SQLPseudoParentRecordName extends AbstractSQLPseudoRecordName {

    public SQLPseudoParentRecordName(String name) {
        super(name);
    }

    public SQLPseudoParentRecordName(ISQLIdentifier name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLPseudoParentRecordName clone() {
        throw new UnsupportedOperationException();
    }


}
