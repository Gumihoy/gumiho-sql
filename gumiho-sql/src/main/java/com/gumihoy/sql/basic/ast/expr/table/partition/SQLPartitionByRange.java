package com.gumihoy.sql.basic.ast.expr.table.partition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * PARTITION BY RANGE{(expr) [PARTITIONS num]
 * [SUBPARTITION BY
 * { [LINEAR] HASH(expr)
 * | [LINEAR] KEY [ALGORITHM={1|2}] (column_list) }
 * [SUBPARTITIONS num]
 * ]
 * [(partition_definition [, partition_definition] ...)]
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * <p>
 * <p>
 * PARTITION BY RANGE ( column [, column]... )
 * [ INTERVAL <expr | (expr)> [ STORE IN ( tablespace [, tablespace]... ) ]]
 * { subpartition_by_range
 * | subpartition_by_list
 * | subpartition_by_hash
 * }
 * ( range_partition_desc [, range_partition_desc ]... )
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 *
 * @author kent onCondition 2018/4/10.
 */
public class SQLPartitionByRange extends AbstractSQLPartitionBy {

    protected boolean intervalParen;
    protected ISQLExpr interval;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, partitionsNum);
            this.acceptChild(visitor, interval);
//            this.acceptChild(visitor, storeInClause);
            this.acceptChild(visitor, subPartitionBy);
            this.acceptChild(visitor, partitions);
        }
    }

    @Override
    public SQLPartitionByRange clone() {
        SQLPartitionByRange x = new SQLPartitionByRange();
        this.cloneTo(x);
        return x;
    }

    public boolean isIntervalParen() {
        return intervalParen;
    }

    public void setIntervalParen(boolean intervalParen) {
        this.intervalParen = intervalParen;
    }

    public ISQLExpr getInterval() {
        return interval;
    }

    public void setInterval(ISQLExpr interval) {
        this.interval = interval;
    }
}
