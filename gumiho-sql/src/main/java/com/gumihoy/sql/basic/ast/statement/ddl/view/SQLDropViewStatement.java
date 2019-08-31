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
package com.gumihoy.sql.basic.ast.statement.ddl.view;


import com.gumihoy.sql.basic.ast.enums.SQLDropBehavior;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLDropStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * DROP VIEW <table name> [CASCADE | RESTRICT]
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#drop%20view%20statement
 * <p>
 * DROP VIEW [IF EXISTS] view_name [, view_name] ... [RESTRICT | CASCADE]
 * https://dev.mysql.com/doc/refman/8.0/en/drop-view.html
 * <p>
 * <p>
 * DROP VIEW [ IF EXISTS ] name [, ...] [ CASCADE | RESTRICT ]
 * https://www.postgresql.org/docs/devel/static/sql-dropview.html
 * <p>
 * <p>
 * DROP VIEW [ schema. ] view [ CASCADE CONSTRAINTS ] ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/DROP-VIEW.html#GUID-1A1BD841-66B9-47E4-896F-D36E075AE296
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDropViewStatement extends AbstractSQLStatement implements ISQLDropStatement {

    protected boolean ifExists;
    protected final List<ISQLName> names = new ArrayList<>();
    protected SQLDropBehavior behavior;


    public SQLDropViewStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, names);
        }
    }


    @Override
    public SQLDropViewStatement clone() {
        SQLDropViewStatement x = new SQLDropViewStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLDropViewStatement x) {
        super.cloneTo(x);
        x.ifExists = this.ifExists;
        for (ISQLName name : names) {
            ISQLName nameClone = name.clone();
            x.addName(nameClone);
        }
        x.behavior = this.behavior;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source instanceof ISQLName) {
//            boolean replace = replaceInList(names, source, (ISQLName) target, this);
//            if (replace) {
//                return true;
//            }
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.VIEW_DROP;
    }


    public boolean isIfExists() {
        return ifExists;
    }

    public void setIfExists(boolean ifExists) {
        this.ifExists = ifExists;
    }

    public List<ISQLName> getNames() {
        return names;
    }

    public void addName(ISQLName name) {
        if (name == null) {
            return;
        }
        setChildParent(name);
        this.names.add(name);
    }

    public SQLDropBehavior getBehavior() {
        return behavior;
    }

    public void setBehavior(SQLDropBehavior behavior) {
        this.behavior = behavior;
    }
}
