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
package com.gumihoy.sql.basic.ast.expr.table.element.constraint.table;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kent onCondition 2018/3/13.
 */
public abstract class AbstractSQLUniqueTableConstraint extends AbstractSQLTableConstraint implements ISQLUniqueTableConstraint {

    protected final List<SQLColumn> columns = new ArrayList<>();

    public AbstractSQLUniqueTableConstraint() {
    }

    public AbstractSQLUniqueTableConstraint(ISQLName name) {
        super(name);
    }

    @Override
    public AbstractSQLUniqueTableConstraint clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }


    public void cloneTo(AbstractSQLUniqueTableConstraint x) {
        super.cloneTo(x);

        for (ISQLExpr column : this.columns) {

        }
    }

    public List<SQLColumn> getColumns() {
        return columns;
    }

    public void addColumn(SQLColumn column) {
        if (column == null) {
            return;
        }
        setChildParent(column);
        this.columns.add(column);
    }

    public void addColumn(ISQLExpr expr) {
        if (expr == null) {
            return;
        }
        SQLColumn column;
        if (expr instanceof SQLColumn) {
            column = (SQLColumn) expr;
        } else {
            column = SQLColumn.of(expr);
        }
        setChildParent(column);
        this.columns.add(column);
    }
}
