package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * [<< label >>] statement
 * @author kent on 2019-07-27.
 */
public class SQLLabelStatement extends AbstractSQLExpr {

    protected final List<SQLLabel> labels = new ArrayList<>();
    protected ISQLObject statement;

    public SQLLabelStatement() {
    }

    public SQLLabelStatement(ISQLObject statement) {
        setStatement(statement);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, labels);
            this.acceptChild(visitor, statement);
        }
    }


    @Override
    public SQLLabelStatement clone() {
        ISQLObject statementClone = this.statement.clone();
        SQLLabelStatement x = new SQLLabelStatement(statementClone);
        for (SQLLabel label : labels) {
            SQLLabel labelClone = label.clone();
            x.addLabel(labelClone);
        }
        return x;
    }

    public List<SQLLabel> getLabels() {
        return labels;
    }

    public void addLabel(SQLLabel label) {
        if (label == null) {
            return;
        }
        setChildParent(label);
        this.labels.add(label);
    }

    public ISQLObject getStatement() {
        return statement;
    }

    public void setStatement(ISQLObject statement) {
        setChildParent(statement);
        this.statement = statement;
    }

}
