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
package com.gumihoy.sql.basic.ast.enums;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#set%20quantifier
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html
 * https://dev.mysql.com/doc/refman/8.0/en/select.html
 *
 * @author kent onCondition 2018/3/20.
 */
public enum SQLSetQuantifier implements ISQLASTEnum {

    DISTINCT("distinct", "DISTINCT"),
    ALL("all", "ALL"),
    UNIQUE("unique", "UNIQUE"),
    DISTINCTROW("distinctrow", "DISTINCTROW");

    public final String lower;
    public final String upper;


    SQLSetQuantifier(String lower, String upper) {
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public String toString() {
        return upper;
    }

    @Override
    public String lower() {
        return lower;
    }

    @Override
    public String upper() {
        return upper;
    }
}
