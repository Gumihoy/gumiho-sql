package com.gumihoy.sql.basic.ast.expr.datatype.datetime;

import com.gumihoy.sql.basic.ast.enums.SQLWithTimeZoneType;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#datetime%20type
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/date-and-time-type-overview.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483
 *
 * @author kent on 2018/4/19.
 */
public interface ISQLDateTimeDataType extends ISQLDataType {


    SQLWithTimeZoneType getWithTimeZoneType();

    void setWithTimeZoneType(SQLWithTimeZoneType withTimeZoneType);
}
