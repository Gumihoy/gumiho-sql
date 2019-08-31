package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.sequence.alter.ISQLAlterSequenceAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#sequence%20generator%20maxvalue%20option
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLSequenceMaxValueOption extends AbstractSQLExpr implements ISQLCreateSequenceOption, ISQLAlterSequenceAction {

    protected ISQLExpr value;

    public SQLSequenceMaxValueOption(ISQLExpr value) {
        setValue(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLSequenceMaxValueOption clone() {
        ISQLExpr valueClone = this.value.clone();
        SQLSequenceMaxValueOption x = new SQLSequenceMaxValueOption(valueClone);
        return x;
    }

    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
    }
}
