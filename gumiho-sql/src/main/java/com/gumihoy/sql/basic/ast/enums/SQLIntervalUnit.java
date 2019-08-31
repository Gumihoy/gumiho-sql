package com.gumihoy.sql.basic.ast.enums;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#non-second%20primary%20datetime%20field
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/date-and-time-functions.html#function_date-add
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Literals.html#GUID-4C258D8F-3DF2-4D45-BE3E-14864DD77100
 *
 * @author kent on 2018/5/14.
 */
public enum SQLIntervalUnit implements ISQLASTEnum {

    YEAR("year", "YEAR"),
    MONTH("month", "MONTH"),
    DAY("day", "DAY"),
    HOUR("hour", "HOUR"),
    MINUTE("minute", "MINUTE"),
    SECOND("second", "SECOND"),
    ;

    public final String lower;
    public final String upper;

    SQLIntervalUnit(String lower, String upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public static SQLIntervalUnit of(String name) {
        for (SQLIntervalUnit unit : SQLIntervalUnit.values()) {
            if (unit.lower.equalsIgnoreCase(name)) {
                return unit;
            }
        }
        return null;
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
