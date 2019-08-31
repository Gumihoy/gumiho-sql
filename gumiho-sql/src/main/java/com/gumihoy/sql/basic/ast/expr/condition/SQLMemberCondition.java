package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * expr [ NOT ] MEMBER [ OF ] nested_table
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Multiset-Conditions.html#GUID-E8164A15-715A-40A0-944D-26DF4C84DE3F
 *
 * @author kent on 2018/5/11.
 */
public class SQLMemberCondition extends AbstractSQLExpr implements ISQLCondition{

    protected ISQLExpr expr;

    protected boolean not;

    protected boolean of;

    protected ISQLName nestedTable;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, expr);
//            this.acceptChild(visitor, nestedTable);
//        }
    }

    @Override
    public SQLMemberCondition clone() {
        SQLMemberCondition x = new SQLMemberCondition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLMemberCondition x) {
        super.cloneTo(x);

        ISQLExpr exprClone = this.expr.clone();
        x.setExpr(exprClone);

        x.not = this.not;
        x.of = this.of;

        ISQLName nestedTableClone = this.nestedTable.clone();
        x.setNestedTable(nestedTableClone);
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

    public boolean isOf() {
        return of;
    }

    public void setOf(boolean of) {
        this.of = of;
    }

    public ISQLName getNestedTable() {
        return nestedTable;
    }

    public void setNestedTable(ISQLName nestedTable) {
        setChildParent(nestedTable);
        this.nestedTable = nestedTable;
    }
}
