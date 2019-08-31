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
package com.gumihoy.sql.basic.ast.expr.select.group;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#having%20clause
 *
 * @author kent onCondition 2018/2/8.
 */
public class SQLHavingClause extends AbstractSQLExpr {

    protected ISQLExpr condition;

    public SQLHavingClause(ISQLExpr condition) {
        setCondition(condition);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, condition);
        }
    }

    @Override
    public SQLHavingClause clone() {
        ISQLExpr conditionClone = this.condition.clone();

        SQLHavingClause x = new SQLHavingClause(conditionClone);

        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLHavingClause x) {
        super.cloneTo(x);
    }

    public ISQLExpr getCondition() {
        return condition;
    }

    public void setCondition(ISQLExpr condition) {
        setChildParent(condition);
        this.condition = condition;
    }
}
