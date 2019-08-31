package com.gumihoy.sql.basic.ast.expr.identifier;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * @author kent on 2019-06-15.
 */
public class SQLUnquotedIdentifier extends AbstractSQLIdentifier implements ISQLName {

    public SQLUnquotedIdentifier(String name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;
    }
}
