package com.gumihoy.sql.basic.ast.enums;


import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483
 *
 * @author kent on 2018/4/19.
 */
public enum SQLCharSizeUnit implements ISQLASTEnum {

    BYTE("byte", "BYTE"),
    CHAR("char", "CHAR"),;

    public final String lower;
    public final String upper;

    SQLCharSizeUnit(String lower, String upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public static SQLCharSizeUnit of(String name) {
        for (SQLCharSizeUnit unit : SQLCharSizeUnit.values()) {
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
