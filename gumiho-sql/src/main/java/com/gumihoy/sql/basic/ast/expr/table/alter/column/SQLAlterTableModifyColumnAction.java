package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.SQLObjectTableSubstitution;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLColumnDefinition;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * MODIFY [COLUMN] col_name column_definition [FIRST | AFTER col_name]
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 *
 *
 * MODIFY COLUMN name substitution force?
 *
 * modify_column_clauses
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableModifyColumnAction extends AbstractSQLExpr implements ISQLAlterTableColumnAction {

    protected boolean column_;
    protected SQLColumnDefinition column;

    protected SQLObjectTableSubstitution substitution;

    protected boolean force;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, column);
//            this.acceptChild(visitor, substitution);
//        }
    }

    @Override
    public SQLAlterTableModifyColumnAction clone() {
        SQLAlterTableModifyColumnAction x = new SQLAlterTableModifyColumnAction();

        return x;
    }


    public boolean isColumn_() {
        return column_;
    }

    public void setColumn_(boolean column_) {
        this.column_ = column_;
    }

    public SQLColumnDefinition getColumn() {
        return column;
    }

    public void setColumn(SQLColumnDefinition column) {
        this.column = column;
    }

    public SQLObjectTableSubstitution getSubstitution() {
        return substitution;
    }

    public void setSubstitution(SQLObjectTableSubstitution substitution) {
        this.substitution = substitution;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }
}
