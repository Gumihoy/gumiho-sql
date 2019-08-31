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
import com.gumihoy.sql.basic.ast.expr.literal.ISQLLiteral;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * NULL
 * <p>
 * https://dev.mysql.com/doc/refman/8.0/en/null-values.html
 *
 * NULL
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Simple-Expressions.html#GUID-0E033897-60FB-40D7-A5F3-498B0FCC31B0
 *
 * @author kent on 2018/3/20.
 */
public class SQLNullExpr extends AbstractSQLExpr implements ISQLLiteral {

    public static SQLNullExpr of() {
        return new SQLNullExpr();
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLNullExpr clone() {
        SQLNullExpr x = new SQLNullExpr();
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLNullExpr x) {
        super.cloneTo(x);
    }

    @Override
    public Object getValue() {
        return null;
    }
}
