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
package com.gumihoy.sql.basic.ast.statement.ddl.trigger;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.trigger.alter.ISQLAlterTriggerAction;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * ALTER TRIGGER name  ON table_name  RENAME TO new_name
 * ALTER TRIGGER name  ON table_name  DEPENDS ON EXTENSION extension_name
 * <p>
 * https://www.postgresql.org/docs/current/static/sql-altertrigger.html
 * <p>
 * ALTER TRIGGER [ schema. ] trigger_name { trigger_compile_clause | { ENABLE | DISABLE } | RENAME TO new_name | { EDITIONABLE | NONEDITIONABLE } }
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ALTER-TRIGGER-statement.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLAlterTriggerStatement extends AbstractSQLStatement implements ISQLAlterStatement {

    protected ISQLName name;

    protected ISQLName onTableName;

    protected ISQLAlterTriggerAction action;


    public SQLAlterTriggerStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, onTableName);
            this.acceptChild(visitor, action);
        }
    }

    @Override
    public SQLAlterTriggerStatement clone() {
        SQLAlterTriggerStatement x = new SQLAlterTriggerStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLAlterTriggerStatement x) {
        super.cloneTo(x);

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        if (this.onTableName != null) {
            ISQLName onTableNameClone = onTableName.clone();
            x.setOnTableName(onTableNameClone);
        }

        if (this.action != null) {
            ISQLAlterTriggerAction actionClone = action.clone();
            x.setAction(actionClone);
        }

    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.TRIGGER_ALTER;
    }



    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLName getOnTableName() {
        return onTableName;
    }

    public void setOnTableName(ISQLName onTableName) {
        setChildParent(onTableName);
        this.onTableName = onTableName;
    }

    public ISQLAlterTriggerAction getAction() {
        return action;
    }

    public void setAction(ISQLAlterTriggerAction action) {
        setChildParent(action);
        this.action = action;
    }
}
