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
package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * ALLOCATE EXTENT[ ( { SIZE size_clause | DATAFILE 'filename'| INSTANCE integer} ...)]
 * allocate_extent_clause
 * <p>
 * https://docs.oracle.com/database/121/SQLRF/clauses001.htm#SQLRF30005
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/allocate_extent_clause.html#GUID-DA6B3DC2-84B5-4404-AD96-5ABF7341580F
 *
 * @author kent onCondition 2018/3/16.
 */
public class SQLAllocateExtentClause extends AbstractSQLExpr {

    protected final List<ISQLItem> items = new ArrayList<>();

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, items);
        }
    }

    @Override
    public SQLAllocateExtentClause clone() {
        SQLAllocateExtentClause x = new SQLAllocateExtentClause();

        return x;
    }

    public List<ISQLItem> getItems() {
        return items;
    }

    public void addItem(ISQLItem item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }


    public interface ISQLItem extends ISQLExpr {
        @Override
        ISQLItem clone();
    }

    /**
     * SIZE size_clause
      */
    public static class SQLAllocateExtentSizeClause extends AbstractSQLExpr implements ISQLItem {

        protected SQLSizeClause sizeClause;

        public SQLAllocateExtentSizeClause() {
        }

        public SQLAllocateExtentSizeClause(SQLSizeClause sizeClause) {
            setSizeClause(sizeClause);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, sizeClause);
//            }
        }

        @Override
        public SQLAllocateExtentSizeClause clone() {
            SQLAllocateExtentSizeClause x = new SQLAllocateExtentSizeClause();
            SQLSizeClause sizeClauseClone = this.sizeClause.clone();
            x.setSizeClause(sizeClauseClone);
            return x;
        }

        public SQLSizeClause getSizeClause() {
            return sizeClause;
        }

        public void setSizeClause(SQLSizeClause sizeClause) {
            setChildParent(sizeClause);
            this.sizeClause = sizeClause;
        }
    }



    /**
     * DATAFILE 'filename'
     */
    public static class SQLAllocateExtentDataFileClause extends AbstractSQLExpr implements ISQLItem {

        protected ISQLExpr name;

        public SQLAllocateExtentDataFileClause() {
        }

        public SQLAllocateExtentDataFileClause(ISQLExpr name) {
            setName(name);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, name);
//            }
        }

        @Override
        public SQLAllocateExtentDataFileClause clone() {
            SQLAllocateExtentDataFileClause x = new SQLAllocateExtentDataFileClause();
            ISQLExpr nameClone = this.name.clone();
            x.setName(nameClone);
            return x;
        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            setChildParent(name);
            this.name = name;
        }
    }

    /**
     * INSTANCE integer
     */
    public static class SQLAllocateExtentInstanceClause extends AbstractSQLExpr implements ISQLItem {

        protected ISQLExpr value;

        public SQLAllocateExtentInstanceClause() {
        }

        public SQLAllocateExtentInstanceClause(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public SQLAllocateExtentInstanceClause clone() {
            SQLAllocateExtentInstanceClause x = new SQLAllocateExtentInstanceClause();
            ISQLExpr valueClone = this.value.clone();
            x.setValue(valueClone);
            return x;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


}
