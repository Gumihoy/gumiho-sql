package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * COALESCE PARTITION number
 * https://dev.mysql.com/doc/refman/5.6/en/alter-table.html
 *
 *
 * COALESCE PARTITION [ update_index_clauses ] [ parallel_clause ] [ allow_disallow_clustering ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/12.
 */
public class SQLAlterTableCoalescePartitionAction extends AbstractSQLAlterTableCoalescePartitionAction implements ISQLAlterTablePartitionAction {


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, expr);
//        }
    }

    @Override
    public SQLAlterTableCoalescePartitionAction clone() {
        SQLAlterTableCoalescePartitionAction x = new SQLAlterTableCoalescePartitionAction();
        super.cloneTo(x);
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

}
