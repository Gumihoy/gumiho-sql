package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#sequence%20generator%20data%20type%20option
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLAsDataTypeOption extends AbstractSQLExpr {

    protected ISQLDataType dataType;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, dataType);
//        }
    }

    @Override
    public SQLAsDataTypeOption clone() {
        SQLAsDataTypeOption x = new SQLAsDataTypeOption();
        ISQLDataType cloneDataType = this.dataType.clone();
        x.setDataType(cloneDataType);

        return x;
    }

    public ISQLDataType getDataType() {
        return dataType;
    }

    public void setDataType(ISQLDataType dataType) {
        this.dataType = dataType;
    }
}
