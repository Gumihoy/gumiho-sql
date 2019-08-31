package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SUBSTR(str FROM pos)
 * SUBSTR(str FROM pos FOR len)
 * <p>
 * https://dev.mysql.com/doc/refman/8.0/en/string-functions.html
 *
 * @author kent on 2018/7/25.
 */
public class SQLSubStrFromFunction extends AbstractSQLFunction {

    public SQLSubStrFromFunction() {
//        super(SQLReserved.SUBSTR.ofExpr());
    }

    public SQLSubStrFromFunction(ISQLExpr str, ISQLExpr pos) {
//        super(SQLReserved.SUBSTR.ofExpr());
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
        SQLSubStrFromFunction x = new SQLSubStrFromFunction();
        return x;
    }

}
