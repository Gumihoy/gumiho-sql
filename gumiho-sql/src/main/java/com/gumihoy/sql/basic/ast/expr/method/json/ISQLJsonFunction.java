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
package com.gumihoy.sql.basic.ast.expr.method.json;

import com.gumihoy.sql.basic.ast.expr.method.ISQLFunction;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Single-Row-Functions.html#GUID-C13171B3-C070-4137-AC71-7A30BD26F380
 *
 * @author kent onCondition 2018/3/20.
 */
public interface ISQLJsonFunction extends ISQLFunction {

    @Override
    ISQLJsonFunction clone();
}