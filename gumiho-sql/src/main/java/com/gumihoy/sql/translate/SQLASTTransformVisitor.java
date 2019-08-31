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
package com.gumihoy.sql.translate;


import com.gumihoy.sql.basic.ast.statement.fcl.SQLAssignmentStatement;
import com.gumihoy.sql.basic.visitor.SQLASTVisitorAdapter;
import com.gumihoy.sql.dialect.edb.visitor.IEDBASTVisitor;
import com.gumihoy.sql.dialect.mysql.ast.expr.select.MySQLOJTableReference;
import com.gumihoy.sql.dialect.mysql.ast.expr.select.MySQLSelectQuery;
import com.gumihoy.sql.dialect.mysql.ast.statement.dml.MySQLInsertStatement;
import com.gumihoy.sql.dialect.mysql.visitor.IMySQLASTVisitor;
import com.gumihoy.sql.dialect.oracle.ast.expr.select.OracleSelectQuery;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;
import com.gumihoy.sql.dialect.oracle.visitor.OracleASTVisitorAdapter;
import com.gumihoy.sql.translate.result.SQLTransformChange;
import com.gumihoy.sql.translate.result.SQLTransformError;
import com.gumihoy.sql.translate.result.SQLTransformWarnning;

import java.util.LinkedHashSet;
import java.util.Set;

public class SQLASTTransformVisitor extends SQLASTVisitorAdapter implements IOracleASTVisitor, IMySQLASTVisitor, IEDBASTVisitor {

    protected SQLTransformConfig config;

    Set<SQLTransformChange> changes = new LinkedHashSet<>();
    Set<SQLTransformWarnning> warnnings = new LinkedHashSet<>();
    Set<SQLTransformError> errors = new LinkedHashSet<>();


    public SQLASTTransformVisitor() {
        this.config = new SQLTransformConfig();
    }

    public SQLASTTransformVisitor(SQLTransformConfig config) {
        if (config == null) {
            config = new SQLTransformConfig();
        }
        this.config = config;
    }


    public Set<SQLTransformChange> getChanges() {
        return changes;
    }

    public void addChange(SQLTransformChange change) {
        if (change == null) {
            return;
        }
        changes.add(change);
    }

    public Set<SQLTransformWarnning> getWarnnings() {
        return warnnings;
    }

    public void addWarnning(SQLTransformWarnning warnning) {
        if (warnning == null) {
            return;
        }
        warnnings.add(warnning);
    }

    public Set<SQLTransformError> getErrors() {
        return errors;
    }

    public void addError(SQLTransformError error) {
        if (error == null) {
            return;
        }
        errors.add(error);
    }


// ----------- Oracle Start ------------------------------------------------------------
    @Override
    public boolean visit(SQLAssignmentStatement x) {
        return true;
    }

    // ------------------------- DataType Start ----------------------------------------

    // ------------------------- DataType End ----------------------------------------


    // ------------------------- Literal Start ----------------------------------------


    // ------------------------- Literal End ----------------------------------------


    // --------------- Common SQL DDL Clauses Start ----------------------------


    // --------------------- Common SQL DDL Clauses End  --------------------------------------------------


    // ----------- PL/SQL Language Elements ------------------------------------------------------------


    // ----------- PL/SQL Language Elements ------------------------------------------------------------


    // ----------- Database Start ------------------------------------------------------------

    // ----------- create ----------------


    // ----------- Database End ------------------------------------------------------------

    // ----------- SELECT Start ------------------------------------------------------------

    @Override
    public boolean visit(OracleSelectQuery x) {
        return false;
    }
    // ----------- SELECT End ------------------------------------------------------------


    // ----------- Table Start ------------------------------------------------------------


    // ----------- Table End ------------------------------------------------------------

// ----------- Oracle End ------------------------------------------------------------


// ----------- PostgreSQL Start ------------------------------------------------------------



// ----------- PostgreSQL End ------------------------------------------------------------


// ----------- MySQL Start ------------------------------------------------------------



    // ------------------------- Comment Start ----------------------------------------

    // ------------------------- Comment End ----------------------------------------

    // ------------------------- Hint Start ----------------------------------------
    // ------------------------- Hint End ----------------------------------------


    // ------------------------- Commons Expr Start ----------------------------------------



    // ------------------------- Commons Expr End ----------------------------------------


    // ------------------ Details ----------------------


    // ------------------ Select Details Start ----------------------

    @Override
    public boolean visit(MySQLInsertStatement x) {
        return false;
    }

    @Override
    public boolean visit(MySQLSelectQuery x) {
        return false;
    }

    @Override
    public boolean visit(MySQLOJTableReference x) {
        return false;
    }


    // ------------------ Select Details End ----------------------

// ----------- MySQL End ------------------------------------------------------------


// ----------- DRDS Start ------------------------------------------------------------

    // ------------------ return true;tails Start ----------------------


    // ------------------ return true;tails End ----------------------
// ----------- DRDS End ------------------------------------------------------------

}
