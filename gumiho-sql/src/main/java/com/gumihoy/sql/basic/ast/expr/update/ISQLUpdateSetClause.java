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
package com.gumihoy.sql.basic.ast.expr.update;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * SET { { (column [, column ]...) = (subquery)
 * | column = { expr | (subquery) | DEFAULT } }
 * [, { (column [, column]...) = (subquery)
 * | column = { expr | (subquery) | DEFAULT } } ]...
 * <p>
 * | VALUE (t_alias) = { expr | (subquery) }}
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/UPDATE.html#GUID-027A462D-379D-4E35-8611-410F3AC8FDA5
 *
 * @author kent onCondition 2018/2/8.
 */
public interface ISQLUpdateSetClause extends ISQLExpr {
    @Override
    ISQLUpdateSetClause clone();
}
