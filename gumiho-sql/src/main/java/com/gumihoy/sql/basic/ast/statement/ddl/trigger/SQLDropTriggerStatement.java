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


import com.gumihoy.sql.basic.ast.enums.SQLDropBehavior;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLDropStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * DROP TRIGGER <trigger name>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#drop%20trigger%20statement
 * <p>
 * DROP TRIGGER [IF EXISTS] [schema_name.]trigger_name
 * https://dev.mysql.com/doc/refman/8.0/en/drop-trigger.html
 * <p>
 * DROP TRIGGER [ IF EXISTS ] name ON table_name [ CASCADE | RESTRICT ]
 * https://www.postgresql.org/docs/devel/static/sql-droptrigger.html
 * <p>
 * DROP TRIGGER [ schema. ] trigger ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/DROP-TRIGGER.html#GUID-724AC8BC-0428-43D3-8F11-4D4AD8DC2984
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDropTriggerStatement extends AbstractSQLStatement implements ISQLDropStatement {

    protected boolean ifExists;
    protected ISQLName name;
    protected ISQLName onTableName;
    protected SQLDropBehavior dropBehavior;

    public SQLDropTriggerStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, onTableName);
        }
    }

    @Override
    public SQLDropTriggerStatement clone() {
        SQLDropTriggerStatement x = new SQLDropTriggerStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLDropTriggerStatement x) {
        super.cloneTo(x);

        x.ifExists = this.ifExists;

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        if (this.onTableName != null) {
            ISQLName onTableNameClone = this.onTableName.clone();
            x.setOnTableName(onTableNameClone);
        }

//        x.option = this.option;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.TRIGGER_DROP;
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

    public ISQLName getOnTableName() {
        return onTableName;
    }

    public void setOnTableName(ISQLName onTableName) {
        setChildParent(onTableName);
        this.onTableName = onTableName;
    }

    public SQLDropBehavior getDropBehavior() {
        return dropBehavior;
    }

    public void setDropBehavior(SQLDropBehavior dropBehavior) {
        this.dropBehavior = dropBehavior;
    }
}
