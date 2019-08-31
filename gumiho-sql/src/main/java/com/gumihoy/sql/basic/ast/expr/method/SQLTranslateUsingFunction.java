package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * TRANSLATE ( char USING  usingType={CHAR_CS | NCHAR_CS })
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/TRANSLATE-USING.html#GUID-EC8DE4D2-4F24-456D-A2E7-AD8F82E3A148
 *
 * @author kent on 2018/4/28.
 */
public class SQLTranslateUsingFunction extends AbstractSQLFunction {

    protected ISQLExpr usingValue;

    public SQLTranslateUsingFunction() {
    }

    public SQLTranslateUsingFunction(ISQLExpr argument) {
        this.addArgument(argument);
    }

    public SQLTranslateUsingFunction(ISQLExpr argument, ISQLExpr usingValue) {
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
