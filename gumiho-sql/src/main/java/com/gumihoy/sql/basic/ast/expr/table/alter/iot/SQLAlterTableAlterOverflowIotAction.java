package com.gumihoy.sql.basic.ast.expr.table.alter.iot;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * OVERFLOW { segment_attributes_clause| allocate_extent_clause| shrink_clause| deallocate_unused_clause}...
 * <p>
 * alter_overflow_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/17.
 */
public class SQLAlterTableAlterOverflowIotAction extends AbstractSQLExpr implements ISQLAlterTableIotAction {

    protected final List<ISQLExpr> items = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, items);
//        }
    }

    @Override
    public SQLAlterTableAlterOverflowIotAction clone() {
        SQLAlterTableAlterOverflowIotAction x = new SQLAlterTableAlterOverflowIotAction();
        return x;
    }


    public List<ISQLExpr> getItems() {
        return items;
    }

    public void addItem(ISQLExpr item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }
}
