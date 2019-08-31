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
import com.gumihoy.sql.basic.ast.expr.insert.ISQLInsertValuesClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * VALUES expr, expr
 * expr: listExpr
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#contextually%20typed%20table%20value%20constructor
 * <p>
 * VALUES (expr), expr
 * [ ORDER BY sort_expression [ ASC | DESC | USING operator ] [, ...] ]
 * [ LIMIT { count | ALL } ]
 * [ OFFSET start [ ROW | ROWS ] ]
 * [ FETCH { FIRST | NEXT } [ count ] { ROW | ROWS } ONLY ]
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/insert.html
 * <p>
 * https://www.postgresql.org/docs/devel/static/sql-values.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/INSERT.html#GUID-903F8043-0254-4EE9-ACC1-CB8AC0AF3423
 *
 * @author kent onCondition 2018/2/8.
 */
public class SQLValuesClause extends AbstractSQLExpr implements ISQLInsertValuesClause {

    protected Type type = Type.VALUES;

    protected final List<SQLValuesItem> items = new ArrayList<>();

//    protected SQLOrderByClause orderByClause;

//    protected ISQLLimitClause limitClause;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, items);
//            this.acceptChild(visitor, orderByClause);
//            this.acceptChild(visitor, limitClause);
        }
    }

    @Override
    public SQLValuesClause clone() {
        SQLValuesClause x = new SQLValuesClause();

        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLValuesClause x) {
        super.cloneTo(x);

//        x.values = this.values;

        for (SQLValuesItem value : this.items) {
            SQLValuesItem valueClone = value.clone();
            x.addValue(valueClone);
        }

//        if (this.orderByClause != null) {
//            SQLOrderByClause orderByClauseClone = this.orderByClause.clone();
//            x.setOrderByClause(orderByClauseClone);
//        }
//
//        if (this.limitClause != null) {
//            ISQLLimitClause limitClause = this.limitClause.clone();
//            x.setLimitClause(limitClause);
//        }

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<SQLValuesItem> getItems() {
        return items;
    }

    public void addValue(SQLValuesItem value) {
        if (value == null) {
            return;
        }
        setChildParent(value);
        this.items.add(value);
    }

//    public SQLOrderByClause getOrderByClause() {
//        return orderByClause;
//    }
//
//    public void setOrderByClause(SQLOrderByClause orderByClause) {
//        setChildParent(orderByClause);
//        this.orderByClause = orderByClause;
//    }
//
//    public ISQLLimitClause getLimitClause() {
//        return limitClause;
//    }
//
//    public void setLimitClause(ISQLLimitClause limitClause) {
//        setChildParent(limitClause);
//        this.limitClause = limitClause;
//    }


    public static class SQLValuesItem extends AbstractSQLExpr {

        protected ISQLExpr expr;

        public SQLValuesItem(ISQLExpr expr) {
            if (expr == null) {
                throw new IllegalArgumentException("expr is null.");
            }
            setExpr(expr);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, expr);
            }
        }

        @Override
        public SQLValuesItem clone() {
            ISQLExpr exprClone = expr.clone();
            SQLValuesItem x = new SQLValuesItem(exprClone);
            return x;
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            return false;
        }

        public ISQLExpr getExpr() {
            return expr;
        }

        public void setExpr(ISQLExpr expr) {
            setChildParent(expr);
            this.expr = expr;
        }
    }


    public enum Type implements ISQLASTEnum {

        VALUE("value", "VALUE"),
        VALUES("values", "VALUES"),
        ;

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
