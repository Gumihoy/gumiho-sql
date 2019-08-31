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
package com.gumihoy.sql.basic.ast.expr.method.aggreate;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLWhereClause;
import com.gumihoy.sql.basic.ast.expr.method.AbstractSQLFunction;
import com.gumihoy.sql.basic.ast.expr.method.window.SQLOverClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * functionName <left paren> <function value expression list> <right paren>
 * <within group specification> [ <filter clause> ]
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#aggregate%20function
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Aggregate-Functions.html#GUID-62BE676B-AF18-4E63-BD14-25206FEA0848
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CUME_DIST.html#GUID-B12C577C-A63C-4D19-8E18-FCCBBFBF8278
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/FIRST.html#GUID-85AB9246-0E0A-44A1-A7E6-4E57502E9238
 *
 * @author kent onCondition 2018/3/20.
 */
public abstract class AbstractSQLAggregateFunction extends AbstractSQLFunction implements ISQLAggregateFunction {

    protected SQLWithinGroupSpecification withinGroup;

    protected SQLFilterClause filterClause;

    protected SQLOverClause overClause;

    public AbstractSQLAggregateFunction() {
    }

    public AbstractSQLAggregateFunction(String name) {
        super(name);
    }

    public AbstractSQLAggregateFunction(ISQLExpr name) {
        super(name);
    }

    @Override
    public AbstractSQLAggregateFunction clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLAggregateFunction x) {
        super.cloneTo(x);
    }


    @Override
    public boolean isWindowFunction() {
        return overClause != null;
    }

    public SQLWithinGroupSpecification getWithinGroup() {
        return withinGroup;
    }

    public void setWithinGroup(SQLWithinGroupSpecification withinGroup) {
        setChildParent(withinGroup);
        this.withinGroup = withinGroup;
    }

    public SQLFilterClause getFilterClause() {
        return filterClause;
    }

    public void setFilterClause(SQLFilterClause filterClause) {
        setChildParent(filterClause);
        this.filterClause = filterClause;
    }

    public SQLOverClause getOverClause() {
        return overClause;
    }

    public void setOverClause(SQLOverClause overClause) {
        setChildParent(overClause);
        this.overClause = overClause;
    }


    /**
     * WITHIN GROUP <left paren> ORDER BY <sort specification list> <right paren>
     *
     * @author kent on 2018/5/22.
     */
    public static class SQLWithinGroupSpecification extends AbstractSQLExpr {

        protected SQLOrderByClause orderByClause;

        public SQLWithinGroupSpecification() {
        }

        public SQLWithinGroupSpecification(SQLOrderByClause orderByClause) {
            setOrderByClause(orderByClause);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, orderByClause);
//            }
        }

        public SQLOrderByClause getOrderByClause() {
            return orderByClause;
        }

        public void setOrderByClause(SQLOrderByClause orderByClause) {
            setChildParent(orderByClause);
            this.orderByClause = orderByClause;
        }
    }

    /**
     * FILTER <left paren> WHERE <search condition> <right paren>
     * <p>
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#filter%20clause
     */
    public class SQLFilterClause extends AbstractSQLExpr {

        protected SQLWhereClause whereClause;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, whereClause);
//            }
        }

        public SQLWhereClause getWhereClause() {
            return whereClause;
        }

        public void setWhereClause(SQLWhereClause whereClause) {
            setChildParent(whereClause);
            this.whereClause = whereClause;
        }
    }
}
