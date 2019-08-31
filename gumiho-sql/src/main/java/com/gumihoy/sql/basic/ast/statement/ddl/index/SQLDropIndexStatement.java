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


import com.gumihoy.sql.basic.ast.enums.SQLDropBehavior;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLDropStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;
import com.gumihoy.sql.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * DROP INDEX index_name ON tbl_name [algorithm_option | lock_option] ...
 * algorithm_option: ALGORITHM [=] {DEFAULT|INPLACE|COPY}
 * lock_option: LOCK [=] {DEFAULT|NONE|SHARED|EXCLUSIVE}
 * https://dev.mysql.com/doc/refman/8.0/en/drop-index.html
 * <p>
 * <p>
 * DROP INDEX [ CONCURRENTLY ] [ IF EXISTS ] name [, ...] [ CASCADE | RESTRICT ]
 * https://www.postgresql.org/docs/devel/static/sql-dropindex.html
 * <p>
 * DROP INDEX [ schema. ] index [ ONLINE ] [ FORCE ] [ { DEFERRED | IMMEDIATE } INVALIDATION ];
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/DROP-INDEX.html#GUID-F60F75DF-2866-4F93-BB7F-8FCE64BF67B6
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDropIndexStatement extends AbstractSQLStatement implements ISQLDropStatement {

    protected boolean concurrently;
    protected boolean ifExists;
//    protected SQLInTimeAction inTimeAction;
    protected final List<ISQLExpr> names = new ArrayList<>();

    protected ISQLName table;

    // oracle
    protected boolean online;
    protected boolean force;
//    protected SQLInvalidationType invalidation;

    // pg
    protected SQLDropBehavior dropBehavior;

    // mysql
    protected final List<ISQLExpr> options = new ArrayList<>();


    public SQLDropIndexStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, names);
            this.acceptChild(visitor, table);
            this.acceptChild(visitor, options);
        }
    }

    @Override
    public SQLDropIndexStatement clone() {
        SQLDropIndexStatement x = new SQLDropIndexStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLDropIndexStatement x) {
        super.cloneTo(x);
        x.ifExists = this.ifExists;
        for (ISQLExpr name : this.names) {
            ISQLExpr nameClone = name.clone();
            x.addName(nameClone);
        }
        x.online = this.online;
        x.force = this.force;
//        x.invalidation = this.invalidation;
//        x.cascade = this.cascade;

        for (ISQLExpr option : this.options) {
            ISQLExpr optionClone = option.clone();
            x.addOption(optionClone);
        }
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {

//        boolean replace = replaceInList(names, source, target, this);
//        if (replace) {
//            return true;
//        }

        if (source == this.table
                && target instanceof ISQLName) {
            setTable((ISQLName) target);
            return true;
        }

        return false;
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.INDEX_DROP;
    }


    public boolean isConcurrently() {
        return concurrently;
    }

    public void setConcurrently(boolean concurrently) {
        this.concurrently = concurrently;
    }

    public boolean isIfExists() {
        return ifExists;
    }

    public void setIfExists(boolean ifExists) {
        this.ifExists = ifExists;
    }

//    public SQLInTimeAction getInTimeAction() {
//        return inTimeAction;
//    }
//
//    public void setInTimeAction(SQLInTimeAction inTimeAction) {
//        this.inTimeAction = inTimeAction;
//    }

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

    public ISQLName getTable() {
        return table;
    }

    public void setTable(ISQLName table) {
        setChildParent(table);
        this.table = table;
    }

    public void setTable(String table) {
        setTable(SQLUtils.ofName(table));
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

//    public SQLInvalidationType getInvalidation() {
//        return invalidation;
//    }
//
//    public void setInvalidation(SQLInvalidationType invalidation) {
//        this.invalidation = invalidation;
//    }
//


    public SQLDropBehavior getDropBehavior() {
        return dropBehavior;
    }

    public void setDropBehavior(SQLDropBehavior dropBehavior) {
        this.dropBehavior = dropBehavior;
    }

    public List<ISQLExpr> getOptions() {
        return options;
    }

    public void addOption(ISQLExpr option) {
        if (option == null) {
            return;
        }
        setChildParent(option);
        this.options.add(option);
    }
}
