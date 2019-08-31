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
package com.gumihoy.sql.basic.ast.expr.databaselink;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * @author kent onCondition 2018/3/16.
 * @see {https://docs.oracle.com/database/121/SQLRF/statements_5006.htm#SQLRF01205}
 */
public class SQLDBLinkAuthenticationClause extends AbstractSQLExpr {

    protected ISQLExpr user;
    protected ISQLExpr password;

    public SQLDBLinkAuthenticationClause() {
    }

    public SQLDBLinkAuthenticationClause(ISQLExpr user, ISQLExpr password) {
        setUser(user);
        setPassword(password);
    }

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, user);
            this.acceptChild(visitor, password);
        }
    }

    @Override
    public SQLDBLinkAuthenticationClause clone() {
        SQLDBLinkAuthenticationClause x = new SQLDBLinkAuthenticationClause();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLDBLinkAuthenticationClause x) {

    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == user) {
            setUser(target);
            return true;
        }
        if (source == password) {
            setPassword(target);
            return true;
        }
        return false;
    }

    public ISQLExpr getUser() {
        return user;
    }

    public void setUser(ISQLExpr user) {
        setChildParent(user);
        this.user = user;
    }

    public ISQLExpr getPassword() {
        return password;
    }

    public void setPassword(ISQLExpr password) {
        setChildParent(password);
        this.password = password;
    }
}
