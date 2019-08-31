package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * xxx [xxx, xxx]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Model-Expressions.html#GUID-83D3FD56-8346-4D3F-A49E-5FE41FE19257
 *
 * @author kent on 2018/5/16.
 */
public class SQLArrayExpr extends AbstractSQLExpr {

    protected ISQLExpr name;

    protected final List<ISQLExpr> arguments = new ArrayList<>();

    @Override
    public SQLArrayExpr clone() {
        SQLArrayExpr x = new SQLArrayExpr();
        this.cloneTo(x);

        ISQLExpr nameClone = this.name.clone();
        x.setName(nameClone);

        for (ISQLExpr argument : arguments) {
            ISQLExpr argumentClone = argument.clone();
            x.addArgument(argumentClone);
        }
        return x;
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, arguments);
//        }
    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }

    public List<ISQLExpr> getArguments() {
        return arguments;
    }

    public void addArgument(ISQLExpr argument) {
        if (argument == null) {
            return;
        }
        setChildParent(argument);
        this.arguments.add(argument);
    }
}
