package com.gumihoy.sql.basic.ast.expr.common;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DEFAULT expr
 * :=   expr
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#default%20clause
 * <p>
 * DEFAULT default_value
 * https://dev.mysql.com/doc/refman/5.6/en/create-table.html
 * <p>
 * DEFAULT expr
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLDefaultClause extends AbstractSQLExpr {

    protected Operator operator = Operator.DEFAULT;

    protected ISQLExpr expr;

    public SQLDefaultClause() {
    }

    public SQLDefaultClause(ISQLExpr expr) {
        this(Operator.DEFAULT, expr);
    }

    public SQLDefaultClause(Operator operator, ISQLExpr expr) {
        this.operator = operator;
        setExpr(expr);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
        }
    }

    @Override
    public SQLDefaultClause clone() {
        SQLDefaultClause x = new SQLDefaultClause();
        x.operator = this.operator;

        ISQLExpr exprClone = this.expr.clone();
        x.setExpr(exprClone);

        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == expr) {
            this.setExpr(target);
            return true;
        }
        return false;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }

    public enum Operator implements ISQLASTEnum {

        DEFAULT("default", "DEFAULT"),
        VAR_ASSIGN_OP(":=", ":=");

        public final String lower;
        public final String upper;

        Operator(String lower, String upper) {
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

}
