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
package com.gumihoy.sql.basic.ast.expr.datatype.string;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * LONG RAW
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Data-Types.html#GUID-7B72E154-677A-4342-A1EA-C74C1EA928E6
 *
 * @author kent onCondition 2018/3/11.
 */
public class SQLLongRawDataType extends AbstractSQLStringDataType {

//    public SQLLongRawDataType() {
//        super(SQLReserved.LONG_RAW.ofExpr());
//    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
