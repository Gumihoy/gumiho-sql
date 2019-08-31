package com.gumihoy.sql.basic.ast.expr.index.alter.partition;

import com.gumihoy.sql.basic.ast.expr.index.alter.ISQLAlterIndexAction;

/**
 * alter_index_partitioning
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-INDEX.html#GUID-D8F648E7-8C07-4C89-BB71-862512536558
 *
 * @author kent on 2019-08-02.
 */
public interface ISQLAlterIndexPartitionAction extends ISQLAlterIndexAction {
    @Override
    ISQLAlterIndexPartitionAction clone();
}
