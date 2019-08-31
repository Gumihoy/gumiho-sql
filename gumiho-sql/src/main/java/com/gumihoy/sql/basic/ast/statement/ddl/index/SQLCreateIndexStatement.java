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
package com.gumihoy.sql.basic.ast.statement.ddl.index;


import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLFromClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLWhereClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
import com.gumihoy.sql.basic.ast.expr.index.SQLIndexColumn;
import com.gumihoy.sql.basic.ast.expr.select.SQLObjectNameTableReference;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * https://docs.microsoft.com/en-us/sql/t-sql/statements/create-index-transact-sql?view=sql-server-2017
 * <p>
 * <p>
 * CREATE [ UNIQUE ] INDEX [ CONCURRENTLY ] [ [ IF NOT EXISTS ] name ] ON [ ONLY ] table_name [ USING method ]
 * ( { column_name | ( expression ) } [ COLLATE collation ] [ opclass ] [ ASC | DESC ] [ NULLS { FIRST | LAST } ] [, ...] )
 * [ INCLUDE ( column_name [, ...] ) ]
 * [ WITH ( storage_parameter = value [, ... ] ) ]
 * [ TABLESPACE tablespace_name ]
 * [ WHERE predicate ]
 * https://www.postgresql.org/docs/devel/static/sql-createindex.html
 * <p>
 * <p>
 * CREATE [UNIQUE|FULLTEXT|SPATIAL] INDEX index_name
 * [index_type]
 * ON tbl_name (index_col_name,...)
 * [index_option]
 * [algorithm_option | lock_option] ...
 * index_col_name:
 * col_name [(length)] [ASC | DESC]
 * <p>
 * index_option:
 * KEY_BLOCK_SIZE [=] value
 * | index_type
 * | WITH PARSER parser_name
 * | COMMENT 'string'
 * | {VISIBLE | INVISIBLE}
 * <p>
 * index_type:
 * USING {BTREE | HASH}
 * <p>
 * algorithm_option:
 * ALGORITHM [=] {DEFAULT|INPLACE|COPY}
 * <p>
 * lock_option:
 * LOCK [=] {DEFAULT|NONE|SHARED|EXCLUSIVE}
 * https://dev.mysql.com/doc/refman/8.0/en/create-index.html
 * <p>
 * CREATE [ UNIQUE | BITMAP ] INDEX [ schema. ] index
 * ON { cluster_index_clause
 * | table_index_clause
 * | bitmap_join_index_clause
 * }
 * [ USABLE | UNUSABLE ]
 * [ { DEFERRED | IMMEDIATE } INVALIDATION ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-INDEX.html#GUID-1F89BBC0-825F-4215-AF71-7588E31D8BFE
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreateIndexStatement extends AbstractSQLStatement implements ISQLCreateStatement {

//    protected SQLInTimeAction inTimeAction;
    protected SQLCategory category;

    protected boolean concurrently;
    protected boolean ifNotExists;
    protected ISQLName name;

    protected SQLIndexType indexType;

    protected boolean cluster;
    protected SQLObjectNameTableReference tableReference;

    protected final List<SQLIndexColumn> columns = new ArrayList<>();

    protected SQLFromClause fromClause;

    protected SQLWhereClause whereClause;

    protected final List<ISQLExpr> properties = new ArrayList<>();

//    protected SQLUsableType usable;
//    protected SQLInvalidationType invalidation;


    public SQLCreateIndexStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, tableReference);
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, fromClause);
            this.acceptChild(visitor, whereClause);
            this.acceptChild(visitor, properties);
        }
    }


    @Override
    public SQLCreateIndexStatement clone() {
        SQLCreateIndexStatement x = new SQLCreateIndexStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCreateIndexStatement x) {
        super.cloneTo(x);

//        x.inTimeAction = this.inTimeAction;
        x.category = this.category;
        x.concurrently = this.concurrently;
        x.ifNotExists = this.ifNotExists;


        if (this.name != null) {
            ISQLName nameClone = this.name.clone();
            x.setName(nameClone);
        }

        if (this.tableReference != null) {
            SQLObjectNameTableReference tableReferenceClone = this.tableReference.clone();
            x.setTableReference(tableReferenceClone);
        }

        for (SQLIndexColumn indexColumn : this.columns) {
            SQLIndexColumn indexColumnClone = indexColumn.clone();
            x.addColumn(indexColumnClone);
        }


        if (this.fromClause != null) {
            SQLFromClause fromClauseClone = this.fromClause.clone();
            x.setFromClause(fromClauseClone);
        }

        if (this.whereClause != null) {
            SQLWhereClause whereClauseClone = this.whereClause.clone();
            x.setWhereClause(whereClauseClone);
        }

    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            this.setName((ISQLName) target);
            return true;
        }

        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.INDEX_CREATE;
    }


//    public SQLInTimeAction getInTimeAction() {
//        return inTimeAction;
//    }
//
//    public void setInTimeAction(SQLInTimeAction inTimeAction) {
//        this.inTimeAction = inTimeAction;
//    }
//
    public SQLCategory getCategory() {
        return category;
    }

    public void setCategory(SQLCategory category) {
        this.category = category;
    }

    public boolean isConcurrently() {
        return concurrently;
    }

    public void setConcurrently(boolean concurrently) {
        this.concurrently = concurrently;
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

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public String getIndexName() {
        if (name != null) {
            return name.getSimpleName();
        }
        return null;
    }

    public ISQLName getOwner() {
        if (name instanceof SQLPropertyExpr
                && ((SQLPropertyExpr) name).getOwner() instanceof ISQLName) {
            return (ISQLName) ((SQLPropertyExpr) name).getOwner();
        }
        return null;
    }

    public SQLIndexType getIndexType() {
        return indexType;
    }

    public void setIndexType(SQLIndexType indexType) {
        this.indexType = indexType;
    }

    public boolean isCluster() {
        return cluster;
    }

    public void setCluster(boolean cluster) {
        this.cluster = cluster;
    }

    public SQLObjectNameTableReference getTableReference() {
        return tableReference;
    }

    public void setTableReference(SQLObjectNameTableReference tableReference) {
        setChildParent(tableReference);
        this.tableReference = tableReference;
    }

    public String getTableName() {
        if (tableReference != null) {
            return tableReference.getTableName();
        }
        return null;
    }


    public List<SQLIndexColumn> getColumns() {
        return columns;
    }

    public void addColumn(SQLIndexColumn indexColumn) {
        if (indexColumn == null) {
            return;
        }
        indexColumn.setParent(this);
        this.columns.add(indexColumn);
    }


    public SQLFromClause getFromClause() {
        return fromClause;
    }

    public void setFromClause(SQLFromClause fromClause) {
        setChildParent(fromClause);
        this.fromClause = fromClause;
    }

    public SQLWhereClause getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(SQLWhereClause whereClause) {
        setChildParent(whereClause);
        this.whereClause = whereClause;
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

//    public SQLUsableType getUsable() {
//        return usable;
//    }
//
//    public void setUsable(SQLUsableType usable) {
//        this.usable = usable;
//    }
//
//    public SQLInvalidationType getInvalidation() {
//        return invalidation;
//    }
//
//    public void setInvalidation(SQLInvalidationType invalidation) {
//        this.invalidation = invalidation;
//    }



    public enum SQLCategory implements ISQLASTEnum {

        UNIQUE("UNIQUE", "UNIQUE"),
        BITMAP("BITMAP", "BITMAP"),
        FULLTEXT("FULLTEXT", "FULLTEXT"),
        SPATIAL("SPATIAL", "SPATIAL");

        public final String lower;
        public final String upper;


        SQLCategory(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }

        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }

    }


    public enum SQLIndexType implements ISQLASTEnum {

        USING_BTREE("using btree", "USING_BTREE"),
        USING_HASH("using hash", "USING_HASH"),;

        public final String lower;
        public final String upper;

        SQLIndexType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }

        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }
    }


}
