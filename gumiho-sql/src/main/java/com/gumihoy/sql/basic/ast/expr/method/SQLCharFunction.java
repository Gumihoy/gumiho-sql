package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * CHAR(N,... [USING charset_name])
 * https://dev.mysql.com/doc/refman/8.0/en/string-functions.html
 *
 * @author kent on 2018/7/24.
 */
public class SQLCharFunction extends AbstractSQLFunction {

//    protected SQLCharsetNameType charsetName;

    public SQLCharFunction() {
//        super(SQLReserved.CHAR.ofExpr());
    }

    public SQLCharFunction(ISQLExpr... arguments) {
//        super(SQLReserved.CHAR.ofExpr());
        for (ISQLExpr argument : arguments) {
            this.addArgument(argument);
        }
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    @Override
    public SQLCharFunction clone() {
        SQLCharFunction x = new SQLCharFunction();
        this.cloneTo(x);
//        x.charsetName = this.charsetName;
        return x;
    }


//    public SQLCharsetNameType getCharsetName() {
//        return charsetName;
//    }
//
//    public void setCharsetName(SQLCharsetNameType charsetName) {
//        this.charsetName = charsetName;
//    }
}
