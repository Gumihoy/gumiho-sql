package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLColumnDefinition;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * CHANGE [COLUMN] old_col_name column_definition [FIRST|AFTER col_name]
 * <p>
 * https://dev.mysql.com/doc/refman/5.6/en/alter-table.html
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableChangeColumnAction extends AbstractSQLExpr implements ISQLAlterTableColumnAction {

    protected boolean column;
    protected ISQLExpr name;
    protected SQLColumnDefinition columnDefinition;
    protected ISQLExpr property;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, columnDefinition);
//            this.acceptChild(visitor, property);
//        }
    }

    @Override
    public SQLAlterTableChangeColumnAction clone() {
        SQLAlterTableChangeColumnAction x = new SQLAlterTableChangeColumnAction();

        x.column = this.column;

        ISQLExpr nameClone = this.name.clone();
        x.setName(nameClone);

        SQLColumnDefinition columnDefinitionClone = this.columnDefinition.clone();
        x.setColumnDefinition(columnDefinitionClone);

        return x;
    }


    public boolean isColumn() {
        return column;
    }

    public void setColumn(boolean column) {
        this.column = column;
    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }

    public SQLColumnDefinition getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(SQLColumnDefinition columnDefinition) {
        setChildParent(columnDefinition);
        this.columnDefinition = columnDefinition;
    }

    public ISQLExpr getProperty() {
        return property;
    }

    public void setProperty(ISQLExpr property) {
        setChildParent(property);
        this.property = property;
    }
}
