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

import com.gumihoy.sql.basic.ast.statement.ISQLStatement;

/**
 * Transaction Control Statements
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Types-of-SQL-Statements.html#GUID-FEC504E9-D22D-4082-A092-07891911C5CF
 *
 * @author kent onCondition 2018/6/29.
 */
public interface SQLTCLStatement extends ISQLStatement {
    @Override
    SQLTCLStatement clone();
}
