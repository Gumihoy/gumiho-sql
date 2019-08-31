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
package com.gumihoy.sql.basic.ast.expr.select;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#table%20reference
 *
 * @author kent onCondition 2018/3/24.
 */
public interface ISQLTableReference extends ISQLExpr {

    boolean isParen();
    void setParen(boolean paren);

    boolean isAs();
    void setAs(boolean as);

    ISQLIdentifier getAlias();
    void setAlias(ISQLIdentifier alias);

    ISQLName computeAlias();
    boolean containsAlias(String alias);
    boolean containsAlias(long aliasLowerHash);

    @Override
    ISQLTableReference clone();
}
