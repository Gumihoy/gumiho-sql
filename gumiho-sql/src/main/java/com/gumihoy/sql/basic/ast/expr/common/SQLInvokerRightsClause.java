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

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * invoker_rights_clause
 * AUTHID (CURRENT_USER | DEFINER)
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/invokers_rights_clause.html#GUID-66745CF3-81D9-491B-BD49-E83E05A5C4E4
 *
 * @author kent onCondition 2018/3/18.
 */
public class SQLInvokerRightsClause extends AbstractOracleExpr {

    protected SQLAuthIdType authidType;

    public SQLInvokerRightsClause(SQLAuthIdType authidType) {
        this.authidType = authidType;
    }

    @Override
    public void accept0(IOracleASTVisitor visitor) {
        visitor.visit(this);
    }

    public SQLAuthIdType getAuthidType() {
        return authidType;
    }

    public void setAuthidType(SQLAuthIdType authidType) {
        this.authidType = authidType;
    }

    public enum SQLAuthIdType implements ISQLASTEnum {

        CURRENT_USER("current_user", "CURRENT_USER"),
        DEFINER("definer", "DEFINER");

        public final String lower;
        public final String upper;


        SQLAuthIdType(String lower, String upper) {
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
