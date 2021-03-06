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
 * ASC | DESC
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#ordering%20specification
 *
 * @author kent onCondition 2018/3/26.
 */
public enum SQLOrderingSpecification implements ISQLASTEnum {

    ASC("ASC", "ASC"),
    DESC("DESC", "DESC");

    public final String lower;
    public final String upper;

    SQLOrderingSpecification(String lower, String upper) {
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
