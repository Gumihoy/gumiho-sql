package com.gumihoy.sql.basic.ast.expr.table.alter.iot;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * alter_iot_clauses
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/11.
 */
public interface ISQLAlterTableIotAction extends ISQLAlterTableAction {
    @Override
    ISQLAlterTableIotAction clone();


    /**
     * COALESCE
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
     */
    class SQLAlterTableCoalesceIotAction extends AbstractSQLExpr implements ISQLAlterTableIotAction {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLAlterTableCoalesceIotAction clone() {
            return new SQLAlterTableCoalesceIotAction();
        }
    }

}
