package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * MODIFY UNIQUE (column [, column ]...) constraint_state [ CASCADE ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableModifyUniqueConstraintAction extends AbstractSQLAlterTableModifyTableConstraintAction {

    protected final List<ISQLExpr> columns = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, columns);
//            this.acceptChild(visitor, options);
//        }
    }

    @Override
    public SQLAlterTableModifyUniqueConstraintAction clone() {
        SQLAlterTableModifyUniqueConstraintAction x = new SQLAlterTableModifyUniqueConstraintAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableModifyUniqueConstraintAction x) {
        super.cloneTo(x);
        for (ISQLExpr column : this.columns) {
            ISQLExpr columnClone = column.clone();
            x.addColumn(columnClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = replaceInList(columns, source, target, this);
        if (replace) {
            return true;
        }
        return false;
    }

    public List<ISQLExpr> getColumns() {
        return columns;
    }

    public void addColumn(ISQLExpr column) {
        if (column == null) {
            return;
        }
        setChildParent(column);
        this.columns.add(column);
    }

}
