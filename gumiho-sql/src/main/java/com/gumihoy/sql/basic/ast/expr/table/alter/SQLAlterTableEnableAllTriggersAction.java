package com.gumihoy.sql.basic.ast.expr.table.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ENABLE ALL TRIGGERSalterTableEnableDisable
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 * @author kent on 2018/7/17.
 */
public class SQLAlterTableEnableAllTriggersAction extends AbstractSQLExpr implements ISQLAlterTableAction {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public SQLAlterTableEnableAllTriggersAction clone() {
        return new SQLAlterTableEnableAllTriggersAction();
    }
}
