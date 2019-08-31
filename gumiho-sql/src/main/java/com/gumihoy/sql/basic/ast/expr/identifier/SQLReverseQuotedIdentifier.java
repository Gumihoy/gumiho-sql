package com.gumihoy.sql.basic.ast.expr.identifier;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * `xx`
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#delimited%20identifier
 *
 * @author kent on 2019-06-15.
 */
public class SQLReverseQuotedIdentifier extends AbstractSQLIdentifier implements ISQLName {

    public SQLReverseQuotedIdentifier(String name) {
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
