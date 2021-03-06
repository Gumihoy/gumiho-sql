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
public class SQLAlterTableAddColumnAction extends AbstractSQLExpr implements ISQLAlterTableColumnAction {

    protected boolean column;

    protected boolean ifNotExists;

    protected boolean paren = false;
    protected final List<ISQLColumnDefinition> columns = new ArrayList<>();

    protected final List<ISQLExpr> properties = new ArrayList<>();

    protected final List<SQLPartitionDefinition> partitions = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, properties);
            this.acceptChild(visitor, partitions);
        }
    }

    @Override
    public SQLAlterTableAddColumnAction clone() {
        return null;
    }

    public boolean isColumn() {
        return column;
    }

    public void setColumn(boolean column) {
        this.column = column;
    }

    public boolean isIfNotExists() {
        return ifNotExists;
    }

    public void setIfNotExists(boolean ifNotExists) {
        this.ifNotExists = ifNotExists;
    }

    public boolean isParen() {
        return paren;
    }

    public void setParen(boolean paren) {
        this.paren = paren;
    }

    public List<ISQLColumnDefinition> getColumns() {
        return columns;
    }

    public void addColumn(ISQLColumnDefinition column) {
        if (column == null) {
            return;
        }
        setChildParent(column);
        this.columns.add(column);
    }

    public List<ISQLExpr> getProperties() {
        return properties;
    }

    public void addProperty(ISQLExpr property) {
        if (property == null) {
            return;
        }
        setChildParent(property);
        this.properties.add(property);
    }

    public List<SQLPartitionDefinition> getPartitions() {
        return partitions;
    }

    public void addPartition(SQLPartitionDefinition partition) {
        if (partition == null) {
            return;
        }
        setChildParent(partition);
        this.partitions.add(partition);
    }

}
