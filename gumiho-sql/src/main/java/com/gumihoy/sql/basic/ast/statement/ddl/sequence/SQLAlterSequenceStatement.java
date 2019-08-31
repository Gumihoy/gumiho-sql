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
package com.gumihoy.sql.basic.ast.statement.ddl.sequence;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.sequence.alter.ISQLAlterSequenceAction;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#alter%20sequence%20generator%20statement
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLAlterSequenceStatement extends AbstractSQLStatement implements ISQLAlterStatement {


    protected boolean ifExists = false;

    protected ISQLName name;

    // --------- options ------------------

    protected final List<ISQLAlterSequenceAction> actions = new ArrayList<>();


    public SQLAlterSequenceStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, actions);
        }
    }


    @Override
    public SQLAlterSequenceStatement clone() {
        SQLAlterSequenceStatement x = new SQLAlterSequenceStatement(this.dbType);
        cloneTo(x);
        return x;
    }


    public void cloneTo(SQLAlterSequenceStatement x) {
        super.cloneTo(x);

        x.setIfExists(this.ifExists);

        ISQLName cloneName = this.name.clone();
        x.setName(cloneName);

        for (ISQLAlterSequenceAction action : this.actions) {
            ISQLAlterSequenceAction actionClone = action.clone();
            x.addAction(actionClone);
        }

    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        return super.replace(source, target);
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.SCHEMA_ALTER;
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
        this.name = name;
    }

    public List<ISQLAlterSequenceAction> getActions() {
        return actions;
    }

    public void addAction(ISQLAlterSequenceAction action) {
        if (action == null) {
            return;
        }

        action.setParent(this);
        this.actions.add(action);
    }

}
