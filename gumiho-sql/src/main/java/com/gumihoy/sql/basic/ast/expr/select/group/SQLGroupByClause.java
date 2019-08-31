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
package com.gumihoy.sql.basic.ast.expr.select.group;

import com.gumihoy.sql.basic.ast.enums.SQLSetQuantifier;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.SQLObjectType;
import com.gumihoy.sql.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * GROUP BY [ <set quantifier> ] <grouping element list>
 * <set quantifier>    ::=   DISTINCT | ALL
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#group%20by%20clause
 * <p>
 * https://dev.mysql.com/doc/refman/8.0/en/select.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/group_by_clause.html
 *
 * @author kent onCondition 2018/2/8.
 */
public class SQLGroupByClause extends AbstractSQLExpr {

    /**
     * true: Group By XX Having XX
     * false: Having XX Group By XX
     */
    protected boolean order = true;

    protected SQLSetQuantifier quantifier;

    protected final List<SQLGroupByItem> items = new ArrayList<>();

    protected boolean withRollup;

    protected SQLHavingClause havingClause;


    public SQLGroupByClause() {
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            if (order) {
                this.acceptChild(visitor, items);
                this.acceptChild(visitor, havingClause);
            } else {
                this.acceptChild(visitor, havingClause);
                this.acceptChild(visitor, items);
            }
        }
    }


    @Override
    public SQLGroupByClause clone() {
        SQLGroupByClause x = new SQLGroupByClause();

        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLGroupByClause x) {
        super.cloneTo(x);

        x.quantifier = this.quantifier;

        for (SQLGroupByItem item : this.items) {
            SQLGroupByItem itemClone = item.clone();
            x.addItem(itemClone);
        }

        SQLHavingClause havingClauseClone = this.havingClause.clone();
        x.setHavingClause(havingClauseClone);
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public SQLSetQuantifier getQuantifier() {
        return quantifier;
    }

    public void setQuantifier(SQLSetQuantifier quantifier) {
        this.quantifier = quantifier;
    }

    public List<SQLGroupByItem> getItems() {
        return items;
    }

    public void addItem(SQLGroupByItem item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }

    public boolean isWithRollup() {
        return withRollup;
    }

    public void setWithRollup(boolean withRollup) {
        this.withRollup = withRollup;
    }

    public SQLHavingClause getHavingClause() {
        return havingClause;
    }

    public void setHavingClause(SQLHavingClause havingClause) {
        setChildParent(havingClause);
        this.havingClause = havingClause;
    }


    public static class SQLGroupByItem extends AbstractSQLExpr {

        protected ISQLExpr expr;

        public SQLGroupByItem(String name) {
            setExpr(SQLUtils.ofName(name));
        }

        public SQLGroupByItem(ISQLExpr expr) {
            setExpr(expr);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, expr);
            }
        }

        @Override
        public SQLGroupByItem clone() {
            ISQLExpr exprClone = this.expr.clone();
            SQLGroupByItem x = new SQLGroupByItem(exprClone);
            return x;
        }

        @Override
        public SQLObjectType getObjectType() {
            return null;
        }

        public ISQLExpr getExpr() {
            return expr;
        }

        public void setExpr(ISQLExpr expr) {
            setChildParent(expr);
            this.expr = expr;
        }
    }


}
