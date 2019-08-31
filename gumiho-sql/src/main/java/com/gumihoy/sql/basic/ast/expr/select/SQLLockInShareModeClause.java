package com.gumihoy.sql.basic.ast.expr.select;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * LOCK IN SHARE MODE
 * https://dev.mysql.com/doc/refman/8.0/en/select.html
 *
 * @author kent on 2018/7/24.
 */
public class SQLLockInShareModeClause extends AbstractSQLExpr implements ISQLLockClause {
    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLLockInShareModeClause clone() {
        return new SQLLockInShareModeClause();
    }
}
