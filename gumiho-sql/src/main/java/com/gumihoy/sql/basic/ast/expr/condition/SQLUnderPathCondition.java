package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * UNDER_PATH (column [, levels ], path_string [, correlation_integer ])
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/XML-Conditions.html#GUID-51E593FF-9AB0-4E1F-ABF7-5330F82FC0AE
 *
 * @author kent on 2018/5/11.
 */
public class SQLUnderPathCondition extends AbstractSQLExpr implements ISQLCondition{

    protected final List<ISQLExpr> arguments = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    @Override
    public SQLUnderPathCondition clone() {
        SQLUnderPathCondition x = new SQLUnderPathCondition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLUnderPathCondition x) {
        super.cloneTo(x);

        for (ISQLExpr argument : this.arguments) {
            ISQLExpr argumentClone = argument.clone();
            x.addArgument(argumentClone);
        }
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
