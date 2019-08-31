package com.gumihoy.sql.basic.ast.expr.commit;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/COMMIT.html#GUID-6CD5C9A7-54B9-4FA2-BA3C-D6B4492B9EE2
 *
 * @author kent on 2018/6/29.
 * @see SQLCommitStatement
 */
public interface SQLCommitOption extends ISQLExpr {
    @Override
    SQLCommitOption clone();
}
