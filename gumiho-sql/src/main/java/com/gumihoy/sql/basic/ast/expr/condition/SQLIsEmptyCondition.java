package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * nested_table IS [ NOT ] EMPTY
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Multiset-Conditions.html#GUID-E8164A15-715A-40A0-944D-26DF4C84DE3F
 *
 * @author kent on 2018/5/11.
 */
public class SQLIsEmptyCondition extends AbstractSQLExpr implements ISQLCondition {

    protected ISQLName nestedTable;

    protected boolean not;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, nestedTable);
//        }
    }

    @Override
    public SQLIsEmptyCondition clone() {
        SQLIsEmptyCondition x = new SQLIsEmptyCondition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLIsEmptyCondition x) {
        super.cloneTo(x);

        ISQLName nestedTableClone = this.nestedTable.clone();
        x.setNestedTable(nestedTableClone);

        x.not = this.not;
    }


    public ISQLName getNestedTable() {
        return nestedTable;
    }

    public void setNestedTable(ISQLName nestedTable) {
        setChildParent(nestedTable);
        this.nestedTable = nestedTable;
    }

    public boolean isNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }
}
