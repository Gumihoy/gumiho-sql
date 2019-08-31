package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.enums.SQLLikeOperator;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * char1 [ NOT ] { LIKE | LIKEC | LIKE2 | LIKE4 } char2 [ ESCAPE esc_char ]
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#like%20predicate
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Pattern-matching-Conditions.html#GUID-3FA7F5AB-AC64-4200-8F90-294101428C26
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/string-comparison-functions.html#operator_like
 *
 * @author kent on 2018/5/11.
 */
public class SQLLikeCondition extends AbstractSQLExpr implements ISQLCondition {

    protected ISQLExpr expr;

    protected boolean not;

    protected SQLLikeOperator operator = SQLLikeOperator.LIKE;

    protected ISQLExpr pattern;

    protected ISQLExpr escape;

    public SQLLikeCondition() {
    }

    public SQLLikeCondition(ISQLExpr expr, ISQLExpr pattern) {
        this(expr, SQLLikeOperator.LIKE, pattern);
    }

    public SQLLikeCondition(ISQLExpr expr, SQLLikeOperator operator, ISQLExpr pattern) {
        this(expr, false, operator, pattern);
    }

    public SQLLikeCondition(ISQLExpr expr, boolean not, SQLLikeOperator operator, ISQLExpr pattern) {
        this(expr, false, operator, pattern, null);
    }

    public SQLLikeCondition(ISQLExpr expr, boolean not, SQLLikeOperator operator, ISQLExpr pattern, ISQLExpr escape) {
        this.setExpr(expr);
        this.not = not;
        this.operator = operator;
        this.setPattern(pattern);
        this.setEscape(escape);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
            this.acceptChild(visitor, pattern);
            this.acceptChild(visitor, escape);
        }
    }


    @Override
    public SQLLikeCondition clone() {
        ISQLExpr exprClone = this.expr.clone();
        ISQLExpr patternClone = this.pattern.clone();

        SQLLikeCondition x = new SQLLikeCondition(exprClone, patternClone);

        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLLikeCondition x) {
        super.cloneTo(x);

        x.not = this.not;

        if (this.escape != null) {
            ISQLExpr escapeClone = this.escape.clone();
            x.setEscape(escapeClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        return false;
    }

    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }

    public boolean isNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    public SQLLikeOperator getOperator() {
        return operator;
    }

    public void setOperator(SQLLikeOperator operator) {
        this.operator = operator;
    }

    public ISQLExpr getPattern() {
        return pattern;
    }

    public void setPattern(ISQLExpr pattern) {
        setChildParent(pattern);
        this.pattern = pattern;
    }

    public ISQLExpr getEscape() {
        return escape;
    }

    public void setEscape(ISQLExpr escape) {
        setChildParent(escape);
        this.escape = escape;
    }
}
