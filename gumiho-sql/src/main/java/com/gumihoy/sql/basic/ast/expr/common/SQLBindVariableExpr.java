package com.gumihoy.sql.basic.ast.expr.common;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.util.SQLUtils;

/**
 * :xx
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Placeholder-Expressions.html#GUID-B98B5394-A573-4BF8-9EC3-7B1BB1130553
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLBindVariableExpr extends AbstractSQLExpr {

    protected ISQLIdentifier name;

    public SQLBindVariableExpr(String name) {
        setName(SQLUtils.ofName(removeColon(name)));
    }

    public SQLBindVariableExpr(ISQLIdentifier name) {
        setName(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLBindVariableExpr clone() {
        ISQLIdentifier nameClone = this.name.clone();
        SQLBindVariableExpr x = new SQLBindVariableExpr(nameClone);
        return x;
    }

    public ISQLIdentifier getName() {
        return name;
    }

    public void setName(ISQLIdentifier name) {
        setChildParent(name);
        this.name = name;
    }

    String removeColon(String name) {
        if (name.length() > 0
                && name.charAt(0) == ':') {
            name = name.substring(1);
        }
        return name;
    }
}
