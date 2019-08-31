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
package com.gumihoy.sql.basic.ast.statement.ddl.schema;


import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLDropStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * DROP SCHEMA <schema name> behavior=[CASCADE | RESTRICT]
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#drop%20schema%20statement
 * <p>
 * DROP SCHEMA [IF EXISTS] db_name
 * https://dev.mysql.com/doc/refman/5.7/en/drop-database.html
 * <p>
 * DROP SCHEMA [ IF EXISTS ] name [, ...] [ CASCADE | RESTRICT ]
 * <p>
 * https://www.postgresql.org/docs/9.6/static/sql-dropschema.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDropSchemaStatement extends AbstractSQLStatement implements ISQLDropStatement {

    protected boolean ifExists;
    protected final List<ISQLName> names = new ArrayList<>();
//    protected SQLCascadeType option;


    public SQLDropSchemaStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, names);
        }
    }


    @Override
    public ISQLStatement clone() {
        return null;
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.SCHEMA_DROP;
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


//    public SQLCascadeType getOption() {
//        return option;
//    }
//
//    public void setOption(SQLCascadeType option) {
//        this.option = option;
//    }
}
