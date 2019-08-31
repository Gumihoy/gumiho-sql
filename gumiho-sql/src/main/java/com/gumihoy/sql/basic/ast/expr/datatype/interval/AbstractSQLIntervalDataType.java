package com.gumihoy.sql.basic.ast.expr.datatype.interval;

import com.gumihoy.sql.basic.ast.expr.datatype.AbstractSQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#interval%20type
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483
 *
 * @author kent on 2018/4/19.
 */
public abstract class AbstractSQLIntervalDataType extends AbstractSQLDataType implements ISQLIntervalDataType {

    public AbstractSQLIntervalDataType() {
    }

    public AbstractSQLIntervalDataType(String name) {
        super(name);
    }

    public AbstractSQLIntervalDataType(ISQLName name) {
        super(name);
    }
}
