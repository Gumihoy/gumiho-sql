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

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * REPLACE [LOW_PRIORITY | DELAYED]
 * [INTO] tbl_name
 * [PARTITION (partition_name [, partition_name] ...)]
 * [(col_name [, col_name] ...)]
 * {VALUES | VALUE} (value_list) [, (value_list)] ...
 * https://dev.mysql.com/doc/refman/5.7/en/replace.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLReplaceStatement extends AbstractSQLStatement implements ISQLDMLStatement {

//    protected SQLPriorityType priority;

    protected boolean into;

    protected ISQLTableReference tableReference;

    protected final List<ISQLExpr> columns = new ArrayList<>();

    protected ISQLExpr valuesClause;

    public SQLReplaceStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, tableReference);
//            this.acceptChild(visitor, columns);
//            this.acceptChild(visitor, valuesClause);
//        }
    }

    @Override
    public SQLReplaceStatement clone() {
        SQLReplaceStatement x = new SQLReplaceStatement(this.dbType);
        this.cloneTo(x);


        return x;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.REPLACE;
    }


//    public SQLPriorityType getPriority() {
//        return priority;
//    }
//
//    public SQLReplaceStatement setPriority(SQLPriorityType priority) {
//        this.priority = priority;
//        return this;
//    }

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

    public ISQLExpr getValuesClause() {
        return valuesClause;
    }

    public void setValuesClause(ISQLExpr valuesClause) {
        setChildParent(valuesClause);
        this.valuesClause = valuesClause;
    }
}
