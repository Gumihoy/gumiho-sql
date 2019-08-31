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
package com.gumihoy.sql.basic.ast.expr.select.order;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.SQLObjectType;
import com.gumihoy.sql.util.SQLUtils;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#sort%20specification
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 *
 * @author kent onCondition 2018/2/8.
 */
public class SQLOrderByItem extends AbstractSQLExpr {

    private ISQLExpr sortKey;

    private ISQLOrderingSpecification orderingSpecification;

    private SQLNullOrderType nullOrdering;

    public SQLOrderByItem(String sortKey) {
        if (sortKey == null) {
            throw new IllegalArgumentException("sortKey is null.");
        }
        setSortKey(SQLUtils.ofName(sortKey));
    }

    public SQLOrderByItem(ISQLExpr sortKey) {
        this(sortKey, null, null);
    }

    public SQLOrderByItem(ISQLExpr sortKey, ISQLOrderingSpecification orderingSpecification) {
        this(sortKey, orderingSpecification, null);
    }

    public SQLOrderByItem(ISQLExpr sortKey, ISQLOrderingSpecification orderingSpecification, SQLNullOrderType nullOrdering) {
        setSortKey(sortKey);
        setOrderingSpecification(orderingSpecification);
        this.nullOrdering = nullOrdering;
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, sortKey);
            this.acceptChild(visitor, orderingSpecification);
        }
    }

    @Override
    public SQLOrderByItem clone() {
        ISQLExpr sortKeyClone = this.sortKey.clone();
        SQLOrderByItem x = new SQLOrderByItem(sortKeyClone);

        if (this.orderingSpecification != null) {
            ISQLOrderingSpecification orderingSpecificationClone = this.orderingSpecification.clone();
            x.setOrderingSpecification(orderingSpecificationClone);
        }

        x.nullOrdering = this.nullOrdering;

        return x;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;
    }

    public ISQLExpr getSortKey() {
        return sortKey;
    }

    public void setSortKey(ISQLExpr sortKey) {
        setChildParent(sortKey);
        this.sortKey = sortKey;
    }

    public ISQLOrderingSpecification getOrderingSpecification() {
        return orderingSpecification;
    }

    public void setOrderingSpecification(ISQLOrderingSpecification orderingSpecification) {
        setChildParent(orderingSpecification);
        this.orderingSpecification = orderingSpecification;
    }

    public SQLNullOrderType getNullOrdering() {
        return nullOrdering;
    }

    public void setNullOrdering(SQLNullOrderType nullOrdering) {
        this.nullOrdering = nullOrdering;
    }


    public interface ISQLOrderingSpecification extends ISQLExpr {
        ISQLOrderingSpecification clone();
    }

    /**
     * ASC
     */
    public static class SQLASCOrderingSpecification extends AbstractSQLExpr implements ISQLOrderingSpecification {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLASCOrderingSpecification clone() {
            return new SQLASCOrderingSpecification();
        }
    }

    /**
     * DESC
     */
    public static class SQLDESCOrderingSpecification extends AbstractSQLExpr implements ISQLOrderingSpecification {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLDESCOrderingSpecification clone() {
            return new SQLDESCOrderingSpecification();
        }
    }

    /**
     * USING expr
     */
    public static class SQLUsingCOrderingSpecification extends AbstractSQLExpr implements ISQLOrderingSpecification {
        protected ISQLExpr expr;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, expr);
            }
        }

        @Override
        public SQLUsingCOrderingSpecification clone() {
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

    public enum SQLNullOrderType implements ISQLASTEnum {
        NULLS_FIRST("nulls first", "NULLS FIRST"),
        NULLS_LAST("nulls last", "NULLS LAST"),
        ;

        public final String lower;
        public final String upper;

        SQLNullOrderType(String lower, String upper) {
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
