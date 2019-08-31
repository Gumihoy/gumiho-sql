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
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * https://www.postgresql.org/docs/10/static/sql-comment.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/COMMENT.html#GUID-65F447C4-6914-4823-9691-F15D52DB74D7
 *
 * @author kent onCondition 2018/3/27.
 */
public abstract class AbstractSQLCommentStatement extends AbstractSQLStatement implements ISQLCommentStatement {

    protected ISQLName name;
    protected ISQLExpr comment;

    public AbstractSQLCommentStatement(DBType dbType) {
        super(dbType);
    }

    public AbstractSQLCommentStatement(ISQLName name, ISQLExpr comment, DBType dbType) {
        super(dbType);
        setName(name);
        setComment(comment);
    }

    @Override
    public AbstractSQLCommentStatement clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    public void cloneTo(AbstractSQLCommentStatement x) {
        super.cloneTo(x);

        ISQLExpr commentClone = this.comment.clone();
        x.setComment(commentClone);
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }
        if (source == comment) {
            setComment(target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.COMMENT;
    }

    @Override
    public ISQLName getName() {
        return name;
    }

    @Override
    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    @Override
    public ISQLExpr getComment() {
        return comment;
    }

    @Override
    public void setComment(ISQLExpr comment) {
        setChildParent(comment);
        this.comment = comment;
    }

}
