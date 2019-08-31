package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * cell_reference IS PRESENT
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Model-Conditions.html#GUID-A26216BD-D937-412E-87B3-4B79F511AE38
 *
 * @author kent on 2018/5/11.
 */
public class SQLIsPresentCondition extends AbstractSQLExpr implements ISQLCondition{

    protected ISQLExpr cellReference;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, cellReference);
//        }
    }

    @Override
    public SQLIsPresentCondition clone() {
        SQLIsPresentCondition x = new SQLIsPresentCondition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLIsPresentCondition x) {
        super.cloneTo(x);

        ISQLExpr exprClone = this.cellReference.clone();
        x.setCellReference(exprClone);

    }

    public ISQLExpr getCellReference() {
        return cellReference;
    }

    public void setCellReference(ISQLExpr cellReference) {
        setChildParent(cellReference);
        this.cellReference = cellReference;
    }
}
