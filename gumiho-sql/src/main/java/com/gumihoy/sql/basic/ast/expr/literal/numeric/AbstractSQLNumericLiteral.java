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
package com.gumihoy.sql.basic.ast.expr.literal.numeric;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.literal.ISQLLiteral;

import java.math.BigDecimal;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#unsigned%20numeric%20literal
 *
 * @author kent onCondition 2018/3/19.
 */
public abstract class AbstractSQLNumericLiteral extends AbstractSQLExpr implements ISQLNumericLiteral {

    protected String source;
    protected Number value;

    public AbstractSQLNumericLiteral() {
    }

    public AbstractSQLNumericLiteral(String source) {
        this.source = source;
    }

    public AbstractSQLNumericLiteral(Number value) {
        this.value = value;
    }

    @Override
    public AbstractSQLNumericLiteral clone() {
        throw new UnsupportedOperationException();
    }

    public int compareTo(Number value) {
        BigDecimal decimal = new BigDecimal(this.value.toString());
        BigDecimal valueDecimal = new BigDecimal(value.toString());
        return decimal.compareTo(valueDecimal);
    }

    public boolean lt(Number value) {
        int compareTo = compareTo(value);
        return compareTo == -1;
    }

    public boolean eq(Number value) {
        int compareTo = compareTo(value);
        return compareTo == 0;
    }

    public boolean gt(Number value) {
        int compareTo = compareTo(value);
        return compareTo == 1;
    }

    public String getSource() {
        return source;
    }

    @Override
    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }
}
