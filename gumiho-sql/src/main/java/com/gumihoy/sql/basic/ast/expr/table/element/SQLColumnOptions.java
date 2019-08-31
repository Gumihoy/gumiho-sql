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
package com.gumihoy.sql.basic.ast.expr.table.element;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.ISQLColumnConstraint;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#column%20options
 *
 * @author kent onCondition 2018/3/28.
 */
public class SQLColumnOptions extends AbstractSQLExpr implements ISQLTableElement {

    protected ISQLName columnName;

    protected ISQLName scopeTableName;

    protected ISQLExpr defaultExpr;

    protected final List<ISQLColumnConstraint> columnConstraints = new ArrayList<>();


    @Override
    protected void accept0(ISQLASTVisitor visitor) {

    }

    @Override
    public SQLColumnOptions clone() {
        SQLColumnOptions x = new SQLColumnOptions();
        return x;
    }


    public ISQLName getColumnName() {
        return columnName;
    }

    public void setColumnName(ISQLName columnName) {
        this.columnName = columnName;
    }

    public ISQLName getScopeTableName() {
        return scopeTableName;
    }

    public void setScopeTableName(ISQLName scopeTableName) {
        this.scopeTableName = scopeTableName;
    }

    public ISQLExpr getDefaultExpr() {
        return defaultExpr;
    }

    public void setDefaultExpr(ISQLExpr defaultExpr) {
        this.defaultExpr = defaultExpr;
    }

    public List<ISQLColumnConstraint> getColumnConstraints() {
        return columnConstraints;
    }
}
