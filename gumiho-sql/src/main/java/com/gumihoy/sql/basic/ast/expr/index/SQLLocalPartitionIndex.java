package com.gumihoy.sql.basic.ast.expr.index;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLStoreInClause;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionDefinition;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * LOCAL ( PARTITION [ partition ] [ { segment_attributes_clause | index_compression }... ] [ USABLE | UNUSABLE ] [, PARTITION [ partition ] [ { segment_attributes_clause | index_compression }... ] [ USABLE | UNUSABLE ] ]...)
 * <p>
 * local_partitioned_index
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-INDEX.html#GUID-1F89BBC0-825F-4215-AF71-7588E31D8BFE
 *
 * @author kent on 2018/7/6.
 */
public class SQLLocalPartitionIndex extends AbstractSQLExpr {

    protected SQLStoreInClause storeInClause;
    protected final List<SQLPartitionDefinition> partitions = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, storeInClause);
//            this.acceptChild(visitor, items);
//        }
    }

    @Override
    public SQLLocalPartitionIndex clone() {
        SQLLocalPartitionIndex x = new SQLLocalPartitionIndex();
        if (storeInClause != null) {
            SQLStoreInClause storeInClauseClone = storeInClause.clone();
            x.setStoreInClause(storeInClauseClone);
        }

        for (SQLPartitionDefinition partition : this.partitions) {
            SQLPartitionDefinition partitionClone = partition.clone();
            x.addPartition(partitionClone);
        }
        return x;
    }

    public SQLStoreInClause getStoreInClause() {
        return storeInClause;
    }

    public void setStoreInClause(SQLStoreInClause storeInClause) {
        setChildParent(storeInClause);
        this.storeInClause = storeInClause;
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
