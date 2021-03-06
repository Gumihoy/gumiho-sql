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
package com.gumihoy.sql.basic.ast.expr.literal.string;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Literals.html#GUID-F521FBA0-FFED-4079-ABC4-9052218B3FD5
 *
 * @author kent onCondition 2018/3/19.
 */
public class SQLQStringLiteral extends AbstractSQLStringLiteral {

    public SQLQStringLiteral(String value) {
        super(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
