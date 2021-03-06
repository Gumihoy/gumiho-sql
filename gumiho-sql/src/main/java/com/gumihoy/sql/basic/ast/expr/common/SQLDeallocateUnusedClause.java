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

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DEALLOCATE UNUSED [ KEEP size_clause ]
 *
 * deallocate_unused_clause
 * <p>
 * https://docs.oracle.com/database/121/SQLRF/clauses003.htm#SQLRF30007
 *
 * @author kent onCondition 2018/3/16.
 */
public class SQLDeallocateUnusedClause extends AbstractSQLExpr {

    public SQLSizeClause keep;

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, keep);
        }
    }

    @Override
    public SQLDeallocateUnusedClause clone() {
        SQLDeallocateUnusedClause x = new SQLDeallocateUnusedClause();

        if (keep!=null) {
            SQLSizeClause keepClone = this.keep.clone();
            x.setKeep(keepClone);
        }
        return x;
    }

    public SQLSizeClause getKeep() {
        return keep;
    }

    public void setKeep(SQLSizeClause keep) {
        setChildParent(keep);
        this.keep = keep;
    }
}
