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
package com.gumihoy.sql.basic.ast.statement.ddl.table;


import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.select.SQLObjectNameTableReference;
import com.gumihoy.sql.basic.ast.expr.table.partition.ISQLPartitionBy;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * ALTER TABLE <table name> <alter table action>
 * <p>
 * <add column definition> : ADD [ COLUMN ] <column definition>
 * <alter column definition> :   ALTER [ COLUMN ] <column name> <alter column action>
 * <drop column definition> :  DROP [ COLUMN ] <column name> <drop behavior>
 * <add table constraint definition> : ADD <table constraint definition>
 * <drop table constraint definition> :  DROP CONSTRAINT <constraint name> <drop behavior>
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#alter%20table%20statement
 * <p>
 * ALTER [ONLINE|OFFLINE] [IGNORE] TABLE tbl_name [alter_specification [, alter_specification] ...] [partition_options]
 * https://dev.mysql.com/doc/refman/5.7/en/alter-table.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLAlterTableStatement extends AbstractSQLStatement implements ISQLAlterStatement {

//    protected SQLInTimeAction inTimeAction;

    protected boolean ignore;

    protected boolean ifExists = false;

    protected SQLObjectNameTableReference tableReference;

    protected SQLMemOptimizeReadType memOptimizeRead;

    protected final List<ISQLExpr> actions = new ArrayList<>();

    // mysql
    protected ISQLPartitionBy partitionBy;


    public SQLAlterTableStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, tableReference);
            this.acceptChild(visitor, actions);
            this.acceptChild(visitor, partitionBy);
        }
    }

    @Override
    public SQLAlterTableStatement clone() {
        SQLAlterTableStatement x = new SQLAlterTableStatement(this.dbType);

        cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableStatement x) {
        super.cloneTo(x);
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.TABLE_ALTER;
    }





//    public SQLInTimeAction getInTimeAction() {
//        return inTimeAction;
//    }
//
//    public void setInTimeAction(SQLInTimeAction inTimeAction) {
//        this.inTimeAction = inTimeAction;
//    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public boolean isIfExists() {
        return ifExists;
    }

    public void setIfExists(boolean ifExists) {
        this.ifExists = ifExists;
    }

    public SQLMemOptimizeReadType getMemOptimizeRead() {
        return memOptimizeRead;
    }

    public void setMemOptimizeRead(SQLMemOptimizeReadType memOptimizeRead) {
        this.memOptimizeRead = memOptimizeRead;
    }

    public SQLObjectNameTableReference getTableReference() {
        return tableReference;
    }

    public void setTableReference(SQLObjectNameTableReference tableReference) {
        setChildParent(tableReference);
        this.tableReference = tableReference;
    }

    public ISQLName getTable() {
        if (tableReference == null) {
            return null;
        }
        return tableReference.getName();
    }

    public String getTableName() {
        if (tableReference == null) {
            return null;
        }
        return tableReference.getName().getSimpleName();
    }


    public List<ISQLExpr> getActions() {
        return actions;
    }

    public void addAction(ISQLExpr action) {
        if (action == null) {
            return;
        }
        setChildParent(action);
        this.actions.add(action);
    }


    public ISQLPartitionBy getPartitionBy() {
        return partitionBy;
    }

    public void setPartitionBy(ISQLPartitionBy partitionBy) {
        setChildParent(partitionBy);
        this.partitionBy = partitionBy;
    }



    public enum SQLMemOptimizeReadType implements ISQLASTEnum {

        MEMOPTIMIZE_FOR_READ("memoptimize for read", "MEMOPTIMIZE FOR READ"),
        NO_MEMOPTIMIZE_FOR_READ("no memoptimize for read", "NO MEMOPTIMIZE FOR READ");

        public final String lower;
        public final String upper;


        SQLMemOptimizeReadType(String lower, String upper) {
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
