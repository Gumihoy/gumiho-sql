package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * MOVE SUBPARTITION nameIdentifier indexingClause? partitioningStorageClause?
 * updateIndexClause? filterCondition? parallelClause? allowDisallowClustering? ONLINE?
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableMoveSubPartitionAction extends AbstractSQLMoveSubPartitionAction implements ISQLAlterTableAction {

    protected ISQLName name;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLAlterTableMoveSubPartitionAction clone() {
        SQLAlterTableMoveSubPartitionAction x = new SQLAlterTableMoveSubPartitionAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableMoveSubPartitionAction x) {
        super.cloneTo(x);

        ISQLName nameClone = name.clone();
        x.setName(nameClone);

    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }
}
