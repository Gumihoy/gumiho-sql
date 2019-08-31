package com.gumihoy.sql.basic.ast.expr.table.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * {WITHOUT|WITH} VALIDATION
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableWithValidationAction extends AbstractSQLExpr implements ISQLAlterTableAction {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//        }
    }

    @Override
    public SQLAlterTableWithValidationAction clone() {
        SQLAlterTableWithValidationAction x = new SQLAlterTableWithValidationAction();
        return x;
    }

}
