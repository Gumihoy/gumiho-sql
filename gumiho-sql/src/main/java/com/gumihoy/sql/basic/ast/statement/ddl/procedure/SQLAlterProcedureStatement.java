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


import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.procedure.alter.ISQLAlterProcedureAction;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 *
 * ALTER PROCEDURE [ schema. ] procedure_name { procedure_compile_clause | { EDITIONABLE | NONEDITIONABLE } } ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ALTER-PROCEDURE-statement.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLAlterProcedureStatement extends AbstractSQLStatement implements ISQLAlterStatement {

    protected ISQLName name;
    protected ISQLAlterProcedureAction action;

    public SQLAlterProcedureStatement(DBType dbType) {
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
    public SQLAlterProcedureStatement clone() {
        return null;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.PROCEDURE_ALTER;
    }



    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        this.name = name;
    }

    public ISQLAlterProcedureAction getAction() {
        return action;
    }

    public void setAction(ISQLAlterProcedureAction action) {
        this.action = action;
    }
}
