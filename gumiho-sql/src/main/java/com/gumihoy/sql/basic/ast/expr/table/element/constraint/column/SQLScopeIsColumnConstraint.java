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
package com.gumihoy.sql.basic.ast.expr.table.element.constraint.column;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.AbstractSQLConstraint;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SCOPE IS scopeTable
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#check%20constraint%20definition
 *
 * @author kent onCondition 2018/3/13.
 */
public class SQLScopeIsColumnConstraint extends AbstractSQLConstraint implements ISQLColumnConstraint {

    protected ISQLName scopeTable;

    public SQLScopeIsColumnConstraint() {
    }

    public SQLScopeIsColumnConstraint(ISQLName scopeTable) {
        setScopeTable(scopeTable);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, scopeTable);
        }
    }

    @Override
    public SQLScopeIsColumnConstraint clone() {
        SQLScopeIsColumnConstraint x = new SQLScopeIsColumnConstraint();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLScopeIsColumnConstraint x) {
        ISQLName scopeTableClone = this.scopeTable.clone();
        x.setScopeTable(scopeTableClone);

    }

    public ISQLName getScopeTable() {
        return scopeTable;
    }

    public void setScopeTable(ISQLName scopeTable) {
        setChildParent(scopeTable);
        this.scopeTable = scopeTable;
    }
}
