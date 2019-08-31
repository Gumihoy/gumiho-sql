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

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * exception_declaration
 *
 * exception EXCEPTION;
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/exception-declaration.html#GUID-AAC8C54F-775C-4E65-B531-0350CFF5B1BD
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/exception-declaration.html#GUID-AAC8C54F-775C-4E65-B531-0350CFF5B1BD
 *
 * @author kent onCondition 2018/3/22.
 */
public class SQLExceptionDeclaration extends AbstractSQLExpr {

    private ISQLName name;

    public SQLExceptionDeclaration(ISQLName name) {
        setName(name);
        setAfterSemi(true);
    }

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }


    @Override
    public SQLExceptionDeclaration clone() {
        ISQLName nameClone = this.name.clone();
        return new SQLExceptionDeclaration(nameClone);
    }


    public void cloneTo(SQLExceptionDeclaration x) {
        super.cloneTo(x);
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        this.name = name;
    }
}
