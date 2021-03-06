package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * MODIFY PRIMARY KEY constraint_state [ CASCADE ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableModifyPrimaryKeyConstraintAction extends AbstractSQLAlterTableModifyTableConstraintAction {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, options);
        }
    }

    @Override
    public SQLAlterTableModifyPrimaryKeyConstraintAction clone() {
        SQLAlterTableModifyPrimaryKeyConstraintAction x = new SQLAlterTableModifyPrimaryKeyConstraintAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableModifyPrimaryKeyConstraintAction x) {
        super.cloneTo(x);
    }

}
