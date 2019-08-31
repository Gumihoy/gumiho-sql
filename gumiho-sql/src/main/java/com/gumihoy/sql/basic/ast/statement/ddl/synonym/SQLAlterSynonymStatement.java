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


import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.synonym.alter.ISQLAlterSynonymAction;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.awt.*;

/**
 * ALTER [ PUBLIC ] SYNONYM [ schema. ] synonym { EDITIONABLE | NONEDITIONABLE | COMPILE } ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/ALTER-SYNONYM.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLAlterSynonymStatement extends AbstractSQLStatement implements ISQLAlterStatement {


    protected boolean public_;

    protected ISQLName name;

    protected ISQLAlterSynonymAction action;


    public SQLAlterSynonymStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }


    @Override
    public SQLAlterSynonymStatement clone() {
        SQLAlterSynonymStatement x = new SQLAlterSynonymStatement(this.dbType);
        cloneTo(x);
        return x;
    }


    public void cloneTo(SQLAlterSynonymStatement x) {
        super.cloneTo(x);

        x.public_ = this.public_;

        ISQLName cloneName = this.name.clone();
        x.setName(cloneName);

        x.action = this.action;

    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.SYNONYM_ALTER;
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

    public ISQLAlterSynonymAction getAction() {
        return action;
    }

    public void setAction(ISQLAlterSynonymAction action) {
        setChildParent(name);
        this.action = action;
    }
}
