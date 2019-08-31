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
package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLReturningClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLCompileClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLParameterDeclaration;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLDeleteStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.oracle.ast.statement.AbstractOracleStatement;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * 13.28 EXECUTE IMMEDIATE Statement
 * <p>
 * EXECUTE IMMEDIATE dynamic_sql_stmt
 * [ { into_clause | bulk_collect_into_clause } [ using_clause ]
 * | using_clause [ dynamic_returning_clause ]
 * | dynamic_returning_clause
 * ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/EXECUTE-IMMEDIATE-statement.html#GUID-C3245A95-B85B-4280-A01F-12307B108DC8
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/EXECUTE-IMMEDIATE-statement.html#GUID-C3245A95-B85B-4280-A01F-12307B108DC8
 *
 * @author kent onCondition 2018/3/22.
 */
public class SQLExecuteImmediateStatement extends AbstractSQLStatement {

    protected ISQLExpr dynamicSQLStmt;

    protected boolean bulkCollect = false;
    protected final List<ISQLExpr> intoItems = new ArrayList<>();

    protected SQLUsingClause usingClause;

    protected ISQLReturningClause returningClause;


    public SQLExecuteImmediateStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if(visitor.visit(this)) {
            this.acceptChild(visitor, dynamicSQLStmt);
            this.acceptChild(visitor, intoItems);
            this.acceptChild(visitor, usingClause);
            this.acceptChild(visitor, returningClause);
        }
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;
    }

    public ISQLExpr getDynamicSQLStmt() {
        return dynamicSQLStmt;
    }

    public void setDynamicSQLStmt(ISQLExpr dynamicSQLStmt) {
        this.dynamicSQLStmt = dynamicSQLStmt;
    }

    public boolean isBulkCollect() {
        return bulkCollect;
    }

    public void setBulkCollect(boolean bulkCollect) {
        this.bulkCollect = bulkCollect;
    }

    public List<ISQLExpr> getIntoItems() {
        return intoItems;
    }

    public void addInotItem(ISQLExpr item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.intoItems.add(item);
    }

    public SQLUsingClause getUsingClause() {
        return usingClause;
    }

    public void setUsingClause(SQLUsingClause usingClause) {
        setChildParent(usingClause);
        this.usingClause = usingClause;
    }


    public ISQLReturningClause getReturningClause() {
        return returningClause;
    }

    public void setReturningClause(ISQLReturningClause returningClause) {
        setChildParent(returningClause);
        this.returningClause = returningClause;
    }


    public static class SQLUsingClause extends AbstractSQLExpr {


        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }
    }

    public static class SQLParameter extends AbstractSQLExpr {

        protected SQLParameterModel model;
        protected ISQLExpr name;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        public SQLParameterModel getModel() {
            return model;
        }

        public void setModel(SQLParameterModel model) {
            this.model = model;
        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            this.name = name;
        }
    }

    public enum SQLParameterModel implements ISQLASTEnum {

        IN("in", "IN"),
        OUT("out", "OUT"),
        IN_OUT("in out", "IN OUT"),
        ;

        public final String lower;
        public final String upper;

        SQLParameterModel(String lower, String upper) {
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
