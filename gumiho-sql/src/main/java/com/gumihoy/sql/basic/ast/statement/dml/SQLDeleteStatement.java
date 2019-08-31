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
import com.gumihoy.sql.basic.ast.expr.common.ISQLPartitionExtensionClause;
import com.gumihoy.sql.basic.ast.expr.common.ISQLReturningClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLErrorLoggingClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLWhereClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLWithClause;
import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.basic.ast.expr.select.limit.ISQLLimitClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * DELETE [LOW_PRIORITY] [QUICK] [IGNORE] [FROM] tbl_name
 * [PARTITION (partition_name [, partition_name] ...)]
 * [WHERE where_condition]
 * [ORDER BY ...]
 * [LIMIT row_count]
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#delete%20statement:%20positioned
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#delete%20statement:%20searched
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#dynamic%20delete%20statement:%20positioned
 * <p>
 * <p>
 * DELETE LOW_PRIORITY? QUICK? IGNORE? FROM iTableReference
 * (USING iTableReference)? whereClause? orderByClause? limitOffsetClause?
 * https://dev.mysql.com/doc/refman/8.0/en/delete.html
 * <p>
 * https://www.postgresql.org/docs/devel/static/sql-delete.html
 * <p>
 * DELETE FROM? iTableReference whereClause? returningIntoClause? errorLoggingClause?
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/DELETE.html#GUID-156845A5-B626-412B-9F95-8869B988ABD7
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDeleteStatement extends AbstractSQLStatement implements ISQLDMLStatement {

    protected SQLWithClause withClause;

    protected boolean lowPriority;

    protected boolean quick;

    protected boolean ignore;

    // mysql
    protected ISQLTableReference multipleTableReference;

    protected boolean from = true;

    protected ISQLTableReference tableReference;

    protected ISQLPartitionExtensionClause partitionExtensionClause;

    // mysql
    protected ISQLTableReference usingTableReference;

    protected SQLUsingClause usingClause;

    protected SQLWhereClause whereClause;

    protected SQLOrderByClause orderByClause;

    protected ISQLLimitClause limitClause;

    protected ISQLReturningClause returningClause;

    // oracle
    protected SQLErrorLoggingClause errorLoggingClause;

    public SQLDeleteStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, withClause);
            this.acceptChild(visitor, multipleTableReference);
            this.acceptChild(visitor, tableReference);
            this.acceptChild(visitor, partitionExtensionClause);
            this.acceptChild(visitor, usingTableReference);
            this.acceptChild(visitor, usingClause);
            this.acceptChild(visitor, whereClause);
            this.acceptChild(visitor, orderByClause);
            this.acceptChild(visitor, limitClause);
            this.acceptChild(visitor, returningClause);
            this.acceptChild(visitor, errorLoggingClause);
        }
    }

    @Override
    public SQLDeleteStatement clone() {
        SQLDeleteStatement x = new SQLDeleteStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLDeleteStatement x) {
        super.cloneTo(x);

        if (this.withClause != null) {
            SQLWithClause withClauseClone = this.withClause.clone();
            x.setWithClause(withClauseClone);
        }

        x.lowPriority = this.lowPriority;

        x.quick = this.quick;

        x.ignore = this.ignore;

        x.from = this.from;

        ISQLTableReference tableReferenceClone = this.tableReference.clone();
        x.setTableReference(tableReferenceClone);


        if (this.whereClause != null) {
            SQLWhereClause whereClauseClone = whereClause.clone();
            x.setWhereClause(whereClauseClone);
        }

        if (this.orderByClause != null) {
            SQLOrderByClause orderByClauseClone = orderByClause.clone();
            x.setOrderByClause(orderByClauseClone);
        }

        if (this.limitClause != null) {
            ISQLLimitClause limitClauseClone = limitClause.clone();
            x.setLimitClause(limitClauseClone);
        }

        if (this.returningClause != null) {
            ISQLReturningClause returningClauseClone = this.returningClause.clone();
            x.setReturningClause(returningClauseClone);
        }

        if (this.errorLoggingClause != null) {
            SQLErrorLoggingClause errorLoggingClauseClone = this.errorLoggingClause.clone();
            x.setErrorLoggingClause(errorLoggingClauseClone);
        }
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.DELETE;
    }

    public SQLWithClause getWithClause() {
        return withClause;
    }

    public void setWithClause(SQLWithClause withClause) {
        setChildParent(withClause);
        this.withClause = withClause;
    }

    public boolean isLowPriority() {
        return lowPriority;
    }

    public void setLowPriority(boolean lowPriority) {
        this.lowPriority = lowPriority;
    }

    public boolean isQuick() {
        return quick;
    }

    public void setQuick(boolean quick) {
        this.quick = quick;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public ISQLTableReference getMultipleTableReference() {
        return multipleTableReference;
    }

    public void setMultipleTableReference(ISQLTableReference multipleTableReference) {
        setChildParent(multipleTableReference);
        this.multipleTableReference = multipleTableReference;
    }

    public boolean isFrom() {
        return from;
    }

    public void setFrom(boolean from) {
        this.from = from;
    }

    public ISQLTableReference getTableReference() {
        return tableReference;
    }

    public void setTableReference(ISQLTableReference tableReference) {
        setChildParent(tableReference);
        this.tableReference = tableReference;
    }

    public ISQLPartitionExtensionClause getPartitionExtensionClause() {
        return partitionExtensionClause;
    }

    public void setPartitionExtensionClause(ISQLPartitionExtensionClause partitionExtensionClause) {
        setChildParent(partitionExtensionClause);
        this.partitionExtensionClause = partitionExtensionClause;
    }

    public ISQLTableReference getUsingTableReference() {
        return usingTableReference;
    }

    public void setUsingTableReference(ISQLTableReference usingTableReference) {
        setChildParent(usingTableReference);
        this.usingTableReference = usingTableReference;
    }

    public SQLUsingClause getUsingClause() {
        return usingClause;
    }

    public void setUsingClause(SQLUsingClause usingClause) {
        setChildParent(usingClause);
        this.usingClause = usingClause;
    }

    public SQLWhereClause getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(SQLWhereClause whereClause) {
        setChildParent(whereClause);
        this.whereClause = whereClause;
    }

    public SQLOrderByClause getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(SQLOrderByClause orderByClause) {
        setChildParent(orderByClause);
        this.orderByClause = orderByClause;
    }

    public ISQLLimitClause getLimitClause() {
        return limitClause;
    }

    public void setLimitClause(ISQLLimitClause limitClause) {
        setChildParent(limitClause);
        this.limitClause = limitClause;
    }

    public ISQLReturningClause getReturningClause() {
        return returningClause;
    }

    public void setReturningClause(ISQLReturningClause returningClause) {
        setChildParent(returningClause);
        this.returningClause = returningClause;
    }

    public SQLErrorLoggingClause getErrorLoggingClause() {
        return errorLoggingClause;
    }

    public void setErrorLoggingClause(SQLErrorLoggingClause errorLoggingClause) {
        setChildParent(errorLoggingClause);
        this.errorLoggingClause = errorLoggingClause;
    }


    /**
     * USING using_list
     * <p>
     * https://www.postgresql.org/docs/11/sql-delete.html
     */
    public static class SQLUsingClause extends AbstractSQLExpr {
        protected ISQLTableReference tableReference;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, tableReference);
//            }
        }

        public ISQLTableReference getTableReference() {
            return tableReference;
        }

        public void setTableReference(ISQLTableReference tableReference) {
            setChildParent(tableReference);
            this.tableReference = tableReference;
        }
    }
}
