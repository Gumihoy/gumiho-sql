package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * MODIFY PARTITION nameIdentifier
 * <p>
 * modify_table_partition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableModifySubPartitionAction extends AbstractSQLExpr {

    protected ISQLExpr name;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLAlterTableModifySubPartitionAction clone() {
        SQLAlterTableModifySubPartitionAction x = new SQLAlterTableModifySubPartitionAction();
        this.cloneTo(x);

        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            setName(target);
            return true;
        }

        return false;
    }


    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        this.name = name;
    }

//    public List<ISQLElement> getItems() {
//        return items;
//    }
//
//    public void addItem(ISQLElement item) {
//        if (item == null) {
//            return;
//        }
//        setChildParent(item);
//        this.items.add(item);
//    }

}
