package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * COALESCE SUBPARTITION subpartition [update_index_clauses] [parallel_clause] [allow_disallow_clustering]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLCoalesceSubPartitionAction extends AbstractSQLAlterTableCoalescePartitionAction implements ISQLAlterTablePartitionAction {


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, expr);
//            this.acceptChild(visitor, updateIndex);
//            this.acceptChild(visitor, parallel);
//        }
    }

    @Override
    public SQLCoalesceSubPartitionAction clone() {
        SQLCoalesceSubPartitionAction x = new SQLCoalesceSubPartitionAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCoalesceSubPartitionAction x) {
        super.cloneTo(x);
    }


}
