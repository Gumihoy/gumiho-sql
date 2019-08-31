package com.gumihoy.sql.basic.ast.expr.literal;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#boolean%20literal
 * https://dev.mysql.com/doc/refman/8.0/en/boolean-literals.html
 *
 * @author kent on 2019-06-15.
 */
public class SQLBooleanLiteral extends AbstractSQLExpr implements ISQLLiteral {

    protected Boolean value;

    public SQLBooleanLiteral() {
    }

    public SQLBooleanLiteral(Boolean value) {
        this.value = value;
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }


    @Override
    public SQLObjectType getObjectType() {
        return null;
    }


    @Override
    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
