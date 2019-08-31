package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.sequence.alter.ISQLAlterSequenceAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#sequence%20generator%20increment%20by%20option
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLSequenceIncrementByOption extends AbstractSQLExpr implements ISQLCreateSequenceOption, ISQLAlterSequenceAction {

    protected boolean by = true;

    protected ISQLExpr value;

    public SQLSequenceIncrementByOption(ISQLExpr value) {
        setValue(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLSequenceIncrementByOption clone() {
        ISQLExpr valueClone = this.value.clone();
        SQLSequenceIncrementByOption x = new SQLSequenceIncrementByOption(valueClone);
        x.setBy(this.by);
        return x;
    }

    public boolean isBy() {
        return by;
    }

    public void setBy(boolean by) {
        this.by = by;
    }


    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
    }
}
