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
package com.gumihoy.sql.basic.ast.expr.table;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * OBJECT IDENTIFIER IS { SYSTEM GENERATED | PRIMARY KEY }
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/3/25.
 */
public class SQLOidClause extends AbstractSQLExpr {

    private SQLType type;

    public SQLOidClause(SQLType type) {
        this.type = type;
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }


    public SQLType getType() {
        return type;
    }

    public void setType(SQLType type) {
        this.type = type;
    }

    public enum SQLType implements ISQLASTEnum {

        SYSTEM_GENERATED("system generated", "SYSTEM_GENERATED"),
        PRIMARY_KEY("primary key", "PRIMARY KEY");

        public final String lower;
        public final String upper;


        SQLType(String lower, String upper) {
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
