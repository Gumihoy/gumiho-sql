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
import com.gumihoy.sql.basic.ast.expr.common.SQLParameterDeclaration;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLDropStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * DROP FUNCTION [IF EXISTS] function_name  [(parameters)]  [ CASCADE | RESTRICT ]
 * <p>
 * https://www.postgresql.org/docs/current/static/sql-dropfunction.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/DROP-FUNCTION-statement.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDropFunctionStatement extends AbstractSQLStatement implements ISQLDropStatement {

    protected boolean ifExists;

    protected ISQLName name;

    protected final List<SQLParameterDeclaration> parameters = new ArrayList<>();

//    protected SQLCascadeType option;


    public SQLDropFunctionStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, parameters);
        }
    }

    @Override
    public SQLDropFunctionStatement clone() {
        SQLDropFunctionStatement x = new SQLDropFunctionStatement(this.dbType);
        cloneTo(x);
        return x;
    }

    public void cloneTo(SQLDropFunctionStatement x) {
        super.cloneTo(x);

        x.setIfExists(this.ifExists);

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        for (SQLParameterDeclaration parameter : x.parameters) {
            SQLParameterDeclaration parameterClone = parameter.clone();
            x.addParameter(parameterClone);
        }

//        x.setOption(this.option);
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            this.setName((ISQLName) target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.FUNCTION_DROP;
    }

    public boolean isIfExists() {
        return ifExists;
    }

    public void setIfExists(boolean ifExists) {
        this.ifExists = ifExists;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        this.name = name;
    }

    public List<SQLParameterDeclaration> getParameters() {
        return parameters;
    }

    public void addParameter(SQLParameterDeclaration parameter) {
        if (parameter == null) {
            return;
        }
        setChildParent(parameter);
        this.parameters.add(parameter);
    }

//    public SQLCascadeType getOption() {
//        return option;
//    }
//
//    public void setOption(SQLCascadeType option) {
//        this.option = option;
//    }
}
