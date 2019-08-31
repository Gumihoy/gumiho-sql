package com.gumihoy.sql.basic.ast.expr.commit;

/**
 * @author kent on 2018/6/29.
 */

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * COMMET string
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/COMMIT.html#GUID-6CD5C9A7-54B9-4FA2-BA3C-D6B4492B9EE2
 *
 * @author kent on 2018/6/29.
 */
public class SQLCommitCommentOption extends AbstractSQLExpr implements SQLCommitOption {

    protected ISQLExpr value;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, value);
//        }
    }

    @Override
    public SQLCommitCommentOption clone() {
        SQLCommitCommentOption x = new SQLCommitCommentOption();
        ISQLExpr valueClone = this.value.clone();
        x.setValue(valueClone);
        return x;
    }

    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
    }
}
