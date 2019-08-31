package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.sequence.alter.ISQLAlterSequenceAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * NO CYCLE
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#sequence%20generator%20cycle%20option
 * <p>
 * NOCYCLE
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-SEQUENCE.html#GUID-E9C78A8C-615A-4757-B2A8-5E6EFB130571
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLSequenceNoCycleOption extends AbstractSQLExpr implements ISQLCreateSequenceOption, ISQLAlterSequenceAction {


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLSequenceNoCycleOption clone() {
        return new SQLSequenceNoCycleOption();
    }
}
