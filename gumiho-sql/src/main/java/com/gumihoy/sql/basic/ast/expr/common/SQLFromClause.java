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
import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * FROM XX
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#from%20clause
 *
 * @author kent on 2018/3/26.
 */
public class SQLFromClause extends AbstractSQLExpr {

    protected ISQLTableReference tableReference;

    public SQLFromClause(ISQLTableReference tableReference) {
        setTableReference(tableReference);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, tableReference);
        }
    }


    @Override
    public SQLFromClause clone() {

        ISQLTableReference tableReferenceClone = this.tableReference.clone();

        SQLFromClause x = new SQLFromClause(tableReferenceClone);

        return x;
    }

    public ISQLTableReference getTableReference() {
        return tableReference;
    }

    public void setTableReference(ISQLTableReference tableReference) {
        setChildParent(tableReference);
        this.tableReference = tableReference;
    }
}
