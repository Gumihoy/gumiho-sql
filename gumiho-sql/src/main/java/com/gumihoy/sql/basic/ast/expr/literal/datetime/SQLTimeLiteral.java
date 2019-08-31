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
 * TIME 'xxx'
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#time%20literal
 *
 * @author kent onCondition 2018/3/20.
 */
public class SQLTimeLiteral extends SQLDatetimeLiteral {


    public SQLTimeLiteral() {
    }

    public SQLTimeLiteral(String value) {
        super(value);
    }


    public SQLTimeLiteral(ISQLExpr value) {
        super(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLTimeLiteral clone() {
        SQLTimeLiteral x = new SQLTimeLiteral();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLTimeLiteral x) {
        super.cloneTo(x);
    }
}
