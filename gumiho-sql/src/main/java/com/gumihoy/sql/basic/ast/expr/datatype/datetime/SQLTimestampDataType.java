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
package com.gumihoy.sql.basic.ast.expr.datatype.datetime;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * TIMESTAMP [ <left paren> <timestamp precision> <right paren> ] [ <with or without time zone> ]
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#datetime%20type
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483
 *
 * @author kent onCondition 2018/3/11.
 */
public class SQLTimestampDataType extends AbstractSQLDateTimeDataType {

    public SQLTimestampDataType() {
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, arguments);
        }
    }

    @Override
    public SQLTimestampDataType clone() {
        SQLTimestampDataType x = new SQLTimestampDataType();

        cloneTo(x);
        return x;
    }

    public void cloneTo(SQLTimestampDataType x) {
        super.cloneTo(x);


    }
}
