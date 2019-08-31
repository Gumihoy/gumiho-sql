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
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.operator.SQLBinaryOperatorExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * WHERE condition
 *
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#where%20clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 *
 * @author kent onCondition 2018/2/8.
 */
public class SQLWhereClause extends AbstractSQLExpr {

    protected ISQLExpr condition;

    public SQLWhereClause(ISQLExpr condition) {
        setCondition(condition);
    }

    public static SQLWhereClause of(ISQLExpr condition) {
        return new SQLWhereClause(condition);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, condition);
        }
    }

    @Override
    public SQLWhereClause clone() {
        return null;
    }


    public void andCondition(ISQLExpr condition) {
        if (condition == null) {
            return;
        }
        condition = SQLBinaryOperatorExpr.and(this.condition, condition);
        setCondition(condition);
    }

    public void orCondition(ISQLExpr condition) {
        if (condition == null) {
            return;
        }
        condition = SQLBinaryOperatorExpr.or(this.condition, condition);
        setCondition(condition);
    }




    public ISQLExpr getCondition() {
        return condition;
    }

    public void setCondition(ISQLExpr condition) {
        setChildParent(condition);
        this.condition = condition;
    }
}
