package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#sequence%20generator%20start%20with%20option
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLSequenceStartWithOption extends AbstractSQLExpr implements ISQLCreateSequenceOption {

    protected boolean with = true;
    protected ISQLExpr value;

    public SQLSequenceStartWithOption(ISQLExpr value) {
        setValue(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLSequenceStartWithOption clone() {
        ISQLExpr valueClone = this.value.clone();
        SQLSequenceStartWithOption x = new SQLSequenceStartWithOption(valueClone);
        x.setWith(this.with);

        return x;
    }

    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
    }

    public boolean isWith() {
        return with;
    }

    public void setWith(boolean with) {
        this.with = with;
    }
}
