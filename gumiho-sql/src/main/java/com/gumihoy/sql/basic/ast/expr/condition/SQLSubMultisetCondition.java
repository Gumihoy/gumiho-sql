package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * nested_table1 [ NOT ] SUBMULTISET [ OF ] nested_table2
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Multiset-Conditions.html#GUID-E8164A15-715A-40A0-944D-26DF4C84DE3F
 *
 * @author kent on 2018/5/11.
 */
public class SQLSubMultisetCondition extends AbstractSQLExpr implements ISQLCondition {

    protected ISQLName nestedTable1;

    protected boolean not;

    protected boolean of;

    protected ISQLName nestedTable2;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, nestedTable1);
//            this.acceptChild(visitor, nestedTable2);
//        }
    }


    @Override
    public SQLSubMultisetCondition clone() {
        SQLSubMultisetCondition x = new SQLSubMultisetCondition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLSubMultisetCondition x) {
        super.cloneTo(x);

        ISQLName nestedTable1Clone = this.nestedTable1.clone();
        x.setNestedTable1(nestedTable1Clone);

        x.not = this.not;
        x.of = this.of;

        ISQLName nestedTable2Clone = this.nestedTable2.clone();
        x.setNestedTable2(nestedTable2Clone);
    }



    public ISQLName getNestedTable1() {
        return nestedTable1;
    }

    public void setNestedTable1(ISQLName nestedTable1) {
        setChildParent(nestedTable1);
        this.nestedTable1 = nestedTable1;
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

    public ISQLName getNestedTable2() {
        return nestedTable2;
    }

    public void setNestedTable2(ISQLName nestedTable2) {
        setChildParent(nestedTable2);
        this.nestedTable2 = nestedTable2;
    }
}
