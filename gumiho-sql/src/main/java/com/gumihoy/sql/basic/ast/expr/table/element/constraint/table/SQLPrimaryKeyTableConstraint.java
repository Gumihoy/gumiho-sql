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

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#unique%20constraint%20definition
 * <p>
 * [CONSTRAINT [symbol]] PRIMARY KEY [index_type] (index_col_name,...) [index_option] ...
 * index_type: USING {BTREE | HASH}
 * https://dev.mysql.com/doc/refman/8.0/en/create-table.html
 * <p>
 * [ CONSTRAINT constraint_name ] PRIMARY KEY (column [, column ]...) [ constraint_state ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
 *
 * @author kent onCondition 2018/3/13.
 */
public class SQLPrimaryKeyTableConstraint extends AbstractSQLUniqueTableConstraint implements ISQLPrimaryKeyTableConstraint {

    public SQLPrimaryKeyTableConstraint() {
    }

    public SQLPrimaryKeyTableConstraint(ISQLName name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, options);
        }
    }

    @Override
    public SQLPrimaryKeyTableConstraint clone() {
        SQLPrimaryKeyTableConstraint x = new SQLPrimaryKeyTableConstraint();
        this.cloneTo(x);
        return x;
    }

}
