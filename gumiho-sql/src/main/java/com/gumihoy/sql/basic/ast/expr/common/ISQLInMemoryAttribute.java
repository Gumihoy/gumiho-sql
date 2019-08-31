package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * [ inmemory_memcompress ] [ inmemory_priority ] [ inmemory_distribute ] [ inmemory_duplicate ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/25.
 */
public interface ISQLInMemoryAttribute extends ISQLExpr {

    @Override
    ISQLInMemoryAttribute clone();
}
