package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * [ dimension_column IS ] ANY
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Model-Conditions.html#GUID-1F5B08DB-2B7A-4ECE-B51A-C753A426928B
 *
 * @author kent on 2018/5/11.
 */
public class SQLIsAnyCondition extends AbstractSQLExpr implements ISQLCondition {

    protected ISQLExpr column;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, column);
//        }
    }

    @Override
    public SQLIsAnyCondition clone() {
        SQLIsAnyCondition x = new SQLIsAnyCondition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLIsAnyCondition x) {
        super.cloneTo(x);

        ISQLExpr columnClone = this.column.clone();
        x.setColumn(columnClone);

    }

    public ISQLExpr getColumn() {
        return column;
    }

    public void setColumn(ISQLExpr column) {
        setChildParent(column);
        this.column = column;
    }
}
