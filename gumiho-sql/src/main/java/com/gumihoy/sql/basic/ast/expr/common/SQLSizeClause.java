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

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * integer [ K | M | G | T | P | E ]
 * <p>
 * size_clause
 * <p>
 * https://docs.oracle.com/database/121/SQLRF/clauses008.htm#SQLRF30012
 *
 * @author kent onCondition 2018/3/16.
 */
public class SQLSizeClause extends AbstractOracleExpr {

    protected ISQLExpr value;
    protected Type type;

    public SQLSizeClause(ISQLExpr value) {
        this(value, null);
    }

    public SQLSizeClause(ISQLExpr value, Type type) {
        setValue(value);
        this.type = type;
    }

    @Override
    public void accept0(IOracleASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLSizeClause clone() {
        ISQLExpr valueClone = this.value.clone();
        SQLSizeClause x = new SQLSizeClause(valueClone, this.type);
        return x;
    }


    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type implements ISQLASTEnum {
        /**
         * KiloBytes
         */
        K("k","K"),
        /**
         * MegaBytes
         */
        M("m", "M"),
        // GigaBytes
        G("g", "G"),
        // TeraBytes
        T("t", "T"),
        // PetaBytes
        P("p", "P"),
        // ExaBytes
        E("e", "E");

        public final String lower;
        public final String upper;

        Type(String lower, String upper) {
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
}
