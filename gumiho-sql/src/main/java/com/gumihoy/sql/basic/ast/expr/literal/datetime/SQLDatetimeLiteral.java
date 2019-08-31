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

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.literal.ISQLLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLStringLiteral;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#datetime%20literal
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/date-and-time-literals.html
 * <p>
 * https://docs.oracle.com/database/121/SQLRF/sql_elements003.htm#SQLRF51062
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Literals.html#GUID-8F4B3F82-8821-4071-84D6-FBBA21C05AC1
 *
 * @author kent onCondition 2018/3/20.
 */
public abstract class SQLDatetimeLiteral extends AbstractSQLExpr implements ISQLLiteral {

    protected ISQLExpr value;

    public SQLDatetimeLiteral() {
    }

    public SQLDatetimeLiteral(String value) {
        setValue(SQLStringLiteral.of(value));
    }

    public SQLDatetimeLiteral(ISQLExpr value) {
        if (value == null) {
            throw new IllegalArgumentException("value is null.");
        }
        setValue(value);
    }

    @Override
    public SQLDatetimeLiteral clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    public void cloneTo(SQLDatetimeLiteral x) {
        super.cloneTo(x);

        ISQLExpr valueClone = value.clone();
        x.setValue(valueClone);
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (value == source) {
            setValue(target);
            return true;
        }
        return false;
    }

    @Override
    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
    }
}
