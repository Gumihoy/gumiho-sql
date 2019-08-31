package com.gumihoy.sql.basic.ast.expr.table.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLStoreInClause;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.ISQLSubPartitionBy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * https://dev.mysql.com/doc/refman/8.0/en/create-table.html
 * <p>
 * PARTITION BY
 * { [LINEAR] HASH(expr)
 * | [LINEAR] KEY [ALGORITHM={1|2}] (column_list)
 * | RANGE{(expr) | COLUMNS(column_list)}
 * | LIST{(expr) | COLUMNS(column_list)} }
 * [PARTITIONS num]
 * [SUBPARTITION BY
 * { [LINEAR] HASH(expr)
 * | [LINEAR] KEY [ALGORITHM={1|2}] (column_list) }
 * [SUBPARTITIONS num]
 * ]
 * [(partition_definition [, partition_definition] ...)]
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * <p>
 * <p>
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 *
 * @author kent onCondition 2018/4/10.
 */
public abstract class AbstractSQLPartitionBy extends AbstractSQLExpr implements ISQLPartitionBy {

    protected boolean linear = false;

    protected final List<ISQLExpr> columns = new ArrayList<>();


    protected ISQLExpr partitionsNum;

    protected SQLStoreInClause storeInClause;


    protected ISQLSubPartitionBy subPartitionBy;

    protected final List<SQLPartitionDefinition> partitions = new ArrayList<>();


    @Override
    public AbstractSQLPartitionBy clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLPartitionBy x) {
        super.cloneTo(x);

        x.linear = this.linear;

        for (ISQLExpr column : this.columns) {
            ISQLExpr columnClone = column.clone();
            x.addColumn(columnClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = replaceInList(columns, source, target, this);
        if (replace) {
            return true;
        }

        if (source == partitionsNum) {
            setPartitionsNum(target);
            return true;
        }
        return false;
    }

    public boolean isLinear() {
        return linear;
    }

    public void setLinear(boolean linear) {
        this.linear = linear;
    }

    public List<ISQLExpr> getColumns() {
        return columns;
    }

    public void addColumn(ISQLExpr column) {
        if (column == null) {
            return;
        }
        setChildParent(column);
        this.columns.add(column);
    }


    public SQLStoreInClause getStoreInClause() {
        return storeInClause;
    }

    public void setStoreInClause(SQLStoreInClause storeInClause) {
        setChildParent(storeInClause);
        this.storeInClause = storeInClause;
    }

    public ISQLExpr getPartitionsNum() {
        return partitionsNum;
    }

    public void setPartitionsNum(ISQLExpr partitionsNum) {
        setChildParent(partitionsNum);
        this.partitionsNum = partitionsNum;
    }

    public ISQLSubPartitionBy getSubPartitionBy() {
        return subPartitionBy;
    }

    public void setSubPartitionBy(ISQLSubPartitionBy subPartitionBy) {
        setChildParent(subPartitionBy);
        this.subPartitionBy = subPartitionBy;
    }

    public List<SQLPartitionDefinition> getPartitions() {
        return partitions;
    }

    public void addPartition(SQLPartitionDefinition partition) {
        if (partition == null) {
            return;
        }
        setChildParent(partition);
        this.partitions.add(partition);
    }
}
