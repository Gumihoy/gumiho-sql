package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DROP PRIMARY KEY  [ CASCADE ] [ { KEEP | DROP } INDEX ] [ ONLINE ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableDropPrimaryKeyConstraintAction extends AbstractSQLAlterTableDropConstraintAction {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLAlterTableDropPrimaryKeyConstraintAction clone() {
        SQLAlterTableDropPrimaryKeyConstraintAction x = new SQLAlterTableDropPrimaryKeyConstraintAction();
        this.cloneTo(x);

        return x;
    }

}
