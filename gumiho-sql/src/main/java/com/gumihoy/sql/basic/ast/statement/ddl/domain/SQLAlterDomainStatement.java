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
package com.gumihoy.sql.basic.ast.statement.ddl.domain;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * @author kent onCondition 2018/1/23.
 * @see {https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#alter domain statement}
 */
public class SQLAlterDomainStatement extends AbstractSQLStatement implements ISQLAlterStatement {


    public SQLAlterDomainStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {

    }

    @Override
    public SQLAlterDomainStatement clone() {
        return null;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        return super.replace(source, target);
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.DOMAIN_ALTER;
    }
}
