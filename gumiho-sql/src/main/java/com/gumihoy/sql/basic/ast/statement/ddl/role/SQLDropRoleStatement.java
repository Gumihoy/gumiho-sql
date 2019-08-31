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
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLDropStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * DROP ROLE <role name>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#drop%20role%20statement
 * <p>
 * DROP ROLE [IF EXISTS] role [, role ] ...
 * https://dev.mysql.com/doc/refman/8.0/en/drop-role.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDropRoleStatement extends AbstractSQLStatement implements ISQLDropStatement {

    protected boolean ifExists;

    protected final List<ISQLExpr> names = new ArrayList<>();


    public SQLDropRoleStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, names);
        }
    }


    @Override
    public SQLDropRoleStatement clone() {
        SQLDropRoleStatement x = new SQLDropRoleStatement(DBType.Oracle);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLDropRoleStatement x) {
        super.cloneTo(x);

        x.ifExists = this.ifExists;

        for (ISQLExpr name : names) {
            ISQLExpr nameClone = name.clone();
            x.addName(nameClone);
        }
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.ROLE_DROP;
    }




    public boolean isIfExists() {
        return ifExists;
    }

    public void setIfExists(boolean ifExists) {
        this.ifExists = ifExists;
    }

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
}
