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
package com.gumihoy.sql.basic.ast.expr.datatype.numeric;

import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#numeric%20type
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/numeric-types.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483
 *
 * @author kent onCondition 2018/3/11.
 */
public interface ISQLNumericDataType extends ISQLDataType {

    boolean isUnsigned();

    void setUnsigned(boolean unsigned);


    boolean isZerofill();

    void setZerofill(boolean zerofill);
}
