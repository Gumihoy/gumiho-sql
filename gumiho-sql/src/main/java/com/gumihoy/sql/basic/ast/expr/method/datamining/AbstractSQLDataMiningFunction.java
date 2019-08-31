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
package com.gumihoy.sql.basic.ast.expr.method.datamining;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.method.AbstractSQLFunction;
import com.gumihoy.sql.basic.ast.expr.method.window.SQLOverClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * functionName ( [argument]... [orderedByWeight=(DESC | ASC | ABS)]
 * [costMatrixClause] [mining_attribute_clause] [AND mining_attribute_clause])
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Single-Row-Functions.html#GUID-B3E664DC-2675-4AC7-885B-B9AB287CF76F
 *
 * @author kent onCondition 2018/3/20.
 */
public abstract class AbstractSQLDataMiningFunction extends AbstractSQLFunction implements ISQLDataMiningFunction {

//    protected SQLReserved orderedByWeight;

    protected ISQLExpr costMatrixClause;

    protected SQLMiningAttributeClause miningAttributeClause;

    protected SQLMiningAttributeClause andMiningAttributeClause;

    protected SQLOverClause overClause;

    public AbstractSQLDataMiningFunction(String name) {
        super(name);
    }

    public AbstractSQLDataMiningFunction(ISQLExpr name) {
        super(name);
    }

    @Override
    public AbstractSQLDataMiningFunction clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLDataMiningFunction x) {
        super.cloneTo(x);
    }


    @Override
    public boolean isWindowFunction() {
        return overClause != null;
    }

//    public SQLReserved getOrderedByWeight() {
//        return orderedByWeight;
//    }
//
//    public void setOrderedByWeight(SQLReserved orderedByWeight) {
//        this.orderedByWeight = orderedByWeight;
//    }

    public ISQLExpr getCostMatrixClause() {
        return costMatrixClause;
    }

    public void setCostMatrixClause(ISQLExpr costMatrixClause) {
        this.costMatrixClause = costMatrixClause;
    }

    public SQLMiningAttributeClause getMiningAttributeClause() {
        return miningAttributeClause;
    }

    public void setMiningAttributeClause(SQLMiningAttributeClause miningAttributeClause) {
        this.miningAttributeClause = miningAttributeClause;
    }

    public SQLMiningAttributeClause getAndMiningAttributeClause() {
        return andMiningAttributeClause;
    }

    public void setAndMiningAttributeClause(SQLMiningAttributeClause andMiningAttributeClause) {
        this.andMiningAttributeClause = andMiningAttributeClause;
    }

    public SQLOverClause getOverClause() {
        return overClause;
    }

    public void setOverClause(SQLOverClause overClause) {
        setChildParent(overClause);
        this.overClause = overClause;
    }


    /**
     * INTO expr
     */
    public static class SQLIntoArgumentExpr extends AbstractSQLExpr {
        protected ISQLExpr expr;

        public SQLIntoArgumentExpr(ISQLExpr expr) {
            setExpr(expr);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, expr);
//            }
        }

        public ISQLExpr getExpr() {
            return expr;
        }

        public void setExpr(ISQLExpr expr) {
            setChildParent(expr);
            this.expr = expr;
        }
    }


    /**
     * For expr
     */
    public static class SQLForArgumentExpr extends AbstractSQLExpr {
        protected ISQLExpr expr;

        public SQLForArgumentExpr(ISQLExpr expr) {
            setExpr(expr);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, expr);
//            }
        }

        public ISQLExpr getExpr() {
            return expr;
        }

        public void setExpr(ISQLExpr expr) {
            setChildParent(expr);
            this.expr = expr;
        }
    }

    /**
     * COST( class_value [, class_value]... ) VALUES ( ( cost_value [, cost_value]...) [ , (cost_value [, cost_value]... ) ]... )}
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/PREDICTION_COST.html#GUID-2E58222D-FB7E-4CA2-BCAA-C932FCDEE890
     */
    public static class SQLCostMatrixClause extends AbstractSQLExpr {

        protected final List<ISQLExpr> classValues = new ArrayList<>();
        protected final List<ISQLExpr> costValues = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, classValues);
//                this.acceptChild(visitor, costValues);
//            }
        }

        public List<ISQLExpr> getClassValues() {
            return classValues;
        }

        public void addClassValue(ISQLExpr item) {
            if (item == null) {
                return;
            }
            setChildParent(item);
            this.classValues.add(item);
        }

        public List<ISQLExpr> getCostValues() {
            return costValues;
        }

        public void addCostValue(ISQLExpr item) {
            if (item == null) {
                return;
            }
            setChildParent(item);
            this.costValues.add(item);
        }
    }

    /**
     * USING { * | { [ schema . ] table . * | expr [ AS alias ] } [, { [ schema . ] table . * | expr [ AS alias ] }]...}
     */
    public static class SQLMiningAttributeClause extends AbstractSQLExpr {

        protected final List<ISQLExpr> items = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, items);
//            }
        }

        public List<ISQLExpr> getItems() {
            return items;
        }

        public void addItem(ISQLExpr item) {
            if (item == null) {
                return;
            }
            setChildParent(item);
            this.items.add(item);
        }
    }


}
