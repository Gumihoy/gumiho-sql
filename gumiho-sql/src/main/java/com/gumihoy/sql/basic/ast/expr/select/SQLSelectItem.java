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
package com.gumihoy.sql.basic.ast.expr.select;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.util.SQLUtils;

/**
 * expr (as? alias)?
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#order%20by%20clause
 *
 * @author kent onCondition 2018/2/8.
 */
public class SQLSelectItem extends AbstractSQLExpr {

    private ISQLExpr expr;

    private boolean as;

    private ISQLExpr alias;

    public SQLSelectItem() {
    }

    public SQLSelectItem(ISQLExpr expr) {
        setExpr(expr);
    }

    public SQLSelectItem(ISQLExpr expr, ISQLExpr alias) {
        setExpr(expr);
        setAlias(alias);
    }

    public SQLSelectItem(ISQLExpr expr, boolean as, ISQLExpr alias) {
        setExpr(expr);
        this.as = as;
        setAlias(alias);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
            this.acceptChild(visitor, alias);
        }
    }


    @Override
    public SQLSelectItem clone() {
        SQLSelectItem x = new SQLSelectItem();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLSelectItem x) {
        super.cloneTo(x);
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == expr) {
            this.setExpr(target);
            return true;
        }

        if (source == alias) {
            this.setAlias(target);
            return true;
        }

        return false;
    }

    public boolean match(ISQLExpr expr) {
        if (expr == null) {
            return false;
        }

        boolean match = SQLUtils.equalsIgnoreCase(alias, expr, false);
        if (match) {
            return true;
        }

        match = SQLUtils.equalsIgnoreCase(this.expr, expr, false);
        if (match) {
            return true;
        }

        return false;
    }

    public boolean match(long hash) {
        if (alias instanceof ISQLName
                && ((ISQLName) alias).lowerHash() == hash) {
            return true;
        }

        if (expr instanceof ISQLName
                && ((ISQLName) expr).lowerHash() == hash) {
            return true;
        }

        return false;
    }


    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }

    public boolean isAs() {
        return as;
    }

    public void setAs(boolean as) {
        this.as = as;
    }

    public ISQLExpr getAlias() {
        return alias;
    }

    public void setAlias(ISQLExpr alias) {
        setChildParent(alias);
        this.alias = alias;
    }
}
