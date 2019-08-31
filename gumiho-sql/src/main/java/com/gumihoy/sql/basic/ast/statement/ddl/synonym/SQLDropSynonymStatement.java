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
package com.gumihoy.sql.basic.ast.statement.ddl.synonym;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLDropStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * DROP [PUBLIC] SYNONYM [ IF EXISTS ] [ schema. ] synonym [FORCE]
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/DROP-SYNONYM.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDropSynonymStatement extends AbstractSQLStatement implements ISQLDropStatement {

    protected boolean public_ = false;

    protected boolean ifExists;

    protected ISQLName name;

    protected boolean force = false;


    public SQLDropSynonymStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLDropSynonymStatement clone() {
        SQLDropSynonymStatement x = new SQLDropSynonymStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLDropSynonymStatement x) {
        super.cloneTo(x);

        x.setPublic_(this.public_);

        x.ifExists = this.ifExists;

        ISQLName cloneName = name.clone();
        x.setName(cloneName);

        x.force = this.force;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            this.setName((ISQLName) target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.SYNONYM_DROP;
    }


    public boolean isPublic_() {
        return public_;
    }

    public void setPublic_(boolean public_) {
        this.public_ = public_;
    }

    public boolean isIfExists() {
        return ifExists;
    }

    public void setIfExists(boolean ifExists) {
        this.ifExists = ifExists;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }
}
