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
public interface ISQLParallelClause extends ISQLExpr, ISQLIndexAttribute {

    @Override
    ISQLParallelClause clone();

    /**
     * PARALLEL [ integer ]
     * <p>
     * https://docs.oracle.com/database/121/SQLRF/clauses005.htm#SQLRF30009
     *
     * @author kent onCondition 2018/3/16.
     */
    public class SQLParallelClause extends AbstractSQLExpr implements ISQLParallelClause {

        protected ISQLExpr value;

        public SQLParallelClause() {
        }

        public SQLParallelClause(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }


        @Override
        public SQLParallelClause clone() {
            SQLParallelClause x = new SQLParallelClause();

            if (this.value != null) {
                ISQLExpr valueClone = this.value.clone();
                x.setValue(valueClone);
            }
            return x;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


    /**
     * NOPARALLEL
     * <p>
     * https://docs.oracle.com/database/121/SQLRF/clauses005.htm#SQLRF30009
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/parallel_clause.html#GUID-59C9EBF3-A45E-4EE5-ABE7-0DA0FCF6C4B5
     *
     * @author kent onCondition 2018/3/16.
     */
    public class SQLNoParallelClause extends AbstractSQLExpr implements ISQLParallelClause {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLNoParallelClause clone() {
            return new SQLNoParallelClause();
        }
    }






}
