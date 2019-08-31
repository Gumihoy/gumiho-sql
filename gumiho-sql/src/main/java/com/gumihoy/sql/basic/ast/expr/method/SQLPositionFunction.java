package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * POSITION(substr IN str)
 * https://dev.mysql.com/doc/refman/8.0/en/string-functions.html
 *
 * @author kent on 2018/7/24.
 */
public class SQLPositionFunction extends AbstractSQLFunction {

    public SQLPositionFunction() {
//        super(SQLReserved.POSITION.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    @Override
    public SQLPositionFunction clone() {
        SQLPositionFunction x = new SQLPositionFunction();
        return x;
    }


}
