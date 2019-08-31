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
package com.gumihoy.sql.basic.ast.statement.ddl.user;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
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
 * CREATE USER [IF NOT EXISTS]
 * user [auth_option] [, user [auth_option]] ...
 * DEFAULT ROLE role [, role ] ...
 * [REQUIRE {NONE | tls_option [[AND] tls_option] ...}]
 * [WITH resource_option [resource_option] ...]
 * [password_option | lock_option] ...
 * <p>
 * https://dev.mysql.com/doc/refman/8.0/en/create-user.html
 * <p>
 * CREATE USER user
 * { {
 * IDENTIFIED
 * <p>
 * {
 * BY password [ [HTTP] DIGEST { ENABLE | DISABLE } ]
 * | EXTERNALLY [ AS 'certificate_DN'  |  AS 'kerberos_principal_name' ]
 * | GLOBALLY [ AS '[ directory_DN ]' ]
 * }
 * <p>
 * }
 * | NO AUTHENTICATION
 * }
 * [ DEFAULT COLLATION collation_name ]
 * [ DEFAULT TABLESPACE tablespace
 * | [ LOCAL ] TEMPORARY TABLESPACE { tablespace | tablespace_group_name }
 * | { QUOTA { size_clause | UNLIMITED } ON tablespace }...
 * | PROFILE profile
 * | PASSWORD EXPIRE
 * | ACCOUNT { LOCK | UNLOCK }
 * [ DEFAULT TABLESPACE tablespace
 * | TEMPORARY TABLESPACE
 * { tablespace | tablespace_group_name }
 * | { QUOTA { size_clause | UNLIMITED } ON tablespace }...
 * | PROFILE profile
 * | PASSWORD EXPIRE
 * | ACCOUNT { LOCK | UNLOCK }
 * | ENABLE EDITIONS
 * | CONTAINER = { CURRENT | ALL }
 * ]...
 * ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-USER.html
 *
 * @author kent on 2018/1/23.
 */
public class SQLCreateUserStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean ifNotExists;

    protected SQLCreateUserItem userItem;

    protected final List<ISQLExpr> options = new ArrayList<>();


    public SQLCreateUserStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//
//        }
    }

    @Override
    public SQLCreateUserStatement clone() {
        SQLCreateUserStatement x = new SQLCreateUserStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.USER_CREATE;
    }



    public void cloneTo(SQLCreateUserStatement x) {
        super.cloneTo(x);
    }


    public boolean isIfNotExists() {
        return ifNotExists;
    }

    public void setIfNotExists(boolean ifNotExists) {
        this.ifNotExists = ifNotExists;
    }



    /**
     *
     */
    public static class SQLCreateUserItem extends AbstractSQLExpr {

        protected ISQLName name;
        protected ISQLExpr password;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//
//            }
        }

        @Override
        public SQLCreateUserItem clone() {
            SQLCreateUserItem x = new SQLCreateUserItem();
            this.cloneTo(x);
            return x;
        }
    }


}
