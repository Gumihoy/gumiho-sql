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
 * [ CONSTRAINT constraint_name ] NOT NULL [ constraint_state ]
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#column%20constraint
 *
 * @author kent onCondition 2018/3/13.
 */
public class SQLNotNullColumnConstraint extends AbstractSQLConstraint implements ISQLNullColumnConstraint {


    public SQLNotNullColumnConstraint() {
    }

    public SQLNotNullColumnConstraint(ISQLName name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, options);
        }
    }

    @Override
    public SQLNotNullColumnConstraint clone() {
        SQLNotNullColumnConstraint x = new SQLNotNullColumnConstraint();
        return x;
    }
}
