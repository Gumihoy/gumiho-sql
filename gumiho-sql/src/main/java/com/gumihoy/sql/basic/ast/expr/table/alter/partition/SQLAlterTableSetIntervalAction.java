package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SET INTERVAL ( [expr] )
 * <p>
 * alter_interval_partitioning
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableSetIntervalAction extends AbstractSQLExpr implements ISQLAlterTablePartitionAction {

    protected ISQLExpr expr;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, expr);
//        }
    }

    @Override
    public SQLAlterTableSetIntervalAction clone() {
        SQLAlterTableSetIntervalAction x = new SQLAlterTableSetIntervalAction();
        this.cloneTo(x);
        if (expr != null) {
            ISQLExpr exprClone = expr.clone();
            x.setExpr(exprClone);
        }
        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == expr) {
            setExpr(target);
            return true;
        }
        return false;
    }

    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }
}
