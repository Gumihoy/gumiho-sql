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
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * STORAGE ({ INITIAL size_clause | NEXT size_clause | MINEXTENTS integer | MAXEXTENTS { integer | UNLIMITED } | maxsize_clause | PCTINCREASE integer | FREELISTS integer | FREELIST GROUPS integer | OPTIMAL [ size_clause | NULL ] | BUFFER_POOL { KEEP | RECYCLE | DEFAULT } | FLASH_CACHE { KEEP | NONE | DEFAULT } | ENCRYPT } ... )
 * <p>
 * storage_clause
 * <p>
 * https://docs.oracle.com/database/121/SQLRF/clauses009.htm#SQLRF30013
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/storage_clause.html#GUID-C5A67610-3160-41E9-8D48-03206BD5ED15
 *
 * @author kent onCondition 2018/3/16.
 */
public class SQLStorageClause extends AbstractSQLExpr implements ISQLLobStorageParameter, ISQLPhysicalAttribute {

    protected final List<ISQLItem> items = new ArrayList<>();

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, items);
        }
    }

    @Override
    public SQLStorageClause clone() {
        SQLStorageClause x = new SQLStorageClause();
        for (ISQLItem item : items) {
            ISQLItem itemClone = item.clone();
            x.addItem(itemClone);
        }
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


    public interface ISQLItem extends ISQLExpr{
        @Override
        ISQLItem clone();
    }

    /**
     * INITIAL size_clause
     */
    public static class SQLInitialSizeClause extends AbstractSQLExpr implements ISQLItem {

        protected SQLSizeClause sizeClause;

        public SQLInitialSizeClause(SQLSizeClause sizeClause) {
            setSizeClause(sizeClause);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, sizeClause);
            }
        }

        @Override
        public SQLInitialSizeClause clone() {
            SQLSizeClause sizeClauseClone = this.sizeClause.clone();
            SQLInitialSizeClause x = new SQLInitialSizeClause(sizeClauseClone);
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
     * NEXT size_clause
     */
    public static class SQLNextSizeClause extends AbstractSQLExpr implements ISQLItem {

        protected SQLSizeClause sizeClause;

        public SQLNextSizeClause(SQLSizeClause sizeClause) {
            setSizeClause(sizeClause);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, sizeClause);
//            }
        }

        @Override
        public SQLNextSizeClause clone() {
            SQLSizeClause sizeClauseClone = this.sizeClause.clone();
            SQLNextSizeClause x = new SQLNextSizeClause(sizeClauseClone);
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
     * MINEXTENTS integer
     */
    public static class SQLMinExtentsClause extends AbstractSQLExpr implements ISQLItem {

        protected ISQLExpr value;

        public SQLMinExtentsClause(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public SQLMinExtentsClause clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLMinExtentsClause x = new SQLMinExtentsClause(valueClone);
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


    /**
     * MAXEXTENTS integer/UNLIMITED
     */
    public static class SQLMaxExtentsClause extends AbstractSQLExpr implements ISQLItem {

        protected ISQLExpr value;

        public SQLMaxExtentsClause(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public SQLMaxExtentsClause clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLMaxExtentsClause x = new SQLMaxExtentsClause(valueClone);
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

    /**
     * PCTINCREASE integer
     */
    public static class SQLPctIncreaseClause extends AbstractSQLExpr implements ISQLItem {

        protected ISQLExpr value;

        public SQLPctIncreaseClause(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public SQLPctIncreaseClause clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLPctIncreaseClause x = new SQLPctIncreaseClause(valueClone);
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


    /**
     * FREELISTS integer
     */
    public static class SQLFreeListsClause extends AbstractSQLExpr implements ISQLItem {

        protected ISQLExpr value;

        public SQLFreeListsClause(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        @Override
        public SQLFreeListsClause clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLFreeListsClause x = new SQLFreeListsClause(valueClone);
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

    /**
     * FREELIST GROUPS integer
     */
    public static class SQLFreeListGroupsClause extends AbstractSQLExpr implements ISQLItem {

        protected ISQLExpr value;

        public SQLFreeListGroupsClause(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public SQLFreeListGroupsClause clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLFreeListGroupsClause x = new SQLFreeListGroupsClause(valueClone);

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

    /**
     * Optimal [size_clause|NULL]
     */
    public static class SQLOptimalClause extends AbstractSQLExpr implements ISQLItem {

        protected ISQLExpr value;

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        @Override
        public SQLOptimalClause clone() {
            SQLOptimalClause x = new SQLOptimalClause();

            if (this.value != null) {
                ISQLExpr valueClone = this.value.clone();
                x.setValue(valueClone);
            }

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


    /**
     * BUFFER_POOL (KEEP|RECYCLE|DEFAULT)
     */
    public static class SQLBufferPoolClause extends AbstractSQLExpr implements ISQLItem {

        protected SQLBufferPoolType type;

        public SQLBufferPoolClause(SQLBufferPoolType type) {
            this.type = type;
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLBufferPoolClause clone() {
            SQLBufferPoolClause x = new SQLBufferPoolClause(this.type);
            return x;
        }

        public SQLBufferPoolType getType() {
            return type;
        }

        public void setType(SQLBufferPoolType type) {
            this.type = type;
        }
    }


    /**
     * FLASH_CACHE { KEEP | NONE | DEFAULT }
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/refrn/ALL_ALL_TABLES.html#GUID-B8CF1D2A-9AA0-4C94-BBBA-4672C7CF735F
     */
    public static class SQLFlashCacheClause extends AbstractSQLExpr implements ISQLItem {

        protected SQLFlashCacheType type;

        public SQLFlashCacheClause(SQLFlashCacheType type) {
            this.type = type;
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLFlashCacheClause clone() {
            SQLFlashCacheClause x = new SQLFlashCacheClause(this.type);
            return x;
        }

        public SQLFlashCacheType getType() {
            return type;
        }

        public void setType(SQLFlashCacheType type) {
            this.type = type;
        }
    }

    /**
     * CELL_FLASH_CACHE { KEEP | NONE | DEFAULT }
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/refrn/ALL_ALL_TABLES.html#GUID-B8CF1D2A-9AA0-4C94-BBBA-4672C7CF735F
     */
    public static class SQLCellFlashCacheClause extends AbstractSQLExpr implements ISQLItem {

        protected SQLCellFlashCacheType type;

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLCellFlashCacheClause clone() {
            SQLCellFlashCacheClause x = new SQLCellFlashCacheClause();
            x.type = this.type;
            return x;
        }

        public SQLCellFlashCacheType getType() {
            return type;
        }

        public void setType(SQLCellFlashCacheType type) {
            this.type = type;
        }
    }

    /**
     * Encrypt
     */
    public static class SQLEncryptClause extends AbstractSQLExpr implements ISQLItem {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLEncryptClause clone() {
            SQLEncryptClause x = new SQLEncryptClause();
            return x;
        }

    }


    public enum SQLBufferPoolType implements ISQLASTEnum {
        KEEP("KEEP", "KEEP"),
        RECYCLE("recycle", "RECYCLE"),
        DEFAULT("default", "DEFAULT");

        public final String lower;
        public final String upper;

        SQLBufferPoolType(String lower, String upper) {
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

    public static enum SQLFlashCacheType implements ISQLASTEnum {

        KEEP("KEEP", "KEEP"),
        NONE("NONE", "NONE"),
        DEFAULT("DEFAULT", "DEFAULT");

        public final String lower;
        public final String upper;

        SQLFlashCacheType(String lower, String upper) {
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

    public enum SQLCellFlashCacheType implements ISQLASTEnum {

        KEEP("KEEP", "KEEP"),
        NONE("NONE", "NONE"),
        DEFAULT("DEFAULT", "DEFAULT");

        public final String lower;
        public final String upper;

        SQLCellFlashCacheType(String lower, String upper) {
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
