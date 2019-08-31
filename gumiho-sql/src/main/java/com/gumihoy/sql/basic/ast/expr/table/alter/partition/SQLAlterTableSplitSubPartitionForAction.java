package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * SPLIT SUBPARTITION FOR LEFT_PAREN nameIdentifier (COMMA nameIdentifier)* RIGHT_PAREN
 * iAlterTableSplitPartitionActionItem splitNestedTablePart? filterCondition? dependentTablesClause?
 * updateIndexClause? parallelClause? allowDisallowClustering? ONLINE?
 * <p>
 * split_table_subpartition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableSplitSubPartitionForAction extends AbstractSQLAlterTableSplitSubPartitionAction {

    protected final List<ISQLExpr> names = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, names);
            this.acceptChild(visitor, item);
        }
    }

    @Override
    public SQLAlterTableSplitSubPartitionForAction clone() {
        SQLAlterTableSplitSubPartitionForAction x = new SQLAlterTableSplitSubPartitionForAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableSplitSubPartitionForAction x) {
        super.cloneTo(x);


    }

    public List<ISQLExpr> getNames() {
        return names;
    }

    public void addName(ISQLExpr name) {
        if (name == null) {
            return;
        }
        setChildParent(name);
        this.names.add(name);
    }

}
