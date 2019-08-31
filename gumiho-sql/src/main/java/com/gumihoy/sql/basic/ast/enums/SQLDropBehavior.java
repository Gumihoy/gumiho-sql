package com.gumihoy.sql.basic.ast.enums;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;

/**
 * CASCADE | RESTRICT
 *
 * @author kent on 2018/6/12.
 */
public enum SQLDropBehavior implements ISQLASTEnum {

    CASCADE("cascade", "CASCADE"),
    CASCADE_CONSTRAINTS("cascade constraints", "CASCADE CONSTRAINTS"),
    RESTRICT("restrict", "RESTRICT"),
    ;

    public final String lower;
    public final String upper;

    SQLDropBehavior(String lower, String upper) {
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
