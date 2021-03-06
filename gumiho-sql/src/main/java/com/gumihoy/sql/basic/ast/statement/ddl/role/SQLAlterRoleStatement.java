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
package com.gumihoy.sql.basic.ast.statement.ddl.role;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.role.ISQLCreateRoleAction;
import com.gumihoy.sql.basic.ast.expr.role.alter.ISQLAlterRoleAction;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * ALTER ROLE role_specification [ WITH ] option [ ... ]
 * https://www.postgresql.org/docs/9.5/static/sql-alterrole.html
 * <p>
 * <p>
 * ALTER ROLE role { NOT IDENTIFIED | IDENTIFIED { BY password | USING [ schema. ] package | EXTERNALLY | GLOBALLY AS domain_name_of directory_group } } [ CONTAINER = { CURRENT | ALL } ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-ROLE.html
 *
 * @author kent onCondition 2018/6/23.
 */
public class SQLAlterRoleStatement extends AbstractSQLStatement implements ISQLAlterStatement {

    protected ISQLExpr name;

    protected ISQLExpr auth;

    protected final List<ISQLAlterRoleAction> actions = new ArrayList<>();

    public SQLAlterRoleStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, auth);
            this.acceptChild(visitor, actions);
        }
    }


    @Override
    public SQLAlterRoleStatement clone() {
        SQLAlterRoleStatement x = new SQLAlterRoleStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterRoleStatement x) {
        super.cloneTo(x);
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.ROLE_ALTER;
    }


    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        this.name = name;
    }

    public ISQLExpr getAuth() {
        return auth;
    }

    public void setAuth(ISQLExpr auth) {
        this.auth = auth;
    }

    public List<ISQLAlterRoleAction> getActions() {
        return actions;
    }
    public void addAction(ISQLAlterRoleAction action) {
        if (action == null) {
            return;
        }
        setChildParent(action);
        this.actions.add(action);
    }
}
