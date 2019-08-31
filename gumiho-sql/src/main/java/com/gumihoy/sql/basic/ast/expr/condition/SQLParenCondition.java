package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * (condition)
 *
 * @author kent on 2018/5/17.
 */
public class SQLParenCondition extends AbstractSQLExpr implements ISQLCondition {

    protected ISQLCondition condition;

    public SQLParenCondition(ISQLCondition condition) {
        setCondition(condition);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, condition);
//        }
    }


    @Override
    public SQLParenCondition clone() {
        ISQLCondition conditionClone = condition.clone();

        SQLParenCondition x = new SQLParenCondition(conditionClone);
        return x;
    }

    public ISQLCondition getCondition() {
        return condition;
    }

    public void setCondition(ISQLCondition condition) {
        setChildParent(condition);
        this.condition = condition;
    }
}
