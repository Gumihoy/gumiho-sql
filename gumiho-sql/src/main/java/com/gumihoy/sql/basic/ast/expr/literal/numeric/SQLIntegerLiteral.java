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

import com.gumihoy.sql.basic.ast.enums.SQLBinaryOperator;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.operator.SQLBinaryOperatorExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.math.BigInteger;

/**
 * Integer Literals
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#signed%20numeric%20literal
 * https://docs.oracle.com/database/121/SQLRF/sql_elements003.htm#SQLRF00220
 *
 * @author kent on 2018/3/19.
 */
public class SQLIntegerLiteral extends AbstractSQLNumericLiteral {

    public SQLIntegerLiteral(String source) {
        super(source);
        this.value = new BigInteger(source);
    }

    public SQLIntegerLiteral(long value) {
        this.source = String.valueOf(value);
        this.value = BigInteger.valueOf(value);
    }

    public SQLIntegerLiteral(Number value) {
        this.source = value.toString();
        this.value = value;
    }

    public static SQLIntegerLiteral of(String value) {
        return new SQLIntegerLiteral(new BigInteger(value));
    }

    public static SQLIntegerLiteral of(long value) {
        return new SQLIntegerLiteral(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLIntegerLiteral clone() {
        SQLIntegerLiteral x = new SQLIntegerLiteral(this.value);
        return x;
    }

    public static ISQLExpr subtract(ISQLExpr left, ISQLExpr right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        if (left instanceof SQLIntegerLiteral
                && right instanceof SQLIntegerLiteral) {
            long val = ((SQLIntegerLiteral) left).getLongValue() - ((SQLIntegerLiteral) right).getLongValue();
            return new SQLIntegerLiteral(val);
        } else {
            return new SQLBinaryOperatorExpr(left, SQLBinaryOperator.Sub, right);
        }
    }

    public static ISQLExpr increment(ISQLExpr x) {
        if (x instanceof SQLIntegerLiteral) {
            long val = ((SQLIntegerLiteral) x).getLongValue() + 1;
            return SQLIntegerLiteral.of(val);
        }

        return new SQLBinaryOperatorExpr(x.clone(), SQLBinaryOperator.Add, SQLIntegerLiteral.of(1));
    }

    public static ISQLExpr decrement(ISQLExpr x) {
        if (x instanceof SQLIntegerLiteral) {
            long val = ((SQLIntegerLiteral) x).getLongValue() - 1;
            return SQLIntegerLiteral.of(val);
        }

        return new SQLBinaryOperatorExpr(x.clone(), SQLBinaryOperator.Sub, SQLIntegerLiteral.of(1));
    }

    public static SQLIntegerLiteral min() {
        return new SQLIntegerLiteral(Long.MIN_VALUE);
    }

    public static SQLIntegerLiteral max() {
        return new SQLIntegerLiteral(Long.MAX_VALUE);
    }


    public long getLongValue() {
        return value.longValue();
    }

    public String getStringValue() {
        return value.toString();
    }

    public void setValue(BigInteger value) {

        this.value = value;
    }
}
