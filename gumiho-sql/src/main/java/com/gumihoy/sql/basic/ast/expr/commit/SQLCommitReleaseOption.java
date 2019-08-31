package com.gumihoy.sql.basic.ast.expr.commit;

/**
 * @author kent on 2018/6/29.
 */

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * [NO] RELEASE
 * https://dev.mysql.com/doc/refman/8.0/en/commit.html
 *
 * @author kent on 2018/6/29.
 */
public class SQLCommitReleaseOption extends AbstractSQLExpr implements SQLCommitOption {

    protected boolean no;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public SQLCommitReleaseOption clone() {
        SQLCommitReleaseOption x = new SQLCommitReleaseOption();

        x.no = this.no;
        return x;
    }

    public boolean isNo() {
        return no;
    }

    public void setNo(boolean no) {
        this.no = no;
    }
}
