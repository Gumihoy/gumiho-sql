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
import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#query%20specification
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLSelectStatement extends AbstractSQLStatement implements ISQLDMLStatement {

    protected ISQLSelectQuery query;

    public SQLSelectStatement(DBType dbType) {
        super(dbType);
    }

    public SQLSelectStatement(ISQLSelectQuery query, DBType dbType) {
        super(dbType);
        setQuery(query);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.query);
        }
    }

    @Override
    public SQLSelectStatement clone() {
        SQLSelectStatement x = new SQLSelectStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLSelectStatement x) {
        super.cloneTo(x);
        ISQLSelectQuery queryClone = this.query.clone();
        x.setQuery(queryClone);
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == query
                && target instanceof ISQLSelectQuery) {
            this.setQuery((ISQLSelectQuery) target);
            return true;
        }

        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.SELECT;
    }


    public ISQLSelectQuery getQuery() {
        return query;
    }

    public void setQuery(ISQLSelectQuery query) {
        setChildParent(query);
        this.query = query;
    }

}
