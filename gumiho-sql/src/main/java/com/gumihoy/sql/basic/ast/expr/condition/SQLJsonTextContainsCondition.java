package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON_TEXTCONTAINS( column, JSON_basic_path_expression, string )
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SQL-JSON-Conditions.html#GUID-99B9493D-2929-4A09-BA39-A56F8E7319DA
 *
 * @author kent on 2018/5/11.
 */
public class SQLJsonTextContainsCondition extends AbstractSQLExpr implements ISQLCondition {

    protected final List<ISQLExpr> arguments = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    @Override
    public SQLJsonTextContainsCondition clone() {
        SQLJsonTextContainsCondition x = new SQLJsonTextContainsCondition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLJsonTextContainsCondition x) {
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
