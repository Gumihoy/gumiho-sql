package com.gumihoy.sql.basic.ast.enums;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Pattern-matching-Conditions.html#GUID-0779657B-06A8-441F-90C5-044B47862A0A
 *
 * @author kent on 2018/5/11.
 */
public enum SQLLikeOperator implements ISQLASTEnum {

    LIKE("like", "LIKE"),
    LIKEC("likec", "LIKEC"),
    LIKE2("like2", "LIKE2"),
    LIKE4("like4", "LIKE4"),
    ;


    public final String lower;
    public final String upper;

    SQLLikeOperator(String name) {
        this(name, name);
    }

    SQLLikeOperator(String lower, String upper) {
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
