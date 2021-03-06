package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * SET STORE IN ( tablespace [, tablespace ]... )
 * <p>
 * alter_automatic_partitioning
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableSetStoreInAction extends AbstractSQLExpr implements ISQLAlterTablePartitionAction {

    protected final List<ISQLExpr> items = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, items);
//        }
    }

    @Override
    public SQLAlterTableSetStoreInAction clone() {
        SQLAlterTableSetStoreInAction x = new SQLAlterTableSetStoreInAction();
        this.cloneTo(x);
        for (ISQLExpr item : this.items) {
            ISQLExpr itemClone = item.clone();
            x.addItem(itemClone);
        }
        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = replaceInList(items, source, target, this);
        if (replace) {
            return true;
        }
        return false;
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
