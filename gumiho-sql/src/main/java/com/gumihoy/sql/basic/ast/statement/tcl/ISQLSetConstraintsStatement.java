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
package com.gumihoy.sql.basic.ast.statement.tcl;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SET { CONSTRAINT | CONSTRAINTS } { constraint [, constraint ]... | ALL } { IMMEDIATE | DEFERRED } ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SET-CONSTRAINTS.html#GUID-1EF5B212-17C5-4F7C-9412-D777DFDEDCE9
 *
 * @author kent onCondition 2018/6/29.
 */
public interface ISQLSetConstraintsStatement extends SQLTCLStatement {
    @Override
    ISQLSetConstraintsStatement clone();

    /**
     * ALL
     */
    class SQLAllItem extends AbstractSQLExpr {

        public static SQLAllItem of () {
            return new SQLAllItem();
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLAllItem clone() {
            return new SQLAllItem();
        }
    }

    enum SQLOption implements ISQLASTEnum {
//        IMMEDIATE(SQLReserved.IMMEDIATE),
//        DEFERRED(SQLReserved.DEFERRED);
        ;

        public final String lower;
        public final String upper;


        SQLOption(String lower, String upper) {
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
