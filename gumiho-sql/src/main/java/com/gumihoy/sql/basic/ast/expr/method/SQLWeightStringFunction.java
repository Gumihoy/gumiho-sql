package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * WEIGHT_STRING(str [AS {CHAR|BINARY}(N)] [flags])
 *
 * https://dev.mysql.com/doc/refman/8.0/en/string-functions.html
 * @author kent on 2018/7/25.
 */
public class SQLWeightStringFunction extends AbstractSQLFunction {

    protected ISQLDataType dataType;

    public SQLWeightStringFunction() {
//        super(SQLReserved.WEIGHT_STRING.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    @Override
    public AbstractSQLFunction clone() {
        SQLWeightStringFunction x = new SQLWeightStringFunction();
        return x;
    }

    public ISQLDataType getDataType() {
        return dataType;
    }

    public void setDataType(ISQLDataType dataType) {
        setChildParent(dataType);
        this.dataType = dataType;
    }
}
