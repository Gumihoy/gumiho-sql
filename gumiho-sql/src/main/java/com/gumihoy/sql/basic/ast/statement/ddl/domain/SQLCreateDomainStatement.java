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
package com.gumihoy.sql.basic.ast.statement.ddl.domain;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.ISQLConstraint;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.List;

/**
 * @author kent onCondition 2018/1/23.
 * @see {https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#domain definition}
 */
public class SQLCreateDomainStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    public ISQLName name;

    public boolean as;

    public ISQLDataType dataType;

    public ISQLExpr defaultExpr;

    public List<ISQLConstraint> constraints;

    public ISQLName collateName;


    public SQLCreateDomainStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, name);
        }
    }

    @Override
    public SQLCreateDomainStatement clone() {
        SQLCreateDomainStatement x = new SQLCreateDomainStatement(this.dbType);

        x.setName(this.name);

        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        return super.replace(source, target);
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.DOMAIN_CREATE;
    }


    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        if (name != null) {
            name.setParent(this);
        }

        this.name = name;
    }


    public boolean isAs() {
        return as;
    }

    public void setAs(boolean as) {
        this.as = as;
    }

    public ISQLDataType getDataType() {
        return dataType;
    }

    public void setDataType(ISQLDataType dataType) {
        this.dataType = dataType;
    }

    public ISQLExpr getDefaultExpr() {
        return defaultExpr;
    }

    public void setDefaultExpr(ISQLExpr defaultExpr) {
        this.defaultExpr = defaultExpr;
    }

    public List<ISQLConstraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<ISQLConstraint> constraints) {
        this.constraints = constraints;
    }

    public ISQLName getCollateName() {
        return collateName;
    }

    public void setCollateName(ISQLName collateName) {
        this.collateName = collateName;
    }
}
