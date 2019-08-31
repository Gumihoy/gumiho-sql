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
package com.gumihoy.sql.basic.ast.statement.ddl.databaselink;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * ALTER [ SHARED ] [ PUBLIC ] DATABASE LINK dblink { CONNECT TO user IDENTIFIED BY password [ dblink_authentication ] | dblink_authentication };
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-DATABASE-LINK.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLAlterDatabaseLinkStatement extends AbstractSQLStatement implements ISQLAlterStatement {

    protected boolean shared;
    protected boolean public_;
    protected ISQLName name;

    protected ISQLExpr action;

    public SQLAlterDatabaseLinkStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, action);
        }
    }


    @Override
    public SQLAlterDatabaseLinkStatement clone() {
        SQLAlterDatabaseLinkStatement x = new SQLAlterDatabaseLinkStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterDatabaseLinkStatement x) {
        super.cloneTo(x);

    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }
        return false;
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.DATABASE_LINK_ALTER;
    }


    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean isPublic_() {
        return public_;
    }

    public void setPublic_(boolean public_) {
        this.public_ = public_;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLExpr getAction() {
        return action;
    }

    public void setAction(ISQLExpr action) {
        setChildParent(action);
        this.action = action;
    }
}
