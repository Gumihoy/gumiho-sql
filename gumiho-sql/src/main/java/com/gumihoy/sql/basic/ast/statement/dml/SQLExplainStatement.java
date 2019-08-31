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
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * {EXPLAIN | DESCRIBE | DESC} tbl_name [col_name | wild]
 * <p>
 * {EXPLAIN | DESCRIBE | DESC} [explain_type] {explainable_stmt | FOR CONNECTION connection_id}
 * <p>
 * explain_type: { FORMAT = format_name }
 * <p>
 * format_name: { TRADITIONAL | JSON | TREE}
 * <p>
 * explainable_stmt: { SELECT statement | DELETE statement | INSERT statement | REPLACE statement | UPDATE statement }
 * https://dev.mysql.com/doc/refman/5.6/en/explain.html
 * <p>
 * <p>
 * EXPLAIN PLAN [ SET STATEMENT_ID = string ] [ INTO [ schema. ] table [ @ dblink ] ] FOR statement ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/EXPLAIN-PLAN.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLExplainStatement extends AbstractSQLStatement implements ISQLDMLStatement {

    protected ISQLExpr statementIdValue;
    protected ISQLStatement statement;

    public SQLExplainStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//
//        }
    }

    @Override
    public SQLExplainStatement clone() {
        SQLExplainStatement x = new SQLExplainStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLExplainStatement x) {
        super.cloneTo(x);
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.EXPLAIN;
    }






}
