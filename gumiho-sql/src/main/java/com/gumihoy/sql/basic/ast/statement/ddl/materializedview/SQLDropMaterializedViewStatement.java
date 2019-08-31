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
package com.gumihoy.sql.basic.ast.statement.ddl.materializedview;


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
 * DROP MATERIALIZED VIEW [ IF EXISTS ] name [, ...] [ CASCADE | RESTRICT ]
 * <p>
 * https://www.postgresql.org/docs/devel/static/sql-dropmaterializedview.html
 * <p>
 * <p>
 * DROP MATERIALIZED VIEW [ schema. ] materialized_view [ PRESERVE TABLE ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/DROP-MATERIALIZED-VIEW.html#GUID-187B88E0-F84A-44DB-8F4D-F477586FD22B
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDropMaterializedViewStatement extends AbstractSQLStatement implements ISQLDropStatement {

    protected boolean ifExists;

    protected final List<ISQLName> names = new ArrayList<>();

    protected SQLOption option;


    public SQLDropMaterializedViewStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, names);
//        }
    }

    @Override
    public SQLDropMaterializedViewStatement clone() {
        SQLDropMaterializedViewStatement x = new SQLDropMaterializedViewStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLDropMaterializedViewStatement x) {
        super.cloneTo(x);

        x.ifExists = this.ifExists;

        for (ISQLName name : this.names) {
            ISQLName nameClone = name.clone();
            x.addName(nameClone);
        }

        x.option = this.option;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (target instanceof ISQLName) {
//            boolean replace = replaceInList(names, source, (ISQLName) target, this);
//            if (replace) {
//                return true;
//            }
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.MATERIALIZED_VIEW_DROP;
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

    public SQLOption getOption() {
        return option;
    }

    public void setOption(SQLOption option) {
        this.option = option;
    }


    public enum SQLOption {
        PRESERVE_TABLE("PRESERVE TABLE"),;
        public final String name;

        SQLOption(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
