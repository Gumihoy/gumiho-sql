package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SUBSTRING(str FROM pos)
 * SUBSTRING(str FROM pos FOR len)
 *
 * https://dev.mysql.com/doc/refman/8.0/en/string-functions.html
 * @author kent on 2018/7/25.
 */
public class SQLSubStringFromFunction extends AbstractSQLFunction {


    public SQLSubStringFromFunction() {
//        super(SQLReserved.SUBSTRING.ofExpr());
    }

    public SQLSubStringFromFunction(ISQLExpr str, ISQLExpr pos) {
//        super(SQLReserved.SUBSTRING.ofExpr());
        this.addArgument(str);
        this.addArgument(pos);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    @Override
    public AbstractSQLFunction clone() {

        SQLSubStringFromFunction x = new SQLSubStringFromFunction();
        return x;
    }

}
