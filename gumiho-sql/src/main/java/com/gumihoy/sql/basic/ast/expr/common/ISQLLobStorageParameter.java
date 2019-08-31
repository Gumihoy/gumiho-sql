package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * { { { TABLESPACE tablespace
 * | TABLESPACE SET tablespace_set }
 * | LOB_parameters [storage_clause]
 * }...
 * | storage_clause
 * <p>
 * LOB_storage_parameters
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
 *
 * @author kent on 2018/6/27.
 */
public interface ISQLLobStorageParameter extends ISQLExpr {
    @Override
    ISQLLobStorageParameter clone();
}
