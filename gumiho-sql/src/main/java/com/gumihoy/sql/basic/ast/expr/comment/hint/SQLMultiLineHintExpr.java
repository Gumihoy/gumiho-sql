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
package com.gumihoy.sql.basic.ast.expr.comment.hint;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.comment.AbstractSQLComment;
import com.gumihoy.sql.basic.ast.expr.comment.ISQLComment;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * /*! MySQL-specific code * /
 * https://dev.mysql.com/doc/refman/5.6/en/comments.html
 *
 *
 * /*+ MySQL-specific code * /
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Comments.html#GUID-D316D545-89E2-4D54-977F-FC97815CD62E
 *
 * @author kent onCondition 2018/3/19.
 */
public class SQLMultiLineHintExpr extends AbstractSQLComment implements ISQLComment {

    public SQLMultiLineHintExpr(String comment) {
        super(comment);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLMultiLineHintExpr clone() {
        SQLMultiLineHintExpr x = new SQLMultiLineHintExpr(this.comment);
        return x;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;
    }
}
