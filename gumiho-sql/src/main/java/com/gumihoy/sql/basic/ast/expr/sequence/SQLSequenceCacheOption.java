package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLIntegerLiteral;
import com.gumihoy.sql.basic.ast.expr.sequence.alter.ISQLAlterSequenceAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#sequence%20generator%20cycle%20option
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLSequenceCacheOption extends AbstractSQLExpr implements ISQLCreateSequenceOption, ISQLAlterSequenceAction {

    protected ISQLExpr value;

    public SQLSequenceCacheOption(ISQLExpr value) {
        setValue(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLSequenceCacheOption clone() {
        ISQLExpr valueClone = this.value.clone();
        SQLSequenceCacheOption x = new SQLSequenceCacheOption(valueClone);
        return x;
    }

    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        this.value = value;
    }
}
