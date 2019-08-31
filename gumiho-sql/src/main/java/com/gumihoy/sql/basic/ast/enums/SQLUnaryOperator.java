package com.gumihoy.sql.basic.ast.enums;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/About-SQL-Operators.html#GUID-CF1DBF8D-966F-4E5E-8AC8-9BF777B984D8
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/expression.html#GUID-D4700B45-F2C8-443E-AEE7-2BD20FFD45B8
 *
 * @author kent on 2018/4/4.
 */
public enum SQLUnaryOperator implements ISQLASTEnum {

    POSITIVE("+"),
    NEGATIVE("-"),
    BIT_INVERSION("~"),

    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Hierarchical-Query-Operators.html#GUID-95F6A554-C6FE-42CD-88A6-7A1C162ED964
     */
    PRIOR("prior", "PRIOR"),

    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Hierarchical-Query-Operators.html#GUID-95F6A554-C6FE-42CD-88A6-7A1C162ED964
     */
    CONNECT_BY_ROOT("connect_by_root", "CONNECT_BY_ROOT"),

    /**
     * https://dev.mysql.com/doc/refman/8.0/en/charset-binary-set.html
     */
    BINARY("binary", "BINARY"),

    COLLATE("collate", "COLLATE"),

    NOT("not", "NOT"),

    ;

    public final String lower;
    public final String upper;

    SQLUnaryOperator(String name) {
        this(name, name);
    }

    SQLUnaryOperator(String lower, String upper) {
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
