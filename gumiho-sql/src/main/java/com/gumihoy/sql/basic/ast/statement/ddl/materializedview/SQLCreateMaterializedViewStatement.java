/*
 * Copyright (C) 2017-2018 kent(kent.bohai@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gumihoy.sql.basic.ast.statement.ddl.materializedview;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATE MATERIALIZED VIEW [ IF NOT EXISTS ] table_name [ (column_name [, ...] ) ] [ WITH ( storage_parameter [= value] [, ... ] ) ] [ TABLESPACE tablespace_name ] AS query [ WITH [ NO ] DATA ]
 * https://www.postgresql.org/docs/devel/static/sql-creatematerializedview.html
 * <p>
 * <p>
 * CREATE MATERIALIZED VIEW [ schema. ] materialized_view
 * [ OF [ schema. ] object_type ]
 * [ ( { scoped_table_ref_constraint
 * | column_alias [ENCRYPT [encryption_spec]]
 * }
 * [, { scoped_table_ref_constraint
 * | column_alias [ENCRYPT [encryption_spec]]
 * }
 * ]...
 * )
 * ]
 * [ DEFAULT COLLATION collation_name ]
 * { ON PREBUILT TABLE
 * [ { WITH | WITHOUT } REDUCED PRECISION ]
 * | physical_properties materialized_view_props
 * }
 * [ USING INDEX
 * [ physical_attributes_clause
 * | TABLESPACE tablespace
 * ]...
 * | USING NO INDEX
 * ]
 * [ create_mv_refresh ]
 * [ evaluation_edition_clause ]
 * [ { ENABLE | DISABLE } ON QUERY COMPUTATION ]
 * [ query_rewrite_clause ]
 * AS subquery ;
 * https://docs.oracle.com/cd/B28359_01/server.111/b28286/statements_6002.htm#SQLRF01302
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreateMaterializedViewStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean ifNotExists;
    protected ISQLName name;

    protected final List<ISQLExpr> columns = new ArrayList<>();

    protected boolean ofDataTypeInFrontOfColumn = true;
    protected ISQLDataType ofDataType;

    protected final List<ISQLExpr> columnConstraints = new ArrayList<>();

//    protected SQLCollationExpr collationExpr;

    protected final List<ISQLExpr> properties = new ArrayList<>();

//    protected ISQLUsingIndexClause usingIndex;

//    protected ISQLCreateMVRefresh createMVRefresh;

    protected boolean forUpdate;

//    protected SQLEvaluationEditionClause evaluationEditionClause;
//
//    protected SQLOnQueryComputationClause onQueryComputationClause;
//
//    protected SQLQueryRewriteClause queryRewriteClause;
//
//
//    protected SQLTablespaceOptionExpr tableSpaceClause;
//
//
//    protected ISQLSelectQuery asSubQuery;

    protected SQLWithDataType withDataType;


    public SQLCreateMaterializedViewStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, columns);
//            this.acceptChild(visitor, ofDataType);
//            this.acceptChild(visitor, columnConstraints);
//            this.acceptChild(visitor, properties);
//            this.acceptChild(visitor, usingIndex);
//            this.acceptChild(visitor, createMVRefresh);
//            this.acceptChild(visitor, evaluationEditionClause);
//            this.acceptChild(visitor, onQueryComputationClause);
//            this.acceptChild(visitor, queryRewriteClause);
//            this.acceptChild(visitor, asSubQuery);
//        }
    }


    @Override
    public SQLCreateMaterializedViewStatement clone() {
        SQLCreateMaterializedViewStatement x = new SQLCreateMaterializedViewStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCreateMaterializedViewStatement x) {
        super.cloneTo(x);

        x.ifNotExists = this.ifNotExists;


//        if (this.asSubQuery != null) {
//            ISQLSelectQuery asSubQueryClone = this.asSubQuery.clone();
//            x.setAsSubQuery(asSubQueryClone);
//        }

        x.withDataType = this.withDataType;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            this.setName((ISQLName) target);
            return true;
        }

        if (source == ofDataType
                && target instanceof ISQLDataType) {
            this.setOfDataType((ISQLDataType) target);
            return true;
        }

        return false;
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.MATERIALIZED_VIEW_CREATE;
    }


    public boolean isIfNotExists() {
        return ifNotExists;
    }

    public void setIfNotExists(boolean ifNotExists) {
        this.ifNotExists = ifNotExists;
    }

    public ISQLName getName() {
        return name;
    }

    public String getMaterializedViewName() {
        if (this.name == null) {
            return null;
        }
        return name.getSimpleName();
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
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

    public boolean isOfDataTypeInFrontOfColumn() {
        return ofDataTypeInFrontOfColumn;
    }

    public void setOfDataTypeInFrontOfColumn(boolean ofDataTypeInFrontOfColumn) {
        this.ofDataTypeInFrontOfColumn = ofDataTypeInFrontOfColumn;
    }

    public ISQLDataType getOfDataType() {
        return ofDataType;
    }

    public void setOfDataType(ISQLDataType ofDataType) {
        setChildParent(ofDataType);
        this.ofDataType = ofDataType;
    }


    public List<ISQLExpr> getColumnConstraints() {
        return columnConstraints;
    }

    public void addColumnConstraint(ISQLExpr columnConstraint) {
        if (columnConstraint == null) {
            return;
        }
        setChildParent(columnConstraint);
        this.columnConstraints.add(columnConstraint);
    }

//    public SQLCollationExpr getCollationExpr() {
//        return collationExpr;
//    }
//
//    public void setCollationExpr(SQLCollationExpr collationExpr) {
//        this.collationExpr = collationExpr;
//    }


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

//    public ISQLUsingIndexClause getUsingIndex() {
//        return usingIndex;
//    }
//
//    public void setUsingIndex(ISQLUsingIndexClause usingIndex) {
//        setChildParent(usingIndex);
//        this.usingIndex = usingIndex;
//    }
//
//    public ISQLCreateMVRefresh getCreateMVRefresh() {
//        return createMVRefresh;
//    }
//
//    public void setCreateMVRefresh(ISQLCreateMVRefresh createMVRefresh) {
//        setChildParent(createMVRefresh);
//        this.createMVRefresh = createMVRefresh;
//    }

    public boolean isForUpdate() {
        return forUpdate;
    }

    public void setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
    }

//    public SQLEvaluationEditionClause getEvaluationEditionClause() {
//        return evaluationEditionClause;
//    }
//
//    public void setEvaluationEditionClause(SQLEvaluationEditionClause evaluationEditionClause) {
//        setChildParent(evaluationEditionClause);
//        this.evaluationEditionClause = evaluationEditionClause;
//    }
//
//    public SQLOnQueryComputationClause getOnQueryComputationClause() {
//        return onQueryComputationClause;
//    }
//
//    public void setOnQueryComputationClause(SQLOnQueryComputationClause onQueryComputationClause) {
//        setChildParent(onQueryComputationClause);
//        this.onQueryComputationClause = onQueryComputationClause;
//    }

//    public SQLQueryRewriteClause getQueryRewriteClause() {
//        return queryRewriteClause;
//    }
//
//    public void setQueryRewriteClause(SQLQueryRewriteClause queryRewriteClause) {
//        setChildParent(queryRewriteClause);
//        this.queryRewriteClause = queryRewriteClause;
//    }
//
//
//    public SQLTablespaceOptionExpr getTableSpaceClause() {
//        return tableSpaceClause;
//    }
//
//    public void setTableSpaceClause(SQLTablespaceOptionExpr tableSpaceClause) {
//        setChildParent(tableSpaceClause);
//        this.tableSpaceClause = tableSpaceClause;
//    }
//
//    public ISQLSelectQuery getAsSubQuery() {
//        return asSubQuery;
//    }
//
//    public void setAsSubQuery(ISQLSelectQuery asSubQuery) {
//        setChildParent(asSubQuery);
//        this.asSubQuery = asSubQuery;
//    }

    public SQLWithDataType getWithDataType() {
        return withDataType;
    }

    public void setWithDataType(SQLWithDataType withDataType) {
        this.withDataType = withDataType;
    }


    /**
     * name [ENCRYPT encryption_spec]
     */
    public static class SQLColumn extends AbstractSQLExpr {

        protected ISQLName name;
//        protected SQLEncryptClause encryptClause;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, name);
//                this.acceptChild(visitor, encryptClause);
//            }
        }

        @Override
        public SQLColumn clone() {
            SQLColumn x = new SQLColumn();
            return x;
        }

        public void cloneTo(SQLColumn x) {
            super.cloneTo(x);
        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            this.name = name;
        }

//        public SQLEncryptClause getEncryptClause() {
//            return encryptClause;
//        }
//
//        public void setEncryptClause(SQLEncryptClause encryptClause) {
//            this.encryptClause = encryptClause;
//        }
    }


    /**
     * https://www.postgresql.org/docs/10/static/sql-creatematerializedview.html
     */
    public enum SQLWithDataType {

        WITH_DATA("WITH DATA"),
        WITH_NO_DATA("WITH NO DATA"),
        ;

        public final String name;

        SQLWithDataType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
