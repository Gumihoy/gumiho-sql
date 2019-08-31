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
package com.gumihoy.sql.basic.ast.statement.ddl.database;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * ALTER DATABASE [db_name] alter_specification ...
 * ALTER DATABASE db_name UPGRADE DATA DIRECTORY NAME
 * https://dev.mysql.com/doc/refman/5.6/en/alter-database.html
 * <p>
 * <p>
 * ALTER DATABASE name [ [ WITH ] option [ ... ] ]
 * ALTER DATABASE name RENAME TO new_name
 * ALTER DATABASE name OWNER TO { new_owner | CURRENT_USER | SESSION_USER }
 * ALTER DATABASE name SET TABLESPACE new_tablespace
 * ALTER DATABASE name SET configuration_parameter { TO | = } { value | DEFAULT }
 * ALTER DATABASE name SET configuration_parameter FROM CURRENT
 * ALTER DATABASE name RESET configuration_parameter
 * ALTER DATABASE name RESET ALL
 * https://www.postgresql.org/docs/devel/static/sql-alterdatabase.html
 * <p>
 * ALTER DATABASE [db_name] ...
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-DATABASE.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLAlterDatabaseStatement extends AbstractSQLStatement implements ISQLAlterStatement {

    protected ISQLName name;
    protected final List<ISQLExpr> items = new ArrayList<>();

    public SQLAlterDatabaseStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, items);
        }
    }

    @Override
    public SQLAlterDatabaseStatement clone() {
        SQLAlterDatabaseStatement x = new SQLAlterDatabaseStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterDatabaseStatement x) {
        super.cloneTo(x);
        if (this.name != null) {
            ISQLName nameClone = this.name.clone();
            x.setName(nameClone);
        }

    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.DATABASE_ALTER;
    }

    public ISQLName getName() {
        return name;
    }

    public SQLAlterDatabaseStatement setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
        return this;
    }

    public List<ISQLExpr> getItems() {
        return items;
    }

    public void addItem(ISQLExpr item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }


}
