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
package com.gumihoy.sql.translate.visitor.oracle2.mysql;

import com.gumihoy.sql.config.SQLOutputConfig;
import com.gumihoy.sql.dialect.mysql.visitor.MySQLASTOutputVisitor;
import com.gumihoy.sql.dialect.oracle.ast.expr.select.OracleSelectQuery;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;
import com.gumihoy.sql.dialect.oracle.visitor.OracleASTOutputVisitor;

/**
 * @author kent onCondition 2018/1/16.9
 */
public class Oracle2MySQLASTOutputVisitor extends MySQLASTOutputVisitor implements IOracleASTVisitor {

    protected OracleASTOutputVisitor oracleSQLASTOutputVisitor;

    public Oracle2MySQLASTOutputVisitor(StringBuilder appender, SQLOutputConfig config) {
        super(appender, config);
        oracleSQLASTOutputVisitor = new OracleASTOutputVisitor(appender, config);
    }

//    @Override
//    public boolean visit(OracleSQLAssignmentStatement x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(SQLSetTransactionStatement x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }

    // ------------------------- Data Types Start ----------------------------------------

//    @Override
//    public boolean visit(SQLRefDataType x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }

    // ------------------------- Data Types End ----------------------------------------

    // ------------------------- Literal Start ----------------------------------------

//    @Override
//    public boolean visit(SQLBooleanLiteral x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }

    // ------------------------- Literal End ----------------------------------------

    // ------------------------- Commons Expr Start ----------------------------------------

//    @Override
//    public boolean visit(SQLParameterDeclaration x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }


    // ------------------------- Commons Expr End ----------------------------------------


    // --------------- Common SQL DDL Clauses Start ----------------------------


    // ----------- PL/SQL Language Elements End ------------------------------------------------------------


    // ----------- Database Start ------------------------------------------------------------

    // ----------- create ----------------



    // ----------- Database End ------------------------------------------------------------


    // ------------------ Sequence Details Start ----------------------
//    @Override
//    public boolean visit(SQLSequenceNoMaxValueOption x) {
//        print(SQLReserved.NOMAXVALUE);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLSequenceNoMinValueOption x) {
//        print(SQLReserved.NOMINVALUE);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLSequenceNoCycleOption x) {
//        print(SQLReserved.NOCYCLE);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLSequenceNoCacheOption x) {
//        print(SQLReserved.NOCACHE);
//        return false;
//    }
    // ------------------ Sequence Details End ----------------------


    // ----------- SELECT Details Start ------------------------------------------------------------

    @Override
    public boolean visit(OracleSelectQuery x) {
        return oracleSQLASTOutputVisitor.visit(x);
    }


    // ----------- SELECT Details End ------------------------------------------------------------


    // ----------- Table Details Start ------------------------------------------------------------


    // ----------- Table Details End ------------------------------------------------------------
}
