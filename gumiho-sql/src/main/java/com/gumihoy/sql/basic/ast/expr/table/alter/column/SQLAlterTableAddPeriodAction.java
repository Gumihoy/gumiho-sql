package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.element.ISQLColumnDefinition;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionDefinition;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * ADD [ COLUMN ] [ IF NOT EXISTS ] ( <column definition> ,... )
 * [FIRST | AFTER col_name]
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#add%20column%20definition
 * <p>
 * ADD [COLUMN] column_definition [FIRST | AFTER col_name]
 * ADD [COLUMN] (column_definition,...)
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 * <p>
 * <p>
 * ADD [ COLUMN ] [ IF NOT EXISTS ] column_name data_type [ COLLATE collation ] [ column_constraint [ ... ] ]
 * https://www.postgresql.org/docs/devel/static/sql-altertable.html
 * <p>
 * <p>
 * ADD ( {column_definition | virtual_column_definition [, column_definition | virtual_column_definition] ... } ) [ column_properties ] [ ( out_of_line_part_storage [, out_of_line_part_storage]... ) ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableAddPeriodAction extends AbstractSQLExpr implements ISQLAlterTableColumnAction {

    protected ISQLExpr column;
    protected ISQLExpr startColumn;
    protected ISQLExpr endColumn;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, column);
            this.acceptChild(visitor, startColumn);
            this.acceptChild(visitor, endColumn);
        }
    }

    @Override
    public SQLAlterTableAddPeriodAction clone() {
        return null;
    }

    public ISQLExpr getColumn() {
        return column;
    }

    public void setColumn(ISQLExpr column) {
        this.column = column;
    }

    public ISQLExpr getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(ISQLExpr startColumn) {
        this.startColumn = startColumn;
    }

    public ISQLExpr getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(ISQLExpr endColumn) {
        this.endColumn = endColumn;
    }
}
