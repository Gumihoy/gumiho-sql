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
package com.gumihoy.sql.basic.ast.expr.table.element;

import com.gumihoy.sql.basic.ast.enums.SQLCharSizeUnit;
import com.gumihoy.sql.basic.ast.enums.SQLReferentialAction;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.AbstractSQLConstraint;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#references%20specification
 * <p>
 * <p>
 * https://docs.oracle.com/cd/B28359_01/server.111/b28286/clauses002.htm#CJAFFBAA
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
 *
 * @author kent onCondition 2018/3/28.
 */
public abstract class AbstractSQLReferencesConstraint extends AbstractSQLConstraint implements ISQLTableElement {

    protected ISQLName referencedTable;

    protected final List<SQLColumn> referencedColumns = new ArrayList<>();

    protected MatchType matchType;

    protected final List<SQLReferentialTriggerAction> actions = new ArrayList<>();


    @Override
    public AbstractSQLReferencesConstraint clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }


    public ISQLName getReferencedTable() {
        return referencedTable;
    }

    public void setReferencedTable(ISQLName referencedTable) {
        this.referencedTable = referencedTable;
    }

    public List<SQLColumn> getReferencedColumns() {
        return referencedColumns;
    }

    public void addReferencedColumn(SQLColumn referencedColumn) {
        if (referencedColumn == null) {
            return;
        }
        setChildParent(referencedColumn);
        this.referencedColumns.add(referencedColumn);
    }

    public void addReferencedColumn(ISQLExpr expr) {
        if (expr == null) {
            return;
        }
        SQLColumn referencedColumn;
        if (expr instanceof SQLColumn) {
            referencedColumn = (SQLColumn) expr;
        } else {
            referencedColumn = SQLColumn.of(expr);
        }
        setChildParent(referencedColumn);
        this.referencedColumns.add(referencedColumn);
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public List<SQLReferentialTriggerAction> getActions() {
        return actions;
    }

    public void addAction(SQLReferentialTriggerAction action) {
        if (action == null) {
            return;
        }
        setChildParent(action);
        this.actions.add(action);
    }

    /**
     * MATCH (FULL | PARTIAL | SIMPLE)
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#match%20type
     */
    public enum MatchType implements ISQLASTEnum {
        FULL("full", "FULL"),
        PARTIAL("partial", "PARTIAL"),
        SIMPLE("simple", "SIMPLE");

        public final String lower;
        public final String upper;

        MatchType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public static SQLCharSizeUnit of(String name) {
            for (SQLCharSizeUnit unit : SQLCharSizeUnit.values()) {
                if (unit.lower.equalsIgnoreCase(name)) {
                    return unit;
                }
            }
            return null;
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
     * ON UPDATE <referential action>
     * ON DELETE <referential action>
     * <referential triggered action>
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#referential%20triggered%20action
     */
    public interface SQLReferentialTriggerAction extends ISQLExpr {
        @Override
        SQLReferentialTriggerAction clone();
    }

    static abstract class AbstractSQLReferentialTriggerAction extends AbstractSQLExpr implements SQLReferentialTriggerAction {

        protected SQLReferentialAction action;


        public AbstractSQLReferentialTriggerAction(SQLReferentialAction action) {
            this.action = action;
        }

        @Override
        public AbstractSQLReferentialTriggerAction clone() {
            throw new UnsupportedOperationException(getClass().getName());
        }

        public void cloneTo(AbstractSQLReferentialTriggerAction x) {
            super.cloneTo(x);
            x.action = this.action;
        }

        public SQLReferentialAction getAction() {
            return action;
        }

        public void setAction(SQLReferentialAction action) {
            this.action = action;
        }
    }

    /**
     * ON UPDATE <referential action>
     */
    public static class SQLOnUpdateAction extends AbstractSQLReferentialTriggerAction {

        public SQLOnUpdateAction(SQLReferentialAction action) {
            super(action);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLOnUpdateAction clone() {
            SQLOnUpdateAction x = new SQLOnUpdateAction(this.action);
            this.cloneTo(x);
            return x;
        }
    }

    /**
     * ON DELETE <referential action>
     */
    public static class SQLOnDeleteAction extends AbstractSQLReferentialTriggerAction {

        public SQLOnDeleteAction(SQLReferentialAction action) {
            super(action);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLOnDeleteAction clone() {
            SQLOnDeleteAction x = new SQLOnDeleteAction(this.action);
            this.cloneTo(x);
            return x;
        }
    }
}
