package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * CHR(n usingType=[ USING NCHAR_CS ])
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CHR.html#GUID-35FEE007-D49C-4562-A904-041186AC8928
 *
 * @author kent on 2018/4/28.
 */
public class SQLChrFunction extends AbstractSQLFunction {

    protected ISQLExpr usingValue;

    public SQLChrFunction() {
    }

    public SQLChrFunction(ISQLExpr argument) {
        this.addArgument(argument);
    }

    public SQLChrFunction(ISQLExpr argument, ISQLExpr usingValue) {
        this.addArgument(argument);
        this.setUsingValue(usingValue);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, arguments);
        }
    }


    public ISQLExpr getUsingValue() {
        return usingValue;
    }

    public void setUsingValue(ISQLExpr usingValue) {
        setChildParent(usingValue);
        this.usingValue = usingValue;
    }
}
