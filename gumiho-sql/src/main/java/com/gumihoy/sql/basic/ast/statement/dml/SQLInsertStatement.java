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

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.insert.ISQLInsertValuesClause;
import com.gumihoy.sql.basic.ast.expr.common.ISQLPartitionExtensionClause;
import com.gumihoy.sql.basic.ast.expr.common.ISQLReturningClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLAssignmentExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLErrorLoggingClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLWithClause;
import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * INSERT [INTO] <insertion target> <insert columns and source>
 * <p>
 * overrideClause: OVERRIDING USER VALUE | OVERRIDING SYSTEM VALUE
 * <p>
 * valuesClause: subQuery , SQLValuesClause
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#insert%20statement
 * <p>
 * INSERT [LOW_PRIORITY | DELAYED | HIGH_PRIORITY] [IGNORE]
 * [INTO] tbl_name
 * [PARTITION (partition_name [, partition_name] ...)]
 * [(col_name [, col_name] ...)]
 * {VALUES | VALUE} (value_list) [, (value_list)] ...
 * [ON DUPLICATE KEY UPDATE assignment_list]
 * https://dev.mysql.com/doc/refman/8.0/en/insert.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLInsertStatement extends AbstractSQLStatement implements ISQLDMLStatement {

    protected SQLWithClause withClause;

    protected SQLPriority priority;

    protected boolean ignore;

    protected boolean into = true;

    protected ISQLTableReference tableReference;

    protected ISQLPartitionExtensionClause partitionExtensionClause;

    protected final List<ISQLExpr> columns = new ArrayList<>();

    protected SQLOverrideClause overrideClause;

    /**
     * 1、subQuery
     * 2、value ()
     * 3、values (), ()
     */
    protected ISQLInsertValuesClause valuesClause;

    // mysql
    protected final List<SQLAssignmentExpr> onDuplicateKeyUpdateAssignments = new ArrayList<>();

    protected ISQLReturningClause returningClause;

    protected SQLErrorLoggingClause errorLoggingClause;


    public SQLInsertStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, withClause);
            this.acceptChild(visitor, tableReference);
            this.acceptChild(visitor, partitionExtensionClause);
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, valuesClause);
            this.acceptChild(visitor, onDuplicateKeyUpdateAssignments);
            this.acceptChild(visitor, returningClause);
            this.acceptChild(visitor, errorLoggingClause);
        }
    }

    @Override
    public SQLInsertStatement clone() {
        SQLInsertStatement x = new SQLInsertStatement(this.dbType);
        return x;
    }

    public void cloneTo(SQLInsertStatement x) {
        super.cloneTo(x);

        if (this.withClause != null) {
            SQLWithClause withClauseClone = this.withClause.clone();
            x.setWithClause(withClauseClone);
        }

        x.priority = this.priority;
        x.ignore = this.ignore;
        x.into = this.into;

        ISQLTableReference tableReferenceClone = this.tableReference.clone();
        x.setTableReference(tableReferenceClone);


        for (ISQLExpr column : columns) {
            ISQLExpr columnClone = column.clone();
            x.addColumn(columnClone);
        }

        x.overrideClause = this.overrideClause;

        if (this.valuesClause != null) {
            ISQLInsertValuesClause valuesClauseClone = valuesClause.clone();
            x.setValuesClause(valuesClauseClone);
        }

//        if (this.returningClause != null) {
//            ISQLReturningClause returningClauseClone = this.returningClause.clone();
//            x.setReturningClause(returningClauseClone);
//        }
//
//        if (this.errorLoggingClause != null) {
//            SQLErrorLoggingClause errorLoggingClause = this.errorLoggingClause.clone();
//            x.setErrorLoggingClause(errorLoggingClause);
//        }

    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
//        boolean replace = replaceInList(columns, source, target, this);
//        if (replace) {
//            return true;
//        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.INSERT;
    }


    public SQLWithClause getWithClause() {
        return withClause;
    }

    public void setWithClause(SQLWithClause withClause) {
        setChildParent(withClause);
        this.withClause = withClause;
    }

    public SQLPriority getPriority() {
        return priority;
    }

    public void setPriority(SQLPriority priority) {
        this.priority = priority;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public boolean isInto() {
        return into;
    }

    public void setInto(boolean into) {
        this.into = into;
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

    public SQLOverrideClause getOverrideClause() {
        return overrideClause;
    }

    public void setOverrideClause(SQLOverrideClause overrideClause) {
        this.overrideClause = overrideClause;
    }

    public ISQLInsertValuesClause getValuesClause() {
        return valuesClause;
    }

    public void setValuesClause(ISQLInsertValuesClause valuesClause) {
        setChildParent(valuesClause);
        this.valuesClause = valuesClause;
    }

    public List<SQLAssignmentExpr> getOnDuplicateKeyUpdateAssignments() {
        return onDuplicateKeyUpdateAssignments;
    }

    public void addOnDuplicateKeyUpdateAssignment(SQLAssignmentExpr item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        onDuplicateKeyUpdateAssignments.add(item);
    }

    //
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
     * https://dev.mysql.com/doc/refman/8.0/en/insert.html
     */
    public enum SQLPriority implements ISQLASTEnum {
        LOW_PRIORITY("low_priority", "LOW_PRIORITY"),
        DELAYED("delayed", "DELAYED"),
        HIGH_PRIORITY("high_priority", "HIGH_PRIORITY"),
        ;
        public final String lower;
        public final String upper;

        SQLPriority(String lower, String upper) {
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
     *
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#override%20clause
     */
    public enum SQLOverrideClause implements ISQLASTEnum {
        OVERRIDING_USER_VALUE("overriding user value", "OVERRIDING USER VALUE"),
        OVERRIDING_SYSTEM_VALUE("overriding system value", "OVERRIDING SYSTEM VALUE");

        public final String lower;
        public final String upper;

        SQLOverrideClause(String lower, String upper) {
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
