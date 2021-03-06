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
package com.gumihoy.sql.basic.ast.expr.literal.string;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;

/**
 * TextLiteral
 * value : 'xx'
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#general%20literal
 * https://docs.oracle.com/database/121/SQLRF/sql_elements003.htm#SQLRF00218
 *
 * @author kent onCondition 2018/3/19.
 */
public abstract class AbstractSQLStringLiteral extends AbstractSQLExpr implements ISQLStringLiteral {

    private String value;

    public AbstractSQLStringLiteral(String value) {
        this.value = value;
    }

    @Override
    public AbstractSQLStringLiteral clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
