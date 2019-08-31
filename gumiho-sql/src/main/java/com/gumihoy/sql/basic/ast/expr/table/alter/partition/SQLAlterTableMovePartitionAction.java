package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * MOVE PARTITION nameIdentifier
 * [ MAPPING TABLE ]
 * [ table_partition_description ]
 * [ filter_condition]
 * [ update_index_clauses ]
 * [ parallel_clause ]
 * [ allow_disallow_clustering ]
 * [ ONLINE ]

 * <p>
 * move_table_partition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableMovePartitionAction extends AbstractSQLMovePartitionAction implements ISQLAlterTablePartitionAction {

    protected ISQLExpr name;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLAlterTableMovePartitionAction clone() {
        SQLAlterTableMovePartitionAction x = new SQLAlterTableMovePartitionAction();
        this.cloneTo(x);
//        for (ISQLElement item : this.items) {
//            ISQLExpr itemClone = item.clone();
//            x.addItem(itemClone);
//        }
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

}
