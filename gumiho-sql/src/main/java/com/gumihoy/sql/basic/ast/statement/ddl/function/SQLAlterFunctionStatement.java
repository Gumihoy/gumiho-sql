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
package com.gumihoy.sql.basic.ast.statement.ddl.function;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 *ALTER FUNCTION [ schema. ] function_name { function_compile_clause | EDITIONABLE | NONEDITIONABLE } ;
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ALTER-FUNCTION-statement.html#GUID-C78E0E7E-6BCF-4AE8-8C75-9F133E8FB4E1
 * @author kent onCondition 2018/1/23.
 */
public class SQLAlterFunctionStatement extends AbstractSQLStatement implements ISQLAlterStatement {

    protected ISQLName name;

    protected ISQLExpr action;

    public SQLAlterFunctionStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, action);
        }
    }

    @Override
    public SQLAlterFunctionStatement clone() {
        SQLAlterFunctionStatement x = new SQLAlterFunctionStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterFunctionStatement x) {
        super.cloneTo(x);
        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        if (this.action != null) {
            ISQLExpr actionClone = this.action.clone();
            x.setAction(actionClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        return super.replace(source, target);
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.FUNCTION_ALTER;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLExpr getAction() {
        return action;
    }

    public void setAction(ISQLExpr action) {
        setChildParent(action);
        this.action = action;
    }
}
