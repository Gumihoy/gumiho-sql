package com.gumihoy.sql.basic.ast.expr.datatype.money;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.datatype.AbstractSQLDataType;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * money
 * <p>
 * https://www.postgresql.org/docs/devel/static/datatype-money.html
 *
 * @author kent on 2018/4/20.
 */
public class SQLMoneyDataType extends AbstractSQLDataType {


    public SQLMoneyDataType() {
        super("MONEY");
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public SQLMoneyDataType clone() {
        SQLMoneyDataType x = new SQLMoneyDataType();
        return x;
    }

    @Override
    public void cloneTo(ISQLObject x) {
        super.cloneTo(x);
    }


}
