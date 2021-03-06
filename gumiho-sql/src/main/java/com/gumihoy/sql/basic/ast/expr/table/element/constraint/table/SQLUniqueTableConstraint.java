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
package com.gumihoy.sql.basic.ast.expr.table.element.constraint.table;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#unique%20constraint%20definition
 * <p>
 * <p>
 * [ CONSTRAINT constraint_name ] UNIQUE [INDEX|KEY] [indexName] (column [, column ]...) [ constraint_state ]
 * https://dev.mysql.com/doc/refman/5.6/en/create-table.html
 * <p>
 * [ CONSTRAINT constraint_name ] UNIQUE (column [, column ]...) [ constraint_state ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
 *
 * @author kent onCondition 2018/3/13.
 */
public class SQLUniqueTableConstraint extends AbstractSQLUniqueTableConstraint implements ISQLUniqueTableConstraint {

//    protected SQLIndexFormat indexFormat;
    protected ISQLExpr indexName;


    public SQLUniqueTableConstraint() {
    }

    public SQLUniqueTableConstraint(ISQLName name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, indexName);
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, options);
        }
    }

    @Override
    public SQLUniqueTableConstraint clone() {
        SQLUniqueTableConstraint x = new SQLUniqueTableConstraint();
        this.cloneTo(x);
//        x.indexFormat = this.indexFormat;
        if (this.indexName != null) {
            ISQLExpr indexNameClone = this.indexName.clone();
            x.setIndexName(indexNameClone);
        }
        return x;
    }

//    public SQLIndexFormat getIndexFormat() {
//        return indexFormat;
//    }
//
//    public void setIndexFormat(SQLIndexFormat indexFormat) {
//        this.indexFormat = indexFormat;
//    }

    public ISQLExpr getIndexName() {
        return indexName;
    }

    public void setIndexName(ISQLExpr indexName) {
        setChildParent(indexName);
        this.indexName = indexName;
    }

}
