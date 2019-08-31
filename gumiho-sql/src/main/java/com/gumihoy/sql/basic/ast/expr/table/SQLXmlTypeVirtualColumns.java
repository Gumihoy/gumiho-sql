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
package com.gumihoy.sql.basic.ast.expr.table;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * VIRTUAL COLUMNS ( column AS (expr) [, column AS (expr) ]... )
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/3/25.
 */
public class SQLXmlTypeVirtualColumns extends AbstractSQLExpr {

    protected final List<SQLItem> items = new ArrayList<>();

    @Override
    public void accept0(ISQLASTVisitor visitor) {
//        if(visitor.visit(this)) {
//            this.acceptChild(visitor, items);
//        }
    }

    public List<SQLItem> getItems() {
        return items;
    }

    public void addItem(SQLItem item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }

    /**
     * column AS (expr)
     */
    public static class SQLItem extends AbstractSQLExpr {
        protected ISQLName column;
        protected ISQLExpr expr;

        public SQLItem(ISQLName column, ISQLExpr expr) {
            setColumn(column);
            setExpr(expr);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, column);
//                this.acceptChild(visitor, expr);
//            }
        }

        public ISQLName getColumn() {
            return column;
        }

        public void setColumn(ISQLName column) {
            setChildParent(column);
            this.column = column;
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
