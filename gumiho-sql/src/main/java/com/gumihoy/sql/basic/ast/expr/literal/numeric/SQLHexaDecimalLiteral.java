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
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.literal.ISQLLiteral;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.util.SQLUtils;

/**
 * x'XX' / X'XXX'  /0xXXX
 * <p>
 * https://dev.mysql.com/doc/refman/8.0/en/hexadecimal-literals.html
 *
 * @author kent on 2018/3/20.
 */
public class SQLHexaDecimalLiteral extends AbstractSQLExpr implements ISQLLiteral {

    protected SQLPrefix prefix = SQLPrefix.X;

    private String value;

    public SQLHexaDecimalLiteral(String value) {
        setValue(value);
    }

    public SQLHexaDecimalLiteral(SQLPrefix prefix, String value) {
        this.prefix = prefix;
        setValue(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLHexaDecimalLiteral clone() {
        SQLHexaDecimalLiteral x = new SQLHexaDecimalLiteral(this.prefix, this.value);
        return x;
    }

//    @Override
//    public long hash() {
//        return 0;
//    }
//
//    @Override
//    public long lowerHash() {
//        return 0;
//    }

    public SQLPrefix getPrefix() {
        return prefix;
    }

    public void setPrefix(SQLPrefix prefix) {
        this.prefix = prefix;
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value == null) {
            return;
        }
        this.prefix = ofByValue(value);
        this.value = getValue(value);
    }

    private static SQLPrefix ofByValue(String value) {
        if (value == null) {
            return SQLPrefix.X;
        }

        char c0 = value.charAt(0);

        if (c0 == 'x'
                || c0 == 'X') {
            return SQLPrefix.X;
        }

        char c1 = value.charAt(1);
        if (c0 == '0'
                && c1 == 'x') {
            return SQLPrefix.ZERO_X;
        }

        return SQLPrefix.X;
    }

    private static String getValue(String value) {
        if (value == null) {
            return null;
        }

        String newValue = value;

        char c0 = value.charAt(0);

        if (c0 == 'x'
                || c0 == 'X') {
            newValue = value.substring(1, value.length());

        } else {

            char c1 = value.charAt(1);
            if (c0 == '0'
                    && c1 == 'x') {
                newValue = value.substring(2, value.length());
            }
        }

        newValue = SQLUtils.removeSingleQuote(newValue);

        return newValue;
    }


    public enum SQLPrefix implements ISQLASTEnum {
        X("x", "X"),
        ZERO_X("0x", "0x");
        public final String lower;
        public final String upper;

        SQLPrefix(String lower, String upper) {
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
