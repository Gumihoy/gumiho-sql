package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.sequence.alter.ISQLAlterSequenceAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-SEQUENCE.html#GUID-E9C78A8C-615A-4757-B2A8-5E6EFB130571
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLSequenceNoOrderOption extends AbstractSQLExpr implements ISQLCreateSequenceOption, ISQLAlterSequenceAction {


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLSequenceNoOrderOption clone() {
        return new SQLSequenceNoOrderOption();
    }
}
