package com.gumihoy.sql.basic.ast.expr.table.element.constraint.column;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.AbstractSQLConstraint;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * default xx
 *
 * @author kent on 2019-06-27.
 */
public class SQLDefaultColumnConstraint extends AbstractSQLConstraint implements ISQLColumnConstraint {


    protected ISQLExpr value;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {

    }

    @Override
    public SQLDefaultColumnConstraint clone() {
        return null;
    }


    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
    }
}
