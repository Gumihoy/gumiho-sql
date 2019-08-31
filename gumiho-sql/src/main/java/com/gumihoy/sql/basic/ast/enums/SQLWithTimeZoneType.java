package com.gumihoy.sql.basic.ast.enums;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#datetime%20type
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483
 *
 * @author kent on 2018/4/19.
 */
public enum SQLWithTimeZoneType implements ISQLASTEnum {

    WITH_TIME_ZONE("with time zone", "WITH TIME ZONE"),
    WITH_LOCAL_TIME_ZONE("with local time zone", "WITH LOCAL TIME ZONE"),
    WITHOUT_TIME_ZONE("without time zone", "WITHOUT TIME ZONE"),
    ;

    public final String lower;
    public final String upper;

    SQLWithTimeZoneType(String lower, String upper) {
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public String toString() {
        return upper;
    }


    @Override
    public String lower() {
        return lower;
    }

    @Override
    public String upper() {
        return upper;
    }
}
