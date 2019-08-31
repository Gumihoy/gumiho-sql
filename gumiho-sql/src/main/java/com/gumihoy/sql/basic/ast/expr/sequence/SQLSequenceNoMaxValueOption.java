package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.sequence.alter.ISQLAlterSequenceAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#sequence%20generator%20maxvalue%20option
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLSequenceNoMaxValueOption extends AbstractSQLExpr implements ISQLCreateSequenceOption, ISQLAlterSequenceAction {


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLSequenceNoMaxValueOption clone() {
        return new SQLSequenceNoMaxValueOption();
    }
}
