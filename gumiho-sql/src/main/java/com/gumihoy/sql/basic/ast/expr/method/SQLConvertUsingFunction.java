package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * CONVERT(expr USING transcoding_name)
 * https://dev.mysql.com/doc/refman/8.0/en/cast-functions.html#function_convert
 *
 * @author kent on 2018/7/24.
 */
public class SQLConvertUsingFunction extends AbstractSQLFunction {

    protected ISQLName coding;

    public SQLConvertUsingFunction() {
//        super(SQLReserved.CONVERT.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    @Override
    public AbstractSQLFunction clone() {
        return super.clone();
    }

    public ISQLName getCoding() {
        return coding;
    }

    public void setCoding(ISQLName coding) {
        this.coding = coding;
    }
}
