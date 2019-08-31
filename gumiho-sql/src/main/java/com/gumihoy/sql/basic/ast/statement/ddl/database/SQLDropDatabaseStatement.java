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
package com.gumihoy.sql.basic.ast.statement.ddl.database;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLDropStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * DROP DATABASE ifExists? nameIdentifier
 * https://dev.mysql.com/doc/refman/5.7/en/drop-database.html
 *
 * DROP DATABASE
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/DROP-DATABASE.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDropDatabaseStatement extends AbstractSQLStatement implements ISQLDropStatement {

    protected boolean ifExists;
    protected ISQLName name;

    public SQLDropDatabaseStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLDropDatabaseStatement clone() {
        SQLDropDatabaseStatement x = new SQLDropDatabaseStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLDropDatabaseStatement x) {
        super.cloneTo(x);
        x.ifExists = this.ifExists;
        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);
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
        return SQLObjectType.DATABASE_DROP;
    }

    public boolean isIfExists() {
        return ifExists;
    }

    public SQLDropDatabaseStatement setIfExists(boolean ifExists) {
        this.ifExists = ifExists;
        return this;
    }

    public ISQLName getName() {
        return name;
    }

    public SQLDropDatabaseStatement setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
        return this;
    }
}
