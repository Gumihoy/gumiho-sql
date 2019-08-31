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
package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://docs.oracle.com/database/121/SQLRF/clauses005.htm#SQLRF30009
 *
 * @author kent onCondition 2018/3/16.
 */
public interface ISQLLoggingClause extends ISQLSegmentAttribute, ISQLIndexAttribute {

    @Override
    ISQLLoggingClause clone();


    /**
     * LOGGING
     * https://docs.oracle.com/database/121/SQLRF/clauses005.htm#SQLRF30009
     *
     * @author kent onCondition 2018/3/16.
     */
     class SQLLoggingClause extends AbstractSQLExpr implements ISQLLoggingClause {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLLoggingClause clone() {
            return new SQLLoggingClause();
        }
    }

    /**
     * NOLOGGING
     *
     * https://docs.oracle.com/database/121/SQLRF/clauses005.htm#SQLRF30009
     *
     * @author kent onCondition 2018/3/16.
     */
    public class SQLNoLoggingClause extends AbstractSQLExpr implements ISQLLoggingClause {


        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLNoLoggingClause clone() {
            return new SQLNoLoggingClause();
        }
    }

    /**
     * FILESYSTEM_LIKE_LOGGING
     * <p>
     * https://docs.oracle.com/database/121/SQLRF/clauses005.htm#SQLRF30009
     *
     * @author kent onCondition 2018/3/16.
     */
     class SQLFilesystemLikeLogging extends AbstractSQLExpr implements ISQLLoggingClause {


        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLFilesystemLikeLogging clone() {
            return new SQLFilesystemLikeLogging();
        }
    }
}
