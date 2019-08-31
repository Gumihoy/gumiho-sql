package com.gumihoy.sql.basic.ast.expr.index.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLParallelClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * COALESCE PARTITION [ parallel_clause ]
 * <p>
 * modify_index_partition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-INDEX.html#GUID-D8F648E7-8C07-4C89-BB71-862512536558
 *
 * @author kent on 2019-08-02.
 */
public class SQLAlterIndexCoalescePartition extends AbstractSQLExpr implements ISQLAlterIndexPartitionAction {

    protected ISQLParallelClause parallelClause;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if(visitor.visit(this)) {
            this.acceptChild(visitor, parallelClause);
        }
    }

    @Override
    public SQLAlterIndexCoalescePartition clone() {
        return null;
    }


    public ISQLParallelClause getParallelClause() {
        return parallelClause;
    }

    public void setParallelClause(ISQLParallelClause parallelClause) {
        this.parallelClause = parallelClause;
    }
}
