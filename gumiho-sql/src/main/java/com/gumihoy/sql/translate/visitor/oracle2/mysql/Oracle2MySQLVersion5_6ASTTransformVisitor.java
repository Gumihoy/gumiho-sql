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

import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLAlterIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLCreateIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLDropIndexStatement;
import com.gumihoy.sql.translate.SQLTransformConfig;

/**
 * @author kent onCondition 2018/1/16.
 */
public class Oracle2MySQLVersion5_6ASTTransformVisitor extends Oracle2MySQLVersion5_7ASTTransformVisitor {

    public Oracle2MySQLVersion5_6ASTTransformVisitor(SQLTransformConfig config) {
        super(config);
    }


    @Override
    public boolean visit(SQLCreateIndexStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterIndexStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropIndexStatement x) {
        return super.visit(x);
    }


    // ------------------------- Data Types Start ----------------------------------------


    // ------------------------- Data Types End ----------------------------------------


    // ------------------------- Operators Start ----------------------------------------



    // ------------------------- Operators End ----------------------------------------


    // ------------------------- Expressions Start ----------------------------------------


    // ------------------------- Expressions End ----------------------------------------


    // ------------------------- column constraint Start ----------------------------------------

    // ------------------------- column constraint End ----------------------------------------


    // ------------------------- table constraint Start ----------------------------------------



    // ------------------------- table constraint End ----------------------------------------


    // ------------------ Select Details Start ----------------------


    // ------------------ Select Details End ----------------------

}
