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
 * Begin --
 * '--' ~('\r' | '\n')*
 *
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#comment
 *
 * @author kent onCondition 2018/3/19.
 */
public class SQLMinusHintExpr extends AbstractSQLComment implements ISQLComment {

    public SQLMinusHintExpr(String comment) {
        super(comment);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLMinusHintExpr clone() {
        SQLMinusHintExpr x = new SQLMinusHintExpr(this.comment);
        return x;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;
    }
}
