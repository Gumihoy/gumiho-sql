package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * arg1 => 3
 * <p>
 * https://docs.oracle.com/cd/B28359_01/server.111/b28286/statements_4008.htm#SQLRF01108
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CALL.html#GUID-6CD7B9C4-E5DC-4F3C-9B6A-876AD2C63545
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/expression.html#GUID-D4700B45-F2C8-443E-AEE7-2BD20FFD45B8
 *
 * @author kent on 2018/5/16.
 */
public class SQLCallExpr extends AbstractSQLExpr {

    protected ISQLExpr name;
    protected ISQLExpr value;


    public SQLCallExpr() {
    }

    public SQLCallExpr(ISQLExpr name, ISQLExpr value) {
        setName(name);
        setValue(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, value);
//        }
    }

    @Override
    public SQLCallExpr clone() {
        SQLCallExpr x = new SQLCallExpr();

        ISQLExpr nameClone = this.name.clone();
        x.setName(nameClone);

        ISQLExpr valueClone = this.value.clone();
        x.setValue(valueClone);
        return x;
    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
    }
}
