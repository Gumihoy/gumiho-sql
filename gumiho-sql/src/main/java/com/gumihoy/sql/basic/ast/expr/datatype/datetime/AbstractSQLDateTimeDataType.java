package com.gumihoy.sql.basic.ast.expr.datatype.datetime;

import com.gumihoy.sql.basic.ast.enums.SQLWithTimeZoneType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.AbstractSQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;

/**
 * DATE
 * |     TIME [ <left paren> <time precision> <right paren> ] [ <with or without time zone> ]
 * |     TIMESTAMP [ <left paren> <timestamp precision> <right paren> ] [ <with or without time zone> ]
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#datetime%20type
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/date-and-time-type-overview.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483
 *
 * @author kent on 2018/4/19.
 */
public abstract class AbstractSQLDateTimeDataType extends AbstractSQLDataType implements ISQLDateTimeDataType {

    protected SQLWithTimeZoneType withTimeZoneType;


    public AbstractSQLDateTimeDataType() {
    }

    public AbstractSQLDateTimeDataType(String name) {
        super(name);
    }

    public AbstractSQLDateTimeDataType(String name, ISQLExpr... args) {
        super(name);
        for (ISQLExpr arg : args) {
            this.addArgument(arg);
        }
    }

    public AbstractSQLDateTimeDataType(ISQLName name) {
        super(name);
    }

    public AbstractSQLDateTimeDataType(ISQLName name, ISQLExpr... args) {
        super(name);
        for (ISQLExpr arg : args) {
            this.addArgument(arg);
        }
    }

    public SQLWithTimeZoneType getWithTimeZoneType() {
        return withTimeZoneType;
    }

    public void setWithTimeZoneType(SQLWithTimeZoneType withTimeZoneType) {
        this.withTimeZoneType = withTimeZoneType;
    }
}
