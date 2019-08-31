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
package com.gumihoy.sql.basic.ast.statement.ddl.type;


import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.type.alter.ISQLAlterTypeAction;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * ALTER TYPE typename <alter type action>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#alter%20type%20statement
 * <p>
 * <p>
 * ALTER TYPE name action [, ... ]
 * ALTER TYPE name OWNER TO { new_owner | CURRENT_USER | SESSION_USER }
 * ALTER TYPE name RENAME ATTRIBUTE attribute_name TO new_attribute_name [ CASCADE | RESTRICT ]
 * ALTER TYPE name RENAME TO new_name
 * ALTER TYPE name SET SCHEMA new_schema
 * ALTER TYPE name ADD VALUE [ IF NOT EXISTS ] new_enum_value [ { BEFORE | AFTER } neighbor_enum_value ]
 * ALTER TYPE name RENAME VALUE existing_enum_value TO new_enum_value
 * <p>
 * https://www.postgresql.org/docs/devel/static/sql-altertype.html
 * <p>
 * <p>
 * ALTER TYPE [ schema. ] type_name { { EDITIONABLE | NONEDITIONABLE } | alter_type_clause } ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ALTER-TYPE-statement.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLAlterTypeStatement extends AbstractSQLStatement implements ISQLAlterStatement {

    protected ISQLName name;

    protected final List<ISQLAlterTypeAction> actions = new ArrayList<>();


    public SQLAlterTypeStatement(DBType dbType) {
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
    public SQLAlterTypeStatement clone() {
        SQLAlterTypeStatement x = new SQLAlterTypeStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLAlterTypeStatement x) {
        super.cloneTo(x);

        ISQLName cloneName = this.name.clone();
        x.setName(cloneName);

        for (ISQLAlterTypeAction action : actions) {
            ISQLAlterTypeAction actionClone = action.clone();
            x.addAction(actionClone);
        }
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.TYPE_ALTER;
    }


    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }


    public List<ISQLAlterTypeAction> getActions() {
        return actions;
    }

    public void addAction(ISQLAlterTypeAction action) {
        if (action == null) {
            return;
        }
        setChildParent(action);
        this.actions.add(action);
    }

}
