package com.gumihoy.sql.basic.ast.expr.index.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.index.SQLRebuildClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * REBUILD
 * [ { PARTITION partition
 * | SUBPARTITION subpartition
 * }
 * | { REVERSE | NOREVERSE }
 * ]
 * [ parallel_clause
 * | TABLESPACE tablespace
 * | PARAMETERS ( 'ODCI_parameters' )
 * | XMLIndex_parameters_clause
 * | ONLINE
 * | physical_attributes_clause
 * | index_compression
 * | logging_clause
 * | partial_index_clause
 * ]...
 * <p>
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-INDEX.html#GUID-D8F648E7-8C07-4C89-BB71-862512536558
 *
 * @author kent on 2018/7/9.
 */
public class SQLAlterIndexRebuildAction extends AbstractSQLExpr implements ISQLAlterIndexAction {

    protected SQLRebuildClause rebuildClause;

//    protected SQLInvalidationType invalidation;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, rebuildClause);
//        }
    }

    @Override
    public SQLAlterIndexRebuildAction clone() {
        SQLAlterIndexRebuildAction x = new SQLAlterIndexRebuildAction();


//        x.invalidation = this.invalidation;
        return x;
    }

    public SQLRebuildClause getRebuildClause() {
        return rebuildClause;
    }

    public void setRebuildClause(SQLRebuildClause rebuildClause) {
        setChildParent(rebuildClause);
        this.rebuildClause = rebuildClause;
    }

//    public SQLInvalidationType getInvalidation() {
//        return invalidation;
//    }
//
//    public void setInvalidation(SQLInvalidationType invalidation) {
//        this.invalidation = invalidation;
//    }
}
