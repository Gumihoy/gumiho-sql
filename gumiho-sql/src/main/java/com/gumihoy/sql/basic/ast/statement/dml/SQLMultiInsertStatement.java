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
package com.gumihoy.sql.basic.ast.statement.dml;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLErrorLoggingClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLValuesClause;
import com.gumihoy.sql.basic.ast.expr.insert.ISQLInsertValuesClause;
import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * insert
 * { ALL
 * { insert_into_clause [ values_clause ] [error_logging_clause] }...
 * | conditional_insert_clause
 * } subquery
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/INSERT.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLMultiInsertStatement extends AbstractSQLStatement implements ISQLDMLStatement {

    //    protected List<SQLHint> hints;
    protected ISQLInsertClause insertClause;
    protected ISQLSelectQuery subQuery;


    public SQLMultiInsertStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, insertClause);
            this.acceptChild(visitor, subQuery);
        }
    }

    @Override
    public SQLMultiInsertStatement clone() {
        SQLMultiInsertStatement x = new SQLMultiInsertStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLMultiInsertStatement x) {
        super.cloneTo(x);

        ISQLInsertClause insertClauseClone = insertClause.clone();
        x.setInsertClause(insertClauseClone);

        if (subQuery != null) {
            ISQLSelectQuery clone = subQuery.clone();
            x.setSubQuery(clone);
        }
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.INSERT;
    }

    public ISQLInsertClause getInsertClause() {
        return insertClause;
    }

    public void setInsertClause(ISQLInsertClause insertClause) {
        setChildParent(insertClause);
        this.insertClause = insertClause;
    }

    public ISQLSelectQuery getSubQuery() {
        return subQuery;
    }

    public void setSubQuery(ISQLSelectQuery subQuery) {
        setChildParent(subQuery);
        this.subQuery = subQuery;
    }


    public enum SQLType implements ISQLASTEnum {

        ALL("all", "ALL"),
        FIRST("first", "FIRST");

        public final String lower;
        public final String upper;


        SQLType(String lower, String upper) {
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


    public interface ISQLInsertClause extends ISQLExpr {
        @Override
        ISQLInsertClause clone();
    }

    /**
     * ALL  { insert_into_clause [ values_clause ] [error_logging_clause] }...
     */
    public static class SQLAllInsertClause extends AbstractSQLExpr implements ISQLInsertClause {

        protected final List<SQLInsertIntoClauseItem> items = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, items);
            }
        }

        @Override
        public SQLAllInsertClause clone() {
            return null;
        }

        public List<SQLInsertIntoClauseItem> getItems() {
            return items;
        }

        public void addItem(SQLInsertIntoClauseItem item) {
            if (item == null) {
                return;
            }
            setChildParent(item);
            this.items.add(item);
        }
    }

    /**
     * WHEN condition
     * THEN insert_into_clause
     * [ values_clause ]
     * [ error_logging_clause ]
     * [ insert_into_clause [ values_clause ] [ error_logging_clause ] ]...
     * [ WHEN condition
     * THEN insert_into_clause
     * [ values_clause ]
     * [ error_logging_clause ]
     * [ insert_into_clause [ values_clause ] [ error_logging_clause ] ]...
     * ]...
     * [ ELSE insert_into_clause
     * [ values_clause ]
     * [ error_logging_clause ]
     * [ insert_into_clause [ values_clause ] [ error_logging_clause ] ]...
     * ]
     */
    public static class SQLConditionalInsertClause extends AbstractSQLExpr implements ISQLInsertClause {

        protected SQLType type;

        protected final List<SQLConditionalInsertWhenClause> whenClauses = new ArrayList<>();

        protected final List<SQLInsertIntoClauseItem> elseItems = new ArrayList<>();

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, whenClauses);
                this.acceptChild(visitor, elseItems);
            }
        }

        @Override
        public SQLConditionalInsertClause clone() {
            return null;
        }

        public SQLType getType() {
            return type;
        }

        public void setType(SQLType type) {
            this.type = type;
        }

        public List<SQLConditionalInsertWhenClause> getWhenClauses() {
            return whenClauses;
        }

        public void addWhenClause(SQLConditionalInsertWhenClause whenClause) {
            if (whenClause == null) {
                return;
            }
            setChildParent(whenClause);
            this.whenClauses.add(whenClause);
        }

        public List<SQLInsertIntoClauseItem> getElseItems() {
            return elseItems;
        }

        public void addElseItem(SQLInsertIntoClauseItem item) {
            if (item == null) {
                return;
            }
            setChildParent(item);
            this.elseItems.add(item);
        }
    }

    /**
     * INTO tableReference [columns] [valuesClause] [errorLoggingClause]
     */
    public static class SQLInsertIntoClauseItem extends AbstractSQLExpr {

        protected ISQLTableReference tableReference;
        protected final List<ISQLExpr> columns = new ArrayList<>();
        protected ISQLInsertValuesClause valuesClause;
        protected SQLErrorLoggingClause errorLoggingClause;

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, tableReference);
                this.acceptChild(visitor, columns);
                this.acceptChild(visitor, valuesClause);
                this.acceptChild(visitor, errorLoggingClause);
            }
        }

        @Override
        public SQLInsertIntoClauseItem clone() {
            SQLInsertIntoClauseItem x = new SQLInsertIntoClauseItem();
            this.cloneTo(x);
            return x;
        }

        public void cloneTo(SQLInsertIntoClauseItem x) {
            super.cloneTo(x);
        }


        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            boolean replace = replaceInList(columns, source, target, this);
            if (replace) {
                return true;
            }
            return false;
        }

        public ISQLTableReference getTableReference() {
            return tableReference;
        }

        public void setTableReference(ISQLTableReference tableReference) {
            setChildParent(tableReference);
            this.tableReference = tableReference;
        }

        public List<ISQLExpr> getColumns() {
            return columns;
        }

        public void addColumn(ISQLExpr column) {
            if (column == null) {
                return;
            }
            setChildParent(column);
            this.columns.add(column);
        }

        public ISQLInsertValuesClause getValuesClause() {
            return valuesClause;
        }

        public void setValuesClause(ISQLInsertValuesClause valuesClause) {
            setChildParent(valuesClause);
            this.valuesClause = valuesClause;
        }

        public SQLErrorLoggingClause getErrorLoggingClause() {
            return errorLoggingClause;
        }

        public void setErrorLoggingClause(SQLErrorLoggingClause errorLoggingClause) {
            setChildParent(errorLoggingClause);
            this.errorLoggingClause = errorLoggingClause;
        }
    }

    /**
     * WHEN condition THEN thens
     */
    public static class SQLConditionalInsertWhenClause extends AbstractSQLExpr {

        protected ISQLExpr condition;
        protected final List<SQLInsertIntoClauseItem> thenItems = new ArrayList<>();

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, condition);
                this.acceptChild(visitor, thenItems);
            }
        }

        public ISQLExpr getCondition() {
            return condition;
        }

        public void setCondition(ISQLExpr condition) {
            setChildParent(condition);
            this.condition = condition;
        }

        public List<SQLInsertIntoClauseItem> getThenItems() {
            return thenItems;
        }

        public void addThenItem(SQLInsertIntoClauseItem thenItem) {
            if (thenItem == null) {
                return;
            }
            setChildParent(thenItem);
            this.thenItems.add(thenItem);
        }
    }

}
