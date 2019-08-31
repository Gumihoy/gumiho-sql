package com.gumihoy.sql.basic.ast.expr.table.partition.sub;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * SUBPARTITION TEMPLATE ( { range_subpartition_desc [, range_subpartition_desc] ... | list_subpartition_desc [, list_subpartition_desc] ... | individual_hash_subparts [, individual_hash_subparts] ... } ) | hash_subpartition_quantity
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/26.
 */
public class SQLSubpartitionTemplate extends AbstractSQLExpr {


    protected final List<SQLSubPartitionDefinition> subPartitions = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, subPartitions);
        }
    }

    @Override
    public SQLSubpartitionTemplate clone() {
        SQLSubpartitionTemplate x = new SQLSubpartitionTemplate();
        for (SQLSubPartitionDefinition subPartition : subPartitions) {
            SQLSubPartitionDefinition subPartitionClone = subPartition.clone();
            x.addSubPartition(subPartitionClone);
        }
        return x;
    }

    public List<SQLSubPartitionDefinition> getSubPartitions() {
        return subPartitions;
    }

    public void addSubPartition(SQLSubPartitionDefinition subPartition) {
        if (subPartition == null) {
            return;
        }
        setChildParent(subPartition);
        this.subPartitions.add(subPartition);
    }
}
