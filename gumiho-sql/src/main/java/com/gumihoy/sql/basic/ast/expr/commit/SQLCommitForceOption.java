package com.gumihoy.sql.basic.ast.expr.commit;

/**
 * @author kent on 2018/6/29.
 */

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * FORCE string [, integer ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/COMMIT.html#GUID-6CD5C9A7-54B9-4FA2-BA3C-D6B4492B9EE2
 *
 * @author kent on 2018/6/29.
 */
public class SQLCommitForceOption extends AbstractSQLExpr implements SQLCommitOption {

    protected ISQLExpr id;
    protected ISQLExpr scn;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if(visitor.visit(this)) {
//            this.acceptChild(visitor, id);
//            this.acceptChild(visitor, scn);
//        }
    }

    @Override
    public SQLCommitForceOption clone() {
        SQLCommitForceOption x = new SQLCommitForceOption();

        ISQLExpr idClone = this.id.clone();
        x.setId(idClone);

        if (this.scn != null) {
            ISQLExpr scnClone = this.scn.clone();
            x.setScn(scnClone);
        }

        return x;
    }

    public ISQLExpr getId() {
        return id;
    }

    public void setId(ISQLExpr id) {
        setChildParent(id);
        this.id = id;
    }

    public ISQLExpr getScn() {
        return scn;
    }

    public void setScn(ISQLExpr scn) {
        setChildParent(scn);
        this.scn = scn;
    }
}
