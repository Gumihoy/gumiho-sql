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
 * AGGREGATE USING usingName=nameIdentifier
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/AGGREGATE-clause.html
 *
 * @author kent onCondition 2018/3/20.
 */
public class SQLAggregateClause extends AbstractSQLExpr {

    private ISQLName name;

    public SQLAggregateClause() {
    }

    public SQLAggregateClause(ISQLName name) {
        setName(name);
    }

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }


    @Override
    public SQLAggregateClause clone() {
        SQLAggregateClause x = new SQLAggregateClause();
        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);
        return x;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }
}
