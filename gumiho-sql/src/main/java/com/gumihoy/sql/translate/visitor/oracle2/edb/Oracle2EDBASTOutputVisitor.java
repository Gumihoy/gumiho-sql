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
package com.gumihoy.sql.translate.visitor.oracle2.edb;

import com.gumihoy.sql.config.SQLOutputConfig;
import com.gumihoy.sql.dialect.edb.visitor.IEDBASTVisitor;
import com.gumihoy.sql.dialect.oracle.visitor.OracleASTOutputVisitor;
import com.gumihoy.sql.dialect.postgresql.visitor.PostgreSQLASTOutputVisitor;

/**
 * @author kent onCondition 2018/1/16.
 */
public class Oracle2EDBASTOutputVisitor extends OracleASTOutputVisitor implements IEDBASTVisitor {

    protected PostgreSQLASTOutputVisitor postgreSQLASTOutputVisitor;

    public Oracle2EDBASTOutputVisitor(StringBuilder appender, SQLOutputConfig config) {
        super(appender, config);
        postgreSQLASTOutputVisitor = new PostgreSQLASTOutputVisitor(appender, config);
    }


    // ------------------------- Expr Start ----------------------------------------
//    @Override
//    public boolean visit(PostgreSQLSQLTypeCastExpr x) {
//        x.getExpr().accept(this);
//        print(SQLReserved.DOUBLE_COLON);
//        x.getDataType().accept(this);
//        return false;
//    }
//
//    @Override
//    public boolean visit(PostgreSQLSQLPositionVariableExpr x) {
//        print(SQLReserved.DOLLAR);
//        print(x.getPosition());
//        return false;
//    }
    // ------------------------- Expr End ----------------------------------------

}
