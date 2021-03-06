package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DROP COLUMNS CONTINUE [ CHECKPOINT integer ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableDropColumnsContinueAction extends AbstractSQLAlterTableDropColumnAction implements ISQLAlterTableDropColumnAction {


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, checkPoint);
        }
    }

    @Override
    public SQLAlterTableDropColumnsContinueAction clone() {
        SQLAlterTableDropColumnsContinueAction x = new SQLAlterTableDropColumnsContinueAction();
        this.cloneTo(x);
        return x;
    }

}
