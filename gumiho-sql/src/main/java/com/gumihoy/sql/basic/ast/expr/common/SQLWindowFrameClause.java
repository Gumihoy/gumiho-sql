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
 * <window frame units> <window frame extent> [ <window frame exclusion> ]
 * <p>
 * <window frame units>    ::=   ROWS | RANGE [frame_start | BETWEEN frame_start AND frame_end]
 * <window frame extent> ::= UNBOUNDED PRECEDING |  <unsigned value specification> PRECEDING | CURRENT ROW |  BETWEEN <window frame bound 1> AND <window frame bound 2>
 * <window frame exclusion>    ::= EXCLUDE CURRENT ROW | EXCLUDE GROUP | EXCLUDE TIES | EXCLUDE NO OTHERS
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#window%20frame%20clause
 * <p>
 * https://dev.mysql.com/doc/refman/8.0/en/window-functions-usage.html
 * <p>
 * windowing_clause : https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Analytic-Functions.html#GUID-527832F7-63C0-4445-8C16-307FA5084056
 *
 * @author kent on 2018/2/8.
 */
public class SQLWindowFrameClause extends AbstractSQLExpr {

    protected SQLWindowFrameUnit unit;

    protected boolean between;
    protected ISQLExpr start;
    protected ISQLExpr end;

    protected SQLWindowFrameExclusion exclusion;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, start);
            this.acceptChild(visitor, end);
        }
    }


    @Override
    public SQLWindowFrameClause clone() {
        SQLWindowFrameClause x = new SQLWindowFrameClause();

        x.unit = this.unit;

        x.between = this.between;

        ISQLExpr startClone = this.start.clone();
        x.setStart(startClone);

        if (this.end != null) {
            ISQLExpr endClone = this.end.clone();
            x.setEnd(endClone);
        }

        x.exclusion = this.exclusion;

        return x;
    }


    public SQLWindowFrameUnit getUnit() {
        return unit;
    }

    public void setUnit(SQLWindowFrameUnit unit) {
        this.unit = unit;
    }

    public boolean isBetween() {
        return between;
    }

    public void setBetween(boolean between) {
        this.between = between;
    }

    public ISQLExpr getStart() {
        return start;
    }

    public void setStart(ISQLExpr start) {
        this.start = start;
    }

    public ISQLExpr getEnd() {
        return end;
    }

    public void setEnd(ISQLExpr end) {
        this.end = end;
    }

    public SQLWindowFrameExclusion getExclusion() {
        return exclusion;
    }

    public void setExclusion(SQLWindowFrameExclusion exclusion) {
        this.exclusion = exclusion;
    }


    public enum SQLWindowFrameUnit implements ISQLASTEnum {

        ROWS("rows", "ROWS"),
        RANGE("range", "RANGE"),
        GROUPS("groups", "GROUPS"),
        ;

        public final String lower;
        public final String upper;

        SQLWindowFrameUnit(String lower, String upper) {
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

    /**
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#window%20frame%20exclusion
     */
    public enum SQLWindowFrameExclusion implements ISQLASTEnum {

        EXCLUDE_CURRENT_ROW("EXCLUDE CURRENT ROW", "EXCLUDE CURRENT ROW"),
        EXCLUDE_GROUP("EXCLUDE GROUP", "EXCLUDE GROUP"),
        EXCLUDE_TIES("EXCLUDE TIES", "EXCLUDE TIES"),
        EXCLUDE_NO_OTHERS("EXCLUDE NO OTHERS", "EXCLUDE NO OTHERS"),
        ;

        public final String lower;
        public final String upper;

        SQLWindowFrameExclusion(String lower, String upper) {
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

    /**
     * CURRENT ROW
     */
    public static class SQLCurrentRow extends AbstractSQLExpr {

        public static SQLCurrentRow of () {
            return new SQLCurrentRow();
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }
    }


    public static class SQLWindowFrameBetween extends AbstractSQLExpr {

        protected ISQLExpr between;
        protected ISQLExpr and;

        public SQLWindowFrameBetween(ISQLExpr between, ISQLExpr and) {
            setBetween(between);
            setAnd(and);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, between);
                this.acceptChild(visitor, and);
            }
        }

        public ISQLExpr getBetween() {
            return between;
        }

        public void setBetween(ISQLExpr between) {
            setChildParent(between);
            this.between = between;
        }

        public ISQLExpr getAnd() {
            return and;
        }

        public void setAnd(ISQLExpr and) {
            setChildParent(and);
            this.and = and;
        }
    }

    /**
     * expr PRECEDING
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#window%20frame%20preceding
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Analytic-Functions.html#GUID-527832F7-63C0-4445-8C16-307FA5084056
     */
    public static class SQLWindowFramePreceding extends AbstractSQLExpr {

        protected ISQLExpr value;

        public SQLWindowFramePreceding(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }

    /**
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#window%20frame%20following
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Analytic-Functions.html#GUID-527832F7-63C0-4445-8C16-307FA5084056
     */
    public static class SQLWindowFrameFollowing extends AbstractSQLExpr {

        protected ISQLExpr value;

        public SQLWindowFrameFollowing(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }
}
