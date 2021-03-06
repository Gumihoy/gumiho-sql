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

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;

/**
 * RELEASE SAVEPOINT identifier
 * https://dev.mysql.com/doc/refman/8.0/en/savepoint.html
 *
 * @author kent onCondition 2018/6/29.
 */
public class SQLReleaseSavepointStatement extends AbstractSQLStatement {

    public ISQLExpr name;

    public SQLReleaseSavepointStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }


    @Override
    public SQLReleaseSavepointStatement clone() {
        SQLReleaseSavepointStatement x = new SQLReleaseSavepointStatement(this.dbType);
        this.cloneTo(x);

        if (this.name != null) {
            ISQLExpr nameClone = this.name.clone();
            x.setName(nameClone);
        }

        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            setName(target);
            return true;
        }
        return false;
    }
//
//    @Override
//    public SQLObjectType getObjectType() {
//        return SQLObjectType.RELEASE_SAVEPOINT;
//    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }

}
