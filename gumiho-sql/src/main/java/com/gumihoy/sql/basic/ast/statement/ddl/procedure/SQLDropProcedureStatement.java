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
package com.gumihoy.sql.basic.ast.statement.ddl.procedure;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLDropStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * DROP PROCEDURE [IF EXISTS]  procedure ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/DROP-PROCEDURE-statement.html#GUID-A66E96E6-A582-4A59-B97E-8E6EA52EC292
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDropProcedureStatement extends AbstractSQLStatement implements ISQLDropStatement {

    protected boolean ifExists;

    private ISQLName name;

    public SQLDropProcedureStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLDropProcedureStatement clone() {
        SQLDropProcedureStatement x = new SQLDropProcedureStatement(this.dbType);

        x.ifExists = this.ifExists;

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);
        return x;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            this.setName((ISQLName) target);
            return true;
        }

        return false;
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.PROCEDURE_DROP;
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
        setChildParent(name);
        this.name = name;
    }
}
