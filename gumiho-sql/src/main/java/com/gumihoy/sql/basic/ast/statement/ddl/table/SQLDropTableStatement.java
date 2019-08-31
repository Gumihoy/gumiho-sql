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
package com.gumihoy.sql.basic.ast.statement.ddl.table;


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
 * DROP TABLE <table name> [CASCADE | RESTRICT]
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#drop%20table%20statement
 * <p>
 *
 * DROP [TEMPORARY] TABLE [IF EXISTS] tbl_name [, tbl_name] ... [RESTRICT | CASCADE]
 * https://dev.mysql.com/doc/refman/5.7/en/drop-table.html
 *
 * DROP TABLE [ schema. ] table [ CASCADE CONSTRAINTS ] [ PURGE ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/DROP-TABLE.html
 *
 * DROP TABLE [ IF EXISTS ] name [, ...] [ CASCADE | RESTRICT ]
 * https://www.postgresql.org/docs/devel/static/sql-droptable.html
 *
 * DROP TABLE [ IF EXISTS ] [ database_name . [ schema_name ] . | schema_name . ] table_name [ ,...n ]
 * https://docs.microsoft.com/en-us/sql/t-sql/statements/drop-table-transact-sql
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDropTableStatement extends AbstractSQLStatement implements ISQLDropStatement {

    protected boolean temporary;

    protected boolean ifExists = false;

    protected final List<ISQLName> names = new ArrayList<>(1);

    protected SQLDropBehavior dropBehavior;

    protected boolean purge;

    public SQLDropTableStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, names);
        }
    }


    @Override
    public SQLDropTableStatement clone() {
        SQLDropTableStatement x = new SQLDropTableStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLDropTableStatement x) {
        super.cloneTo(x);

        x.temporary = this.temporary;

        x.ifExists = this.ifExists;

        for (ISQLName name : this.names) {
            ISQLName nameClone = name.clone();
            x.names.add(nameClone);
        }
//        x.dropBehavior = this.dropBehavior;
        x.purge = this.purge;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (target instanceof ISQLName) {
            boolean replace = replaceInList(names, source,  (ISQLName)target, this);
            if (replace) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.TABLE_DROP;
    }


    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
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
        name.setParent(this);
        this.names.add(name);
    }

    public SQLDropBehavior getDropBehavior() {
        return dropBehavior;
    }

    public void setDropBehavior(SQLDropBehavior dropBehavior) {
        this.dropBehavior = dropBehavior;
    }

    public boolean isPurge() {
        return purge;
    }

    public void setPurge(boolean purge) {
        this.purge = purge;
    }
}
