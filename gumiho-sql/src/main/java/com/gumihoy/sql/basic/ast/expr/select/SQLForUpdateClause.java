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
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * FOR { UPDATE | NO KEY UPDATE | SHARE | KEY SHARE } [ OF table_name [, ...] ] [ NOWAIT | SKIP LOCKED| WAIT integer  ] [...]
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/select.html
 * https://www.postgresql.org/docs/devel/static/sql-select.html
 * for_update_clause: https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 *
 * @author kent onCondition 2018/3/21.
 */
public class SQLForUpdateClause extends AbstractSQLExpr implements ISQLLockClause {

    protected SQLForType forType;

    private final List<ISQLName> columns = new ArrayList<>();

    private SQLForOption forOption;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, forOption);
        }
    }

    @Override
    public SQLForUpdateClause clone() {

        SQLForUpdateClause x = new SQLForUpdateClause();

        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLForUpdateClause x) {
        super.cloneTo(x);

        x.forType = this.forType;

        for (ISQLName column : this.columns) {
            ISQLName columnClone = column.clone();
            x.addColumn(columnClone);
        }

        x.forOption = this.forOption;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {

        if (target instanceof ISQLName) {
            boolean replace = replaceInList(columns, source, (ISQLName) target, this);
            if (replace) {
                return true;
            }
        }

        if (source == forOption) {
            if (target == null) {
                setForOption(null);
                return true;
            } else if (target instanceof SQLForOption) {
                setForOption((SQLForOption) target);
                return true;
            }
            return false;
        }

        return false;
    }

    public SQLForType getForType() {
        return forType;
    }

    public void setForType(SQLForType forType) {
        this.forType = forType;
    }

    public List<ISQLName> getColumns() {
        return columns;
    }

    public void addColumn(ISQLName column) {
        if (column == null) {
            return;
        }
        setChildParent(column);
        this.columns.add(column);
    }

    public SQLForOption getForOption() {
        return forOption;
    }

    public void setForOption(SQLForOption forOption) {
        setChildParent(forOption);
        this.forOption = forOption;
    }


    public enum SQLForType implements ISQLASTEnum {
        UPDATE("update", "UPDATE"),
        NO_KEY_UPDATE("no key update", "NO KEY UPDATE"),
        SHARE("share", "SHARE"),
        KEY_SHARE("key share", "KEY SHARE");

        public final String lower;
        public final String upper;

        SQLForType(String name) {
            this(name, name);
        }

        SQLForType(String lower, String upper) {
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


    public interface SQLForOption extends ISQLExpr {
        @Override
        SQLForOption clone();
    }

    /**
     * NOWAIT
     */
    public static class SQLForNoWaitOption extends AbstractSQLExpr implements SQLForOption {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLForNoWaitOption clone() {
            return new SQLForNoWaitOption();
        }
    }

    /**
     * WAIT integer
     */
    public static class SQLForWaitOption extends AbstractSQLExpr implements SQLForOption {

        protected ISQLExpr expr;

        public SQLForWaitOption(ISQLExpr expr) {
            setExpr(expr);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, expr);
            }
        }

        @Override
        public SQLForWaitOption clone() {
            ISQLExpr exprClone = this.expr.clone();
            return new SQLForWaitOption(exprClone);
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
     * SKIP LOCKED
     */
    public static class SQLForSkipLockedOption extends AbstractSQLExpr implements SQLForOption {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLForSkipLockedOption clone() {
            return new SQLForSkipLockedOption();
        }
    }

}
