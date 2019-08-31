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

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * Unicode character string literal
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#Unicode%20character%20string%20literal
 *
 * @author kent onCondition 2018/3/19.
 */
public class SQLUStringLiteral extends AbstractSQLStringLiteral {


    public SQLUStringLiteral(String value) {
        super(value);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLUStringLiteral clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }
}
