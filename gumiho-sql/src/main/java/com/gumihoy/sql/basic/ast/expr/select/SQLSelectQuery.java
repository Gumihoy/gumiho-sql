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


import com.gumihoy.sql.basic.ast.enums.SQLSetQuantifier;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLFromClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLWhereClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLWindowClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLWithClause;
import com.gumihoy.sql.basic.ast.expr.select.group.SQLGroupByClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.ISQLLimitClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#query%20specification
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 *
 * @author kent onCondition 2018/3/20.
 */
public class SQLSelectQuery extends AbstractSQLSelectQuery implements ISQLSelectQuery {

    protected SQLWithClause withClause;

    protected SQLSetQuantifier setQuantifier;

    protected List<SQLSelectItem> selectItems = new ArrayList<>();

    protected boolean bulkCollect = false;

    protected final List<SQLSelectTargetItem> selectTargetItems = new ArrayList<>();

    protected SQLFromClause fromClause;

    protected SQLWhereClause whereClause;

    protected SQLGroupByClause groupByClause;

    protected SQLWindowClause windowClause;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, withClause);
            this.acceptChild(visitor, selectItems);
            this.acceptChild(visitor, selectTargetItems);
            this.acceptChild(visitor, fromClause);
            this.acceptChild(visitor, whereClause);
            this.acceptChild(visitor, windowClause);

            this.acceptChild(visitor, orderByClause);
            this.acceptChild(visitor, limitClause);
            this.acceptChild(visitor, lockClause);
        }
    }

    @Override
    public SQLSelectQuery clone() {
        SQLSelectQuery x = new SQLSelectQuery();

        x.setQuantifier = this.setQuantifier;

        for (SQLSelectItem selectItem : selectItems) {
            SQLSelectItem selectItemClone = selectItem.clone();
            x.addSelectItem(selectItemClone);
        }

        x.bulkCollect = this.bulkCollect;

        for (SQLSelectTargetItem selectTargetItem : selectTargetItems) {
            SQLSelectTargetItem selectTargetItemClone = selectTargetItem.clone();
            x.addSelectTargetItem(selectTargetItemClone);
        }

        SQLFromClause fromClauseClone = this.fromClause.clone();
        x.setFromClause(fromClauseClone);

        if (this.whereClause != null) {
            SQLWhereClause whereClauseClone = this.whereClause.clone();
            x.setWhereClause(whereClauseClone);
        }


        if (this.groupByClause != null) {
            SQLGroupByClause groupByClauseClone = this.groupByClause.clone();
            x.setGroupByClause(groupByClauseClone);
        }


        if (this.windowClause != null) {
            SQLWindowClause windowClauseClone = this.windowClause.clone();
            x.setWindowClause(windowClauseClone);
        }

        if (this.orderByClause != null) {
            SQLOrderByClause orderByClauseClone = this.orderByClause.clone();
            x.setOrderByClause(orderByClauseClone);
        }
        if (this.limitClause != null) {
            ISQLLimitClause limitClauseClone = this.limitClause.clone();
            x.setLimitClause(limitClauseClone);
        }
        if (this.lockClause != null) {
            ISQLLockClause lockClauseClone = this.lockClause.clone();
            x.setLockClause(lockClauseClone);
        }

        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLSelectQuery x) {
        super.cloneTo(x);

        SQLWithClause withClauseClone = this.withClause;
        x.setWithClause(withClauseClone);

        x.setQuantifier = this.setQuantifier;

        for (SQLSelectItem selectItem : this.selectItems) {
            SQLSelectItem selectItemClone = selectItem.clone();
            x.addSelectItem(selectItemClone);
        }

        SQLFromClause fromClauseClone = this.fromClause;
        if (fromClauseClone != null) {
            x.setFromClause(fromClauseClone);
        }

        SQLWhereClause whereClauseClone = this.whereClause;
        if (whereClauseClone != null) {
            x.setWhereClause(whereClauseClone);
        }

        SQLGroupByClause groupByClauseClone = this.groupByClause;
        if (groupByClauseClone != null) {
            x.setGroupByClause(groupByClauseClone);
        }

        SQLWindowClause windowClauseClone = this.windowClause;
        if (windowClauseClone != null) {
            x.setWindowClause(windowClauseClone);
        }

    }


    public SQLSelectItem findSelectItem(String name) {
        if (name == null) {
            return null;
        }
        return findSelectItem(SQLUtils.ofName(name));
    }

    public SQLSelectItem findSelectItem(long hash) {
        for (SQLSelectItem item : this.selectItems) {
            if (item.match(hash)) {
                return item;
            }
        }
        return null;
    }

    public SQLSelectItem findSelectItem(ISQLExpr expr) {
        if (expr == null) {
            return null;
        }
        for (SQLSelectItem item : this.selectItems) {
            if (item.match(expr)) {
                return item;
            }
        }
        return null;
    }

    public void setWhereCondition(ISQLExpr condition) {
        if (condition == null) {
            return;
        }
        SQLWhereClause whereClause = SQLWhereClause.of(condition);
        setChildParent(whereClause);
        this.whereClause = whereClause;
    }

    public void andWhereCondition(ISQLExpr condition) {
        if (condition == null) {
            return;
        }
        if (this.whereClause == null) {
            setWhereCondition(condition);
            return;
        }

        this.whereClause.andCondition(condition);
    }

    public void orWhereCondition(ISQLExpr condition) {
        if (condition == null) {
            return;
        }
        if (this.whereClause == null) {
            setWhereCondition(condition);
            return;
        }
        this.whereClause.orCondition(condition);
    }





    public SQLWithClause getWithClause() {
        return withClause;
    }

    public void setWithClause(SQLWithClause withClause) {
        this.withClause = withClause;
    }

    public SQLSetQuantifier getSetQuantifier() {
        return setQuantifier;
    }

    public void setSetQuantifier(SQLSetQuantifier setQuantifier) {
        this.setQuantifier = setQuantifier;
    }

    public List<SQLSelectItem> getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(List<SQLSelectItem> selectItems) {
        this.selectItems = selectItems;
    }

    public void addSelectItem(SQLSelectItem selectItem) {
        if (selectItem == null) {
            return;
        }
        setChildParent(selectItem);
        this.selectItems.add(selectItem);
    }

    public boolean isBulkCollect() {
        return bulkCollect;
    }

    public void setBulkCollect(boolean bulkCollect) {
        this.bulkCollect = bulkCollect;
    }

    public List<SQLSelectTargetItem> getSelectTargetItems() {
        return selectTargetItems;
    }

    public void addSelectTargetItem(SQLSelectTargetItem item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.selectTargetItems.add(item);
    }

    public void addSelectTargetItem(ISQLExpr expr) {
        if (expr == null) {
            return;
        }
        SQLSelectTargetItem item = null;
        if (expr instanceof SQLSelectTargetItem) {
            item = (SQLSelectTargetItem) expr;
        } else {
            item = new SQLSelectTargetItem(expr);
        }
        setChildParent(item);
        this.selectTargetItems.add(item);
    }

    public SQLFromClause getFromClause() {
        return fromClause;
    }

    public void setFromClause(SQLFromClause fromClause) {
        setChildParent(fromClause);
        this.fromClause = fromClause;
    }

    public SQLWhereClause getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(SQLWhereClause whereClause) {
        setChildParent(whereClause);
        this.whereClause = whereClause;
    }

    public SQLGroupByClause getGroupByClause() {
        return groupByClause;
    }

    public void setGroupByClause(SQLGroupByClause groupByClause) {
        setChildParent(groupByClause);
        this.groupByClause = groupByClause;
    }

    public SQLWindowClause getWindowClause() {
        return windowClause;
    }

    public void setWindowClause(SQLWindowClause windowClause) {
        setChildParent(windowClause);
        this.windowClause = windowClause;
    }


}
