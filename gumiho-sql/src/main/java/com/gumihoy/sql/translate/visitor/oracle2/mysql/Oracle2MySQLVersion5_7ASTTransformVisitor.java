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

import com.gumihoy.sql.basic.ast.expr.method.SQLMethodInvocation;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLSequenceExpr;
import com.gumihoy.sql.translate.SQLTransformConfig;

/**
 * @author kent onCondition 2018/1/16.
 */
public class Oracle2MySQLVersion5_7ASTTransformVisitor extends Oracle2MySQLVersion8_0ASTTransformVisitor {

    public Oracle2MySQLVersion5_7ASTTransformVisitor(SQLTransformConfig config) {
        super(config);
    }




    // ------------------------- Data Types Start ----------------------------------------


    // ------------------------- Data Types End ----------------------------------------

    // ------------------------- Operators Start ----------------------------------------



    // ------------------------- Operators End ----------------------------------------


    // ------------------------- Functions Start ----------------------------------------


    @Override
    public boolean visit(SQLMethodInvocation x) {
        boolean visit = super.visit(x);

//        long nameLowerHash = x.lowerHash();
//        List<ISQLExpr> arguments = x.getArguments();
//        int size = arguments.size();
//
//        ISQLExpr target = x;
//
//        if (x != target) {
//            SQLUtils.replaceInParent(x, target);
//            target.accept(this);
//            return false;
//        }

        return super.visit(x);
    }


    // ------------------------- Functions End ----------------------------------------

    // ------------------------- Expressions Start ----------------------------------------

    @Override
    public boolean visit(SQLSequenceExpr x) {
        return super.visit(x);
    }

    // ------------------------- Expressions End ----------------------------------------


    // ------------------------- column constraint Start ----------------------------------------

    // ------------------------- column constraint End ----------------------------------------


    // ------------------------- table constraint Start ----------------------------------------


    // ------------------------- table constraint End ----------------------------------------


    // ------------------ Select Details Start ----------------------

    // ------------------ Select Details End ----------------------

}
