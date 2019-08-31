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
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATE [ SHARED ] [ PUBLIC ] DATABASE LINK dblink [ CONNECT TO { CURRENT_USER | user IDENTIFIED BY password [ dblink_authentication ] } | dblink_authentication ]...
 * [ USING connect_string ] ;
 * https://docs.oracle.com/database/121/SQLRF/statements_5006.htm#SQLRF01205
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-DATABASE-LINK.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreateDatabaseLinkStatement extends AbstractSQLStatement implements ISQLCreateStatement {


    protected boolean shared = false;

    protected boolean public_ = false;

    protected ISQLName name;

    protected final List<ISQLExpr> actions = new ArrayList<>();

    protected ISQLExpr using;


    public SQLCreateDatabaseLinkStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, actions);
            this.acceptChild(visitor, using);
        }
    }


    @Override
    public SQLCreateDatabaseLinkStatement clone() {
        SQLCreateDatabaseLinkStatement x = new SQLCreateDatabaseLinkStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCreateDatabaseLinkStatement x) {
        x.shared = this.shared;
        x.public_ = this.public_;

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

    }



    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.DATABASE_LINK_CREATE;
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

    public List<ISQLExpr> getActions() {
        return actions;
    }

    public void addAction(ISQLExpr action) {
        if (action == null) {
            return;
        }
        setChildParent(action);
        this.actions.add(action);
    }

    public ISQLExpr getUsing() {
        return using;
    }

    public void setUsing(ISQLExpr using) {
        setChildParent(using);
        this.using = using;
    }
}
