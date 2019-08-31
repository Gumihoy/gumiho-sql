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
package com.gumihoy.sql.basic.ast.statement.ddl.schema;


import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATE SCHEMA [IF NOT EXISTS] db_name [create_specification] ...
 * create_specification: [DEFAULT] CHARACTER SET [=] charset_name | [DEFAULT] COLLATE [=] collation_name
 * https://dev.mysql.com/doc/refman/5.7/en/create-database.html
 *
 *
 * CREATE SCHEMA AUTHORIZATION schema { create_table_statement | create_view_statement | grant_statement }...
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-SCHEMA.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreateSchemaStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean ifNotExists;
    protected ISQLName name;
    protected final List<ISQLObject> actions = new ArrayList<>();


    public SQLCreateSchemaStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, actions);
        }
    }

    @Override
    public SQLCreateSchemaStatement clone() {
        SQLCreateSchemaStatement x = new SQLCreateSchemaStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLCreateSchemaStatement x) {
        super.cloneTo(x);

        x.ifNotExists = this.ifNotExists;

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        for (ISQLObject action : this.actions) {
            ISQLObject actionClone = action.clone();
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

        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.SCHEMA_CREATE;
    }


    public boolean isIfNotExists() {
        return ifNotExists;
    }

    public SQLCreateSchemaStatement setIfNotExists(boolean ifNotExists) {
        this.ifNotExists = ifNotExists;
        return this;
    }

    public ISQLName getName() {
        return name;
    }

    public SQLCreateSchemaStatement setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
        return this;
    }

    public List<ISQLObject> getActions() {
        return actions;
    }
    public SQLCreateSchemaStatement addAction(ISQLObject action) {
        if (action == null) {
            return this;
        }
        setChildParent(action);
        this.actions.add(action);
        return this;
    }
}
