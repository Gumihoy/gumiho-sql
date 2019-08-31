package com.gumihoy.sql.basic.ast.expr.index.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLParallelClause;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionDefinition;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * SPLIT PARTITION partition_name_old
 *    AT (literal [, literal ]...)
 *    [ INTO (index_partition_description,
 *            index_partition_description
 *           )
 *    ]
 *    [ parallel_clause ]
 * <p>
 * split_index_partition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-INDEX.html#GUID-D8F648E7-8C07-4C89-BB71-862512536558
 *
 * @author kent on 2019-08-02.
 */
public class SQLAlterIndexSplitPartition extends AbstractSQLExpr implements ISQLAlterIndexPartitionAction {

    protected ISQLExpr name;
    protected final List<ISQLExpr> atValues = new ArrayList<>();
    protected final List<SQLPartitionDefinition> partitions = new ArrayList<>();
    protected ISQLParallelClause parallelClause;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, atValues);
            this.acceptChild(visitor, partitions);
            this.acceptChild(visitor, parallelClause);
        }
    }

    @Override
    public SQLAlterIndexSplitPartition clone() {
        return null;
    }


    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        this.name = name;
    }

    public List<ISQLExpr> getAtValues() {
        return atValues;
    }

    public void addAtValue(ISQLExpr value) {
        if (value == null) {
            return;
        }
        setChildParent(value);
        this.atValues.add(value);
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

    public ISQLParallelClause getParallelClause() {
        return parallelClause;
    }

    public void setParallelClause(ISQLParallelClause parallelClause) {
        this.parallelClause = parallelClause;
    }


}
