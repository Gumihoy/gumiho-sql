package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLIntegerLiteral;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#sequence%20generator%20start%20with%20option
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLReStartWithOption extends AbstractSQLExpr {

    protected SQLIntegerLiteral restartWith;

    protected boolean with = true;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if(visitor.visit(this)) {
//            this.acceptChild(visitor, restartWith);
//        }
    }

    @Override
    public SQLReStartWithOption clone() {
        SQLReStartWithOption x = new SQLReStartWithOption();

        SQLIntegerLiteral restartWithClone = this.restartWith.clone();
        x.setRestartWith(restartWithClone);

        x.setWith(this.with);

        return x;
    }

    public SQLIntegerLiteral getRestartWith() {
        return restartWith;
    }

    public void setRestartWith(SQLIntegerLiteral restartWith) {
        this.restartWith = restartWith;
    }

    public boolean isWith() {
        return with;
    }

    public void setWith(boolean with) {
        this.with = with;
    }
}
