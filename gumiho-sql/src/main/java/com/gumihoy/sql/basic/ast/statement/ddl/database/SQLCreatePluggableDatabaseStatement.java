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
package com.gumihoy.sql.basic.ast.statement.ddl.database;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATE DATABASE [IF NOT EXISTS] db_name [create_specification] ...
 * create_specification: [DEFAULT] CHARACTER SET [=] charset_name | [DEFAULT] COLLATE [=] collation_name
 * https://dev.mysql.com/doc/refman/5.7/en/create-database.html
 * <p>
 * <p>
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-DATABASE.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreatePluggableDatabaseStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean ifNotExists;
    public ISQLName name;
    protected final List<ISQLExpr> actions = new ArrayList<>();

    public SQLCreatePluggableDatabaseStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, actions);
//        }
    }

    @Override
    public ISQLStatement clone() {
        SQLCreatePluggableDatabaseStatement x = new SQLCreatePluggableDatabaseStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCreatePluggableDatabaseStatement x) {
        super.cloneTo(x);

        x.ifNotExists = this.ifNotExists;

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        for (ISQLExpr action : this.actions) {
            ISQLExpr actionClone = action.clone();
            x.addAction(actionClone);
        }

    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {

        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName)target);
            return true;
        }

//        boolean replace = replaceInList(items, source, target, this);
//        if (replace) {
//            return true;
//        }

        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.DATABASE_CREATE;
    }


    public boolean isIfNotExists() {
        return ifNotExists;
    }

    public SQLCreatePluggableDatabaseStatement setIfNotExists(boolean ifNotExists) {
        this.ifNotExists = ifNotExists;
        return this;
    }

    public ISQLName getName() {
        return name;
    }

    public SQLCreatePluggableDatabaseStatement setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
        return this;
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
}
