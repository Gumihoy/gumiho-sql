package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://www.postgresql.org/docs/current/static/sql-createsequence.html
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLOwnedByOption extends AbstractSQLExpr {

    protected ISQLName ownedBy;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, ownedBy);
//        }
    }

    @Override
    public SQLOwnedByOption clone() {
        SQLOwnedByOption x = new SQLOwnedByOption();

        ISQLName cloneOwnedBy = this.ownedBy.clone();
        x.setOwnedBy(cloneOwnedBy);

        return x;
    }

    public ISQLName getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(ISQLName ownedBy) {
        this.ownedBy = ownedBy;
    }
}
