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
package com.gumihoy.sql.basic.ast.expr.table.element.constraint.table;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.AbstractSQLConstraint;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *SCOPE FOR ({ ref_col | ref_attr }) IS [ schema. ] scope_table
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
 *
 * @author kent onCondition 2018/3/13.
 */
public class SQLScopeForTableConstraint extends AbstractSQLConstraint implements ISQLCheckTableConstraint {

    protected ISQLExpr ref;
    protected ISQLName scopeTale;

    public SQLScopeForTableConstraint(ISQLExpr ref, ISQLName scopeTale) {
        setRef(ref);
        setScopeTale(scopeTale);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, ref);
            this.acceptChild(visitor, scopeTale);
        }
    }

    @Override
    public SQLScopeForTableConstraint clone() {
        ISQLExpr refClone = this.ref.clone();
        ISQLName scopeTaleClone =  this.scopeTale.clone();
        SQLScopeForTableConstraint x =new SQLScopeForTableConstraint(refClone, scopeTaleClone);

        return x;
    }

    public ISQLExpr getRef() {
        return ref;
    }

    public void setRef(ISQLExpr ref) {
        setChildParent(ref);
        this.ref = ref;
    }

    public ISQLName getScopeTale() {
        return scopeTale;
    }

    public void setScopeTale(ISQLName scopeTale) {
        setChildParent(scopeTale);
        this.scopeTale = scopeTale;
    }
}
