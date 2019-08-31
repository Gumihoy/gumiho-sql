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

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * 赋值 expr
 * <p>
 * column = value
 * column := value
 * <p>
 * https://dev.mysql.com/doc/refman/8.0/en/insert.html
 * <p>
 * <p>
 * { { (column [, column ]...) = (subquery)
 *   | column = { expr | (subquery) | DEFAULT }
 *   | VALUE (t_alias) = { expr | (subquery) }
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/UPDATE.html#GUID-027A462D-379D-4E35-8611-410F3AC8FDA5
 *
 * @author kent onCondition 2018/3/22.
 */
public class SQLAssignmentExpr extends AbstractSQLExpr {

    private ISQLExpr column;

    private Operator operator = Operator.ASSIGN;

    private ISQLExpr value;

    public SQLAssignmentExpr() {
    }

    public SQLAssignmentExpr(ISQLExpr column, ISQLExpr value) {
        this(column, Operator.ASSIGN, value);
    }

    public SQLAssignmentExpr(ISQLExpr column, Operator operator, ISQLExpr value) {
        setColumn(column);
        this.operator = operator;
        setValue(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, column);
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLAssignmentExpr clone() {
        ISQLExpr columnClone = this.column.clone();
        ISQLExpr valueClone = this.value.clone();

        SQLAssignmentExpr x = new SQLAssignmentExpr(columnClone, valueClone);
        super.clone();
        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == column) {
            setColumn(target);
            return true;
        }
        if (source == value) {
            setValue(target);
            return true;
        }
        return false;
    }

    public ISQLExpr getColumn() {
        return column;
    }

    public void setColumn(ISQLExpr column) {
        setChildParent(column);
        this.column = column;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
    }


    public enum Operator implements ISQLASTEnum {

        ASSIGN("="),
        ASSIGN2(":="),

        ;

        public final String name;

        Operator(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public String lower() {
            return name;
        }

        @Override
        public String upper() {
            return name;
        }
    }
}
