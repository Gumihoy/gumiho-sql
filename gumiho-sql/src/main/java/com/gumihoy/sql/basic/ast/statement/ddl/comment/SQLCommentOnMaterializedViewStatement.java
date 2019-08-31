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
package com.gumihoy.sql.basic.ast.statement.ddl.comment;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;

/**
 * https://www.postgresql.org/docs/10/static/sql-comment.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/COMMENT.html#GUID-65F447C4-6914-4823-9691-F15D52DB74D7
 *
 * @author kent onCondition 2018/3/27.
 */
public class SQLCommentOnMaterializedViewStatement extends AbstractSQLCommentStatement {

    public SQLCommentOnMaterializedViewStatement(DBType dbType) {
        super(dbType);
    }

    public SQLCommentOnMaterializedViewStatement(ISQLName name, ISQLExpr comment, DBType dbType) {
        super(name, comment, dbType);
    }

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, comment);
        }
    }


    @Override
    public SQLCommentOnMaterializedViewStatement clone() {
        SQLCommentOnMaterializedViewStatement x = new SQLCommentOnMaterializedViewStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

}
