package com.gumihoy.sql.basic.ast.enums;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;

/**
 * SQL SECURITY { DEFINER | INVOKER }
 * https://dev.mysql.com/doc/refman/8.0/en/create-view.html
 * https://dev.mysql.com/doc/refman/8.0/en/alter-view.html
 */
public enum SQLSecurityType implements ISQLASTEnum {

    SQL_SECURITY_DEFINER("SQL SECURITY DEFINER"),
    SQL_SECURITY_INVOKER("SQL SECURITY INVOKER"),
    ;
    public final String upper;

    SQLSecurityType(String upper) {
        this.upper = upper;
    }

    @Override
    public String toString() {
        return upper;
    }


    @Override
    public String lower() {
        return null;
    }

    @Override
    public String upper() {
        return upper;
    }
}
