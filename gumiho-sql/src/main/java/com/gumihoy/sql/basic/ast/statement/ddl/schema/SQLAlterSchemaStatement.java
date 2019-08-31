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
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * ALTER SCHEMA [db_name] alter_specification ...
 * https://dev.mysql.com/doc/refman/5.6/en/alter-database.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLAlterSchemaStatement extends AbstractSQLStatement implements ISQLAlterStatement {

    protected ISQLName name;
    protected final List<ISQLExpr> actions = new ArrayList<>();

    public SQLAlterSchemaStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLAlterSchemaStatement clone() {
        SQLAlterSchemaStatement x = new SQLAlterSchemaStatement(this.dbType);
        return x;
    }

    public void cloneTo(SQLAlterSchemaStatement x) {
        super.cloneTo(x);

    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.SCHEMA_ALTER;
    }


    public ISQLName getName() {
        return name;
    }

    public SQLAlterSchemaStatement setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
        return this;
    }

    public List<ISQLExpr> getActions() {
        return actions;
    }

    public SQLAlterSchemaStatement addAction(ISQLExpr action) {
        if (action == null) {
            return this;
        }
        setChildParent(action);
        this.actions.add(action);
        return this;
    }

}
