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

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.math.BigDecimal;

/**
 * 25.607
 * NUMBER Literals
 * <p>
 * https://docs.oracle.com/database/121/SQLRF/sql_elements003.htm#SQLRF00220
 *
 * @author kent onCondition 2018/3/19.
 */
public class SQLDecimalLiteral extends AbstractSQLNumericLiteral {

    public SQLDecimalLiteral(String source) {
        super(source);
        this.value = new BigDecimal(source);
    }

    public SQLDecimalLiteral(BigDecimal value) {
        this.source = value.toString();
        this.value = value;
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    public static void main(String[] args) {
        Number number = new BigDecimal("1.2");
        System.out.println(number.toString());
    }
}
