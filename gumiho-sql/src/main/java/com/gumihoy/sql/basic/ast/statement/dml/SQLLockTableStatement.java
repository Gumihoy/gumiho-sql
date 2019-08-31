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
package com.gumihoy.sql.basic.ast.statement.dml;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * LOCK TABLES tbl_name [[AS] alias] lock_type [, tbl_name [[AS] alias] lock_type] ...
 * lock_type: READ [LOCAL] | [LOW_PRIORITY] WRITE
 * https://dev.mysql.com/doc/refman/5.6/en/lock-tables.html
 * <p>
 * <p>
 * LOCK [ TABLE ] [ ONLY ] name [ * ] [, ...] [ IN lockmode MODE ] [ NOWAIT ]
 * https://www.postgresql.org/docs/10/static/sql-lock.html
 * <p>
 * LOCK TABLE [ schema. ] { table , view } [ partition_extension_clause , @ dblink ] [, [ schema. ] { table , view } [ partition_extension_clause , @ dblink ] ]... IN lockmode MODE [ NOWAIT , WAIT integer ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/LOCK-TABLE.html#GUID-4C00C6D9-C5C5-46CC-AD33-A64001744A4C
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLLockTableStatement extends AbstractSQLStatement implements ISQLDMLStatement {

    protected SQLType type = SQLType.TABLE;

    protected boolean only;

    protected final List<SQLLockTableItem> items = new ArrayList<>();

    protected SQLLockMode lockMode;

//    protected SQLForUpdateClause.SQLForOption option;

    public SQLLockTableStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, items);
//            this.acceptChild(visitor, option);
//        }
    }

    @Override
    public SQLLockTableStatement clone() {
        SQLLockTableStatement x = new SQLLockTableStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLLockTableStatement x) {
        super.cloneTo(x);

        x.type = this.type;
        x.only = this.only;

        for (SQLLockTableItem item : this.items) {
            SQLLockTableItem itemClone = item.clone();
            x.addItem(itemClone);
        }

        x.lockMode = this.lockMode;

//        if (this.option != null) {
//            SQLForUpdateClause.SQLForOption optionClone = this.option.clone();
//            x.setOption(optionClone);
//        }
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;
    }


    public SQLType getType() {
        return type;
    }

    public SQLLockTableStatement setType(SQLType type) {
        this.type = type;
        return this;
    }

    public boolean isOnly() {
        return only;
    }

    public void setOnly(boolean only) {
        this.only = only;
    }

    public List<SQLLockTableItem> getItems() {
        return items;
    }

    public void addItem(SQLLockTableItem item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }

    public SQLLockMode getLockMode() {
        return lockMode;
    }

    public void setLockMode(SQLLockMode lockMode) {
        this.lockMode = lockMode;
    }

//    public SQLForUpdateClause.SQLForOption getOption() {
//        return option;
//    }
//
//    public void setOption(SQLForUpdateClause.SQLForOption option) {
//        this.option = option;
//    }

    /**
     * tbl_name [[AS] alias] lock_type
     * https://dev.mysql.com/doc/refman/5.6/en/lock-tables.html
     * <p>
     * name [partitionClause]
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/LOCK-TABLE.html#GUID-4C00C6D9-C5C5-46CC-AD33-A64001744A4C
     */
    public static class SQLLockTableItem extends AbstractSQLExpr {
        protected ISQLName name;
        protected boolean as;
        protected ISQLName alias;
//        protected ISQLPartitionClause partitionClause;

        protected SQLLockType lockType;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, name);
//                this.acceptChild(visitor, partitionClause);
//            }
        }

        @Override
        public SQLLockTableItem clone() {
            SQLLockTableItem x = new SQLLockTableItem();

            ISQLName nameClone = this.name.clone();
            x.setName(nameClone);

            x.as = this.as;

            if (this.alias != null) {
                ISQLName aliasClone = this.alias.clone();
                x.setAlias(aliasClone);
            }

//            if (this.partitionClause != null) {
//                ISQLPartitionClause partitionClauseClone = this.partitionClause.clone();
//                x.setPartitionClause(partitionClauseClone);
//            }

            return x;
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            if (source == name
                    && target instanceof ISQLName) {
                setName((ISQLName) target);
                return true;
            }
            return false;
        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            setChildParent(name);
            this.name = name;
        }

        public boolean isAs() {
            return as;
        }

        public SQLLockTableItem setAs(boolean as) {
            this.as = as;
            return this;
        }

        public ISQLName getAlias() {
            return alias;
        }

        public SQLLockTableItem setAlias(ISQLName alias) {
            setChildParent(alias);
            this.alias = alias;
            return this;
        }

//        public ISQLPartitionClause getPartitionClause() {
//            return partitionClause;
//        }
//
//        public void setPartitionClause(ISQLPartitionClause partitionClause) {
//            setChildParent(partitionClause);
//            this.partitionClause = partitionClause;
//        }

        public SQLLockType getLockType() {
            return lockType;
        }

        public SQLLockTableItem setLockType(SQLLockType lockType) {
            this.lockType = lockType;
            return this;
        }
    }

    public enum SQLType implements ISQLASTEnum {
        TABLE("TABLE"),
        TABLES("TABLES");

        public final String upper;

        SQLType(String upper) {
            this.upper = upper;
        }


        @Override
        public String lower() {
            return null;
        }

        @Override
        public String upper() {
            return upper;
        }
    }

    /**
     * https://www.postgresql.org/docs/10/static/sql-lock.html
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/LOCK-TABLE.html#GUID-4C00C6D9-C5C5-46CC-AD33-A64001744A4C
     */
    public enum SQLLockMode implements ISQLASTEnum {

        ACCESS_SHARE("ACCESS_SHARE"),
        ROW_SHARE("ROW_SHARE"),
        ROW_EXCLUSIVE("ROW_EXCLUSIVE"),
        SHARE_UPDATE_EXCLUSIVE("SHARE_UPDATE_EXCLUSIVE"),
        SHARE_UPDATE("SHARE_UPDATE"),
        SHARE("SHARE"),
        SHARE_ROW_EXCLUSIVE("SHARE_ROW_EXCLUSIVE"),
        EXCLUSIVE("EXCLUSIVE"),
        ACCESS_EXCLUSIVE("ACCESS_EXCLUSIVE"),;

        public final String upper;

        SQLLockMode(String upper) {
            this.upper = upper;
        }

        @Override
        public String lower() {
            return null;
        }

        @Override
        public String upper() {
            return upper;
        }

    }


    /**
     * READ [LOCAL] 、[LOW_PRIORITY] WRITE
     * https://dev.mysql.com/doc/refman/5.6/en/lock-tables.html
     */
    public enum SQLLockType implements ISQLASTEnum {
        READ("READ"),
        READ_LOCAL("READ_LOCAL"),
        WRITE("WRITE"),
        LOW_PRIORITY_WRITE("LOW_PRIORITY_WRITE"),;

        public final String upper;

        SQLLockType(String upper) {
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return null;
        }

        @Override
        public String upper() {
            return upper;
        }
    }

}
