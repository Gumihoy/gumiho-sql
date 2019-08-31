package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.literal.ISQLLiteral;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * question mark: ?
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#dynamic%20parameter%20specification
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLVariableExpr extends AbstractSQLExpr implements ISQLLiteral {

    public SQLVariableExpr() {
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLVariableExpr clone() {
        return new SQLVariableExpr();
    }


    @Override
    public String getValue() {
        return "?";
    }


}
