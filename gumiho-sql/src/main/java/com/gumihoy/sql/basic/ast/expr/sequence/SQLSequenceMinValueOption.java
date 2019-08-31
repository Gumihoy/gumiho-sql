package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.sequence.alter.ISQLAlterSequenceAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#sequence%20generator%20minvalue%20option
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLSequenceMinValueOption extends AbstractSQLExpr implements ISQLCreateSequenceOption, ISQLAlterSequenceAction {

    protected ISQLExpr value;

    public SQLSequenceMinValueOption(ISQLExpr value) {
        setValue(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLSequenceMinValueOption clone() {
        ISQLExpr valueClone = this.value.clone();
        SQLSequenceMinValueOption x = new SQLSequenceMinValueOption(valueClone);
        x.setValue(valueClone);
        return x;
    }

    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        this.value = value;
    }
}
