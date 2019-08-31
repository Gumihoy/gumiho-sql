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
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.AbstractSQLConstraint;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#check%20constraint%20definition
 * <p>
 * CHECK (expr)
 * https://dev.mysql.com/doc/refman/5.6/en/create-table.html
 * <p>
 * [ CONSTRAINT constraint_name ] CHECK (condition) [ constraint_state ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
 *
 * @author kent onCondition 2018/3/13.
 */
public class SQLCheckTableConstraint extends AbstractSQLConstraint implements ISQLCheckTableConstraint {

    protected ISQLExpr condition;

    public SQLCheckTableConstraint() {
    }

    public SQLCheckTableConstraint(ISQLExpr condition) {
        this(null, condition);
    }

    public SQLCheckTableConstraint(ISQLName name, ISQLExpr condition) {
        super(name);
        setCondition(condition);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, condition);
            this.acceptChild(visitor, options);
        }
    }

    @Override
    public SQLCheckTableConstraint clone() {
        SQLCheckTableConstraint x = new SQLCheckTableConstraint();
        this.cloneTo(x);
        ISQLExpr conditionClone = condition.clone();
        x.setCondition(conditionClone);
        return x;
    }

    public ISQLExpr getCondition() {
        return condition;
    }

    public void setCondition(ISQLExpr condition) {
        setChildParent(condition);
        this.condition = condition;
    }
}
