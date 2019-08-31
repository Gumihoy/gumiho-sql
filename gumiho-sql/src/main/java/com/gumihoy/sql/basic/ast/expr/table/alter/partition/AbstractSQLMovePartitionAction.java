package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.ISQLUpdateIndexClause;

import java.util.ArrayList;
import java.util.List;

/**

 * MOVE <PARTITION nameIdentifier | PARTITION FOR LEFT_PAREN nameIdentifier (COMMA nameIdentifier)* RIGHT_PAREN>
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
 * @author kent on 2018/7/12.
 */
public abstract class AbstractSQLMovePartitionAction extends AbstractSQLExpr implements ISQLAlterTablePartitionAction {

    protected boolean mappingTable;
    protected final List<ISQLExpr> properties = new ArrayList<>();
//    protected SQLFilterCondition filterCondition;
    protected ISQLUpdateIndexClause updateIndexClause;
//    protected ISQLParallelClause parallelClause;
//    protected SQLAllowDisallowClusteringType allowDisallowClustering;
    protected boolean online;

    @Override
    public AbstractSQLMovePartitionAction clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }


    public boolean isMappingTable() {
        return mappingTable;
    }

    public void setMappingTable(boolean mappingTable) {
        this.mappingTable = mappingTable;
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

//    public SQLFilterCondition getFilterCondition() {
//        return filterCondition;
//    }
//
//    public void setFilterCondition(SQLFilterCondition filterCondition) {
//        this.filterCondition = filterCondition;
//    }

    public ISQLUpdateIndexClause getUpdateIndexClause() {
        return updateIndexClause;
    }

    public void setUpdateIndexClause(ISQLUpdateIndexClause updateIndexClause) {
        this.updateIndexClause = updateIndexClause;
    }

//    public ISQLParallelClause getParallelClause() {
//        return parallelClause;
//    }
//
//    public void setParallelClause(ISQLParallelClause parallelClause) {
//        this.parallelClause = parallelClause;
//    }

//    public SQLAllowDisallowClusteringType getAllowDisallowClustering() {
//        return allowDisallowClustering;
//    }
//
//    public void setAllowDisallowClustering(SQLAllowDisallowClusteringType allowDisallowClustering) {
//        this.allowDisallowClustering = allowDisallowClustering;
//    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
