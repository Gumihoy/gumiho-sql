package com.gumihoy.sql.basic.ast.expr.table.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.SQLUpdateIndexesClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * MOVE [ filter_condition ] [ ONLINE ] [ segment_attributes_clause ] [ table_compression ] [ index_org_table_clause ] [ { LOB_storage_clause | varray_col_properties }... ] [ parallel_clause ] [ allow_disallow_clustering ] [ UPDATE INDEXES [ ( index { segment_attributes_clause | update_index_partition } [, index { segment_attributes_clause | update_index_partition } ]... ) ] ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/11.
 */
public class SQLAlterTableMoveTableAction extends AbstractSQLExpr implements ISQLAlterTableAction {

//    protected SQLFilterCondition filterCondition;
    protected boolean online;
    protected final List<ISQLExpr> properties = new ArrayList<>();
    protected SQLUpdateIndexesClause updateIndexes;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, filterCondition);
//            this.acceptChild(visitor, properties);
//            this.acceptChild(visitor, updateIndexes);
//        }
    }

    @Override
    public SQLAlterTableMoveTableAction clone() {
        SQLAlterTableMoveTableAction x = new SQLAlterTableMoveTableAction();
        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        return false;
    }

//    public SQLFilterCondition getFilterCondition() {
//        return filterCondition;
//    }
//
//    public void setFilterCondition(SQLFilterCondition filterCondition) {
//        setChildParent(filterCondition);
//        this.filterCondition = filterCondition;
//    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
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

    public SQLUpdateIndexesClause getUpdateIndexes() {
        return updateIndexes;
    }

    public void setUpdateIndexes(SQLUpdateIndexesClause updateIndexes) {
        setChildParent(updateIndexes);
        this.updateIndexes = updateIndexes;
    }


    /**
     * index { segment_attributes_clause | update_index_partition }
     */
    public static class SQLUpdateIndexItem extends AbstractSQLExpr {

        protected ISQLExpr name;
        protected final List<ISQLExpr> properties = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLAlterTableMoveTableAction clone() {
            return null;
        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            this.name = name;
        }

        public List<ISQLExpr> getProperties() {
            return properties;
        }

        public void addName(ISQLName property) {
            if (property == null) {
                return;
            }
            setChildParent(property);
            this.properties.add(property);
        }
    }
}
