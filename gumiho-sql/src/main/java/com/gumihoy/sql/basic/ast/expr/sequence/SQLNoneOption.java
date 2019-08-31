package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://www.postgresql.org/docs/current/static/sql-createsequence.html
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLNoneOption extends AbstractSQLExpr {


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public SQLNoneOption clone() {
        return new SQLNoneOption();
    }
}
