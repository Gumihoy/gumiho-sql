package com.gumihoy.sql.basic.ast.expr.table.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ENABLE CONTAINER_MAP
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 * @author kent on 2018/7/17.
 */
public class SQLAlterTableEnableContainerMapAction extends AbstractSQLExpr implements ISQLAlterTableAction {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public SQLAlterTableEnableContainerMapAction clone() {
        return new SQLAlterTableEnableContainerMapAction();
    }
}
