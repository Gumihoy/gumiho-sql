package com.gumihoy.sql.basic.ast.enums;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;

/**
 * 优先级从低到高
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/About-SQL-Operators.html#GUID-CF1DBF8D-966F-4E5E-8AC8-9BF777B984D8
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/plsql-language-fundamentals.html#GUID-04DDDD9B-2D62-4D2D-BF89-74581CE78840
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/expression.html#GUID-D4700B45-F2C8-443E-AEE7-2BD20FFD45B8
 *
 * @author kent onCondition 2018/4/4.
 */
public enum SQLBinaryOperator implements ISQLASTEnum {

    OR("or", "OR"), // or
    LOGIC_OR("||"),       // ||

    XOR("xor", "XOR"), // xor

    And("and", "AND"),   // and
    LOGIC_AND("&&"),     // &&

    EQ("="),   // =
    LESS_THAN_OR_EQUAL_OR_GREATER_THAN("<=>"),   // <=>
    NOT_EQUAL("!="), // !=
    LESS_THAN_OR_GREATER("<>"), // <>
    NOT_EQUAL2("~="),   // ~=
    XOR_ASSIGN("^="), // ^=
    GREATER_THAN(">"), // >
    GREATER_THAN_OR_EQUALS(">="),   // >=
    LESS_THAN("<"), // <
    LESS_THAN_OR_EQUALS("<="), // <=

    BIT_OR("|"),     // |
    BIT_AND("&"),     // &

    LESS_THAN_LESS_THAN("<<"),                 // <<
    GREATER_THAN_GREATER_THAN(">>"),     // >>

    Add("+"),     // +
    Sub("-"), // -
    Concat("||"),       // ||

    Multiply("*"), // *
    Divide("/"),    // /
    DIV("div", "DIV"),   // DIV
    MOD("mod", "MOD"),   // mod
    PERCENT("%"),   // %

    BIT_XOR("^"),


    // union
    MULTISET_EXCEPT("MULTISET EXCEPT"),   // multiset except
    MULTISET_EXCEPT_ALL("MULTISET EXCEPT ALL"),
    MULTISET_EXCEPT_DISTINCT("multiset except distinct", "MULTISET EXCEPT DISTINCT"),

    MULTISET_INTERSECT("multiset intersect", "MULTISET INTERSECT"),
    MULTISET_INTERSECT_ALL("multiset intersect all", "MULTISET INTERSECT ALL"),
    MULTISET_INTERSECT_DISTINCT("multiset intersect distinct", "MULTISET INTERSECT DISTINCT"),

    MULTISET_UNION("multiset union", "MULTISET UNION"),
    MULTISET_UNION_ALL("multiset union all", "MULTISET UNION ALL"),
    MULTISET_UNION_DISTINCT("multiset union distinct", "MULTISET UNION DISTINCT"),

    COLLATE("collate", "COLLATE"),   // collate


    ;
    public final String lower;
    public final String upper;

    SQLBinaryOperator(String name) {
        this(name, name);
    }

    SQLBinaryOperator(String lower, String upper) {
        this.lower = lower;
        this.upper = upper;
    }


    @Override
    public String toString() {
        return upper;
    }


    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Arithmetic-Operators.html#GUID-46CD9FD8-FC94-44BA-AA62-30A16063EAAE
     */
    public static boolean isArithmetic(SQLBinaryOperator operator) {
        switch (operator) {
            case Add:
            case Sub:
            case Multiply:
            case Divide:
                return true;
            default:
                return false;
        }
    }

    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Arithmetic-Operators.html#GUID-46CD9FD8-FC94-44BA-AA62-30A16063EAAE
     */
    public static boolean isMultiset(SQLBinaryOperator operator) {
        switch (operator) {
            case MULTISET_EXCEPT:
            case MULTISET_EXCEPT_ALL:
            case MULTISET_EXCEPT_DISTINCT:

            case MULTISET_INTERSECT:
            case MULTISET_INTERSECT_ALL:
            case MULTISET_INTERSECT_DISTINCT:

            case MULTISET_UNION:
            case MULTISET_UNION_ALL:
            case MULTISET_UNION_DISTINCT:
                return true;
            default:
                return false;
        }
    }

    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Comparison-Conditions.html#GUID-828576BF-E606-4EA6-B94B-BFF48B67F927
     */
    public static boolean isComparison(SQLBinaryOperator operator) {
        switch (operator) {
            case EQ:
            case NOT_EQUAL:
            case LESS_THAN_OR_GREATER:
            case NOT_EQUAL2:
            case XOR_ASSIGN:

            case GREATER_THAN:
            case GREATER_THAN_OR_EQUALS:
            case LESS_THAN:
            case LESS_THAN_OR_EQUALS:

                return true;
            default:
                return false;
        }
    }


    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Logical-Conditions.html#GUID-C5E48AF2-3FF9-401D-A104-CDB5FC19E65F
     */
    public boolean isLogical() {
        return isLogical(this);
    }

    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Logical-Conditions.html#GUID-C5E48AF2-3FF9-401D-A104-CDB5FC19E65F
     */
    public static boolean isLogical(SQLBinaryOperator operator) {
        switch (operator) {
            case And:
            case OR:
                return true;
            default:
                return false;
        }
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
