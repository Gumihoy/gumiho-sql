package com.gumihoy.sql.basic.ast.expr.table.partition;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * Partition By
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 *
 * @author kent onCondition 2018/4/10.
 */
public class SQLPartitionByList extends AbstractSQLPartitionBy {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, partitionsNum);
//            this.acceptChild(visitor, storeInClause);
            this.acceptChild(visitor, subPartitionBy);
            this.acceptChild(visitor, partitions);
        }
    }

    @Override
    public SQLPartitionByList clone() {
        SQLPartitionByList x = new SQLPartitionByList();
        this.cloneTo(x);
        return x;
    }
}
