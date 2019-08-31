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


import com.gumihoy.sql.basic.ast.enums.SQLEditionAbleType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * https://docs.microsoft.com/en-us/sql/t-sql/statements/create-synonym-transact-sql?view=sql-server-2017
 * <p>
 * <p>
 * CREATE [ OR REPLACE ] [ EDITIONABLE | NONEDITIONABLE ]
 * [ PUBLIC ] SYNONYM [ schema. ] synonym
 * [ SHARING = { METADATA | NONE } ] FOR [ schema. ] object [ @ dblink ]
 * https://docs.oracle.com/cd/E11882_01/server.112/e41084/statements_7001.htm
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-SYNONYM.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreateSynonymStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean orReplace = false;

    protected SQLEditionAbleType editionAble;

    protected boolean public_ = false;

    protected ISQLName name;

    protected SQLSharingClause sharingClause;

    protected ISQLName forName;


    public SQLCreateSynonymStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, sharingClause);
            this.acceptChild(visitor, forName);
        }
    }

    @Override
    public SQLCreateSynonymStatement clone() {
        SQLCreateSynonymStatement x = new SQLCreateSynonymStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCreateSynonymStatement x) {
        super.cloneTo(x);

        x.setOrReplace(this.orReplace);

//        x.editionAble = this.editionAble;

        x.setPublic_(this.public_);

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

//        x.sharingClause = this.sharingClause;

        ISQLName forNameClone = this.forName.clone();
        x.setForName(forNameClone);
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source instanceof ISQLName) {

            if (source == name) {
                this.setName((ISQLName) target);
                return true;
            }

            if (source == forName) {
                this.setForName((ISQLName) target);
                return true;
            }
        }

        return false;
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.SYNONYM_CREATE;
    }


    public boolean isOrReplace() {
        return orReplace;
    }

    public void setOrReplace(boolean orReplace) {
        this.orReplace = orReplace;
    }

    public SQLEditionAbleType getEditionAble() {
        return editionAble;
    }

    public void setEditionAble(SQLEditionAbleType editionAble) {
        this.editionAble = editionAble;
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

    public SQLSharingClause getSharingClause() {
        return sharingClause;
    }

    public void setSharingClause(SQLSharingClause sharingClause) {
        setChildParent(sharingClause);
        this.sharingClause = sharingClause;
    }

    public ISQLName getForName() {
        return forName;
    }

    public void setForName(ISQLName forName) {
        setChildParent(name);
        this.forName = forName;
    }
}
