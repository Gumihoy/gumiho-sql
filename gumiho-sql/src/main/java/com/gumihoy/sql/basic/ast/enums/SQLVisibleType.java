package com.gumihoy.sql.basic.ast.enums;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;

/**
 * @author kent on 2018/6/22.
 */
public enum SQLVisibleType implements ISQLASTEnum {

    VISIBLE("visible", "VISIBLE"),
    INVISIBLE("invisible", "INVISIBLE"),
    ;

    public final String lower;
    public final String upper;

    SQLVisibleType(String lower, String upper) {
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
