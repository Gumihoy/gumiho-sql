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
package com.gumihoy.sql.translate.visitor.oracle2.postgresql;

import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;

/**
 * @author kent onCondition 2018/1/16.
 */
public class Oracle2PostgreSQLVersion10ASTTransformVisitor extends SQLASTTransformVisitor {

    public Oracle2PostgreSQLVersion10ASTTransformVisitor(SQLTransformConfig config) {
        super(config);
    }


//    @Override
//    public boolean visit(PostgreSQLSQLTypeCastExpr x) {
//        return true;
//    }
//
//    @Override
//    public boolean visit(PostgreSQLSQLPositionVariableExpr x) {
//        return true;
//    }
}
