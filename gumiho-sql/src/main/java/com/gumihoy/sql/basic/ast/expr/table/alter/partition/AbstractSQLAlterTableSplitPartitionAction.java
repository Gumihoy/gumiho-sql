package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionDefinition;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * split_table_partition
 * split_table_subpartition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/12.
 */
public abstract class AbstractSQLAlterTableSplitPartitionAction extends AbstractSQLExpr implements ISQLAlterTablePartitionAction {

    protected SQLItem item;
    protected SQLSplitNestedTablePart splitNestedTablePart;
//    protected SQLFilterCondition filterCondition;
//    protected SQLDependentTablesClause dependentTables;
//    protected ISQLUpdateIndexClause updateIndex;
//    protected ISQLParallelClause parallel;
//    protected SQLAllowDisallowClusteringType allowDisallowClustering;
    protected boolean online;

    @Override
    public AbstractSQLAlterTableSplitPartitionAction clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLAlterTableSplitPartitionAction x) {
        super.cloneTo(x);

    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        return false;
    }


    public SQLItem getItem() {
        return item;
    }

    public void setItem(SQLItem item) {
        this.item = item;
    }

    public SQLSplitNestedTablePart getSplitNestedTablePart() {
        return splitNestedTablePart;
    }

    public void setSplitNestedTablePart(SQLSplitNestedTablePart splitNestedTablePart) {
        this.splitNestedTablePart = splitNestedTablePart;
    }

//    public SQLFilterCondition getFilterCondition() {
//        return filterCondition;
//    }
//
//    public void setFilterCondition(SQLFilterCondition filterCondition) {
//        this.filterCondition = filterCondition;
//    }
//
//    public SQLDependentTablesClause getDependentTables() {
//        return dependentTables;
//    }
//
//    public void setDependentTables(SQLDependentTablesClause dependentTables) {
//        setChildParent(dependentTables);
//        this.dependentTables = dependentTables;
//    }
//
//    public ISQLUpdateIndexClause getUpdateIndex() {
//        return updateIndex;
//    }
//
//    public void setUpdateIndex(ISQLUpdateIndexClause updateIndex) {
//        setChildParent(updateIndex);
//        this.updateIndex = updateIndex;
//    }
//
//    public ISQLParallelClause getParallel() {
//        return parallel;
//    }
//
//    public void setParallel(ISQLParallelClause parallel) {
//        setChildParent(parallel);
//        this.parallel = parallel;
//    }
//
    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
//
//    public SQLAllowDisallowClusteringType getAllowDisallowClustering() {
//        return allowDisallowClustering;
//    }
//
//    public void setAllowDisallowClustering(SQLAllowDisallowClusteringType allowDisallowClustering) {
//        this.allowDisallowClustering = allowDisallowClustering;
//    }


    public interface SQLItem extends ISQLExpr {
        @Override
        SQLItem clone();
    }

    /**
     * AT (literal [, literal]... ) [ INTO ( range_partition_desc, range_partition_desc ) ]
     * | VALUES ( list_values ) [ INTO ( list_partition_desc, list_partition_desc ) ]
     * | INTO ( { range_partition_desc [, range_partition_desc ]...| list_partition_desc [, list_partition_desc ]... }, partition_spec )
     */
    public static abstract class AbstractSQLItem extends AbstractSQLExpr implements SQLItem {

        protected final List<SQLPartitionDefinition> partitions = new ArrayList<>();

        @Override
        public AbstractSQLItem clone() {
            throw new UnsupportedOperationException(this.getClass().getName());
        }

        public void cloneTo(AbstractSQLItem x) {
            super.cloneTo(x);
            for (SQLPartitionDefinition partition : partitions) {
                SQLPartitionDefinition partitionClone = partition.clone();
                x.addPartition(partitionClone);
            }
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

    /**
     * AT (literal [, literal]... )
     * [ INTO ( range_partition_desc, range_partition_desc ) ]
     */
    public static class SQLAt extends AbstractSQLItem {

        protected final List<ISQLExpr> items = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, items);
//                this.acceptChild(visitor, partitions);
//            }
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

    /**
     * VALUES (expr [, expr]... )
     * [ INTO ( range_partition_desc, range_partition_desc ) ]
     */
    public static class SQLValues extends AbstractSQLItem {

        protected final List<ISQLExpr> items = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, items);
//                this.acceptChild(visitor, partitions);
//            }
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

    /**
     * INTO ( range_partition_desc, range_partition_desc )
     */
    public static class SQLInto extends AbstractSQLItem {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, partitions);
//            }
        }
    }


    /**
     * NESTED TABLE column INTO ( nested_table_partition_spec, nested_table_partition_spec [split_nested_table_part] ) [split_nested_table_part]
     * <p>
     * split_nested_table_part
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
     */
    public static class SQLSplitNestedTablePart extends AbstractSQLExpr {

        protected ISQLExpr column;
        protected final List<ISQLExpr> items = new ArrayList<>();
        protected SQLSplitNestedTablePart splitNestedTablePart;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, column);
//                this.acceptChild(visitor, items);
//                this.acceptChild(visitor, splitNestedTablePart);
//            }
        }

        public ISQLExpr getColumn() {
            return column;
        }

        public void setColumn(ISQLExpr column) {
            setChildParent(column);
            this.column = column;
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

        public SQLSplitNestedTablePart getSplitNestedTablePart() {
            return splitNestedTablePart;
        }

        public void setSplitNestedTablePart(SQLSplitNestedTablePart splitNestedTablePart) {
            setChildParent(splitNestedTablePart);
            this.splitNestedTablePart = splitNestedTablePart;
        }
    }
}
