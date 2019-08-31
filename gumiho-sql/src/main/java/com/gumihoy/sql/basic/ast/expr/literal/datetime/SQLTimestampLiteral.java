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
package com.gumihoy.sql.basic.ast.expr.literal.datetime;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * TIMESTAMP '1997-01-31 09:26:50.124' [AT TIME ZONE 'US/Pacific']
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#datetime%20literal
 * <p>
 * https://docs.oracle.com/database/121/SQLRF/sql_elements003.htm#SQLRF51062
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Literals.html#GUID-8F4B3F82-8821-4071-84D6-FBBA21C05AC1
 *
 * @author kent on 2018/3/20.
 */
public class SQLTimestampLiteral extends SQLDatetimeLiteral {

    protected ISQLExpr atTimeZone;

    public SQLTimestampLiteral() {
    }

    public SQLTimestampLiteral(String value) {
        this();
    }

    public SQLTimestampLiteral(ISQLExpr value, ISQLExpr atTimeZone) {
        super(value);
        setAtTimeZone(atTimeZone);
    }

    public SQLTimestampLiteral(ISQLExpr value) {
        super(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLTimestampLiteral clone() {
        SQLTimestampLiteral x = new SQLTimestampLiteral();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLTimestampLiteral x) {
        super.cloneTo(x);

        x.atTimeZone = this.atTimeZone;
    }


    public ISQLExpr getAtTimeZone() {
        return atTimeZone;
    }

    public void setAtTimeZone(ISQLExpr atTimeZone) {
        setChildParent(atTimeZone);
        this.atTimeZone = atTimeZone;
    }
}
