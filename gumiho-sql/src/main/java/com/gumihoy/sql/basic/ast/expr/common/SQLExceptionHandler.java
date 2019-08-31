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
package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * WHEN { exception [ OR exception ]... | OTHERS }
 * THEN statement [ statement ]...
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/exception-handler.html#GUID-3FECF29B-A240-4191-A635-92C612D00C4D
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/exception-handler.html#GUID-3FECF29B-A240-4191-A635-92C612D00C4D
 *
 * @author kent onCondition 2018/3/22.
 */
public class SQLExceptionHandler extends AbstractOracleExpr {

    private final List<ISQLName> exceptions = new ArrayList<>();

    private final List<SQLLabelStatement> statements = new ArrayList<>();


    @Override
    public void accept0(IOracleASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, exceptions);
            this.acceptChild(visitor, statements);
        }
    }

    @Override
    public SQLExceptionHandler clone() {
        SQLExceptionHandler x = new SQLExceptionHandler();
        return x;
    }

    public List<ISQLName> getExceptions() {
        return exceptions;
    }

    public void addException(ISQLName exception) {
        if (exception == null) {
            return;
        }
        setChildParent(exception);
        this.exceptions.add(exception);
    }

    public List<SQLLabelStatement> getStatements() {
        return statements;
    }

    public void addStatement(SQLLabelStatement statement) {
        if (statement == null) {
            return;
        }
        setChildParent(statement);
        this.statements.add(statement);
    }
}
