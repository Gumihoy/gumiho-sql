package com.gumihoy.sql.basic.ast.expr.index.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLCompression;
import com.gumihoy.sql.basic.ast.expr.common.ISQLParallelClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLTableSpaceSetClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ADD PARTITION
 * [ partition_name ]
 * [ TABLESPACE tablespace_name ]
 * [ index_compression ]
 * [ parallel_clause ]
 * <p>
 * add_hash_index_partition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-INDEX.html#GUID-D8F648E7-8C07-4C89-BB71-862512536558
 *
 * @author kent on 2019-08-02.
 */
public class SQLAlterIndexAddPartition extends AbstractSQLExpr implements ISQLAlterIndexPartitionAction {

    protected ISQLExpr name;
    protected SQLTableSpaceSetClause tableSpaceClause;
    protected ISQLCompression indexCompression;
    protected ISQLParallelClause parallelClause;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, tableSpaceClause);
            this.acceptChild(visitor, indexCompression);
            this.acceptChild(visitor, parallelClause);
        }
    }

    @Override
    public SQLAlterIndexAddPartition clone() {
        return null;
    }


    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        this.name = name;
    }

    public SQLTableSpaceSetClause getTableSpaceClause() {
        return tableSpaceClause;
    }

    public void setTableSpaceClause(SQLTableSpaceSetClause tableSpaceClause) {
        this.tableSpaceClause = tableSpaceClause;
    }

    public ISQLCompression getIndexCompression() {
        return indexCompression;
    }

    public void setIndexCompression(ISQLCompression indexCompression) {
        this.indexCompression = indexCompression;
    }

    public ISQLParallelClause getParallelClause() {
        return parallelClause;
    }

    public void setParallelClause(ISQLParallelClause parallelClause) {
        this.parallelClause = parallelClause;
    }
}
