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
package com.gumihoy.sql.basic.ast.statement.ddl.view;


import com.gumihoy.sql.basic.ast.enums.SQLSecurityType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLSubQueryRestrictionClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLAlgorithmOptionExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLDefinerOptionExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * ALTER VIEW [ IF EXISTS ] name ALTER [ COLUMN ] column_name SET DEFAULT expression
 * ALTER VIEW [ IF EXISTS ] name ALTER [ COLUMN ] column_name DROP DEFAULT
 * ALTER VIEW [ IF EXISTS ] name OWNER TO { new_owner | CURRENT_USER | SESSION_USER }
 * ALTER VIEW [ IF EXISTS ] name RENAME TO new_name
 * ALTER VIEW [ IF EXISTS ] name SET SCHEMA new_schema
 * ALTER VIEW [ IF EXISTS ] name SET ( view_option_name [= view_option_value] [, ... ] )
 * ALTER VIEW [ IF EXISTS ] name RESET ( view_option_name [, ... ] )
 * https://www.postgresql.org/docs/10/static/sql-alterview.html
 * <p>
 * <p>
 * ALTER VIEW [ schema. ] view { ADD out_of_line_constraint | MODIFY CONSTRAINT constraint { RELY | NORELY } | DROP { CONSTRAINT constraint | PRIMARY KEY | UNIQUE (column [, column ]...) } | COMPILE | { READ ONLY | READ WRITE } | { EDITIONABLE | NONEDITIONABLE } } ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-VIEW.html#GUID-0DEDE960-B481-4B55-8027-EA9E4C863625
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLAlterViewStatement extends AbstractSQLStatement implements ISQLAlterStatement {

    protected SQLAlgorithmOptionExpr algorithmOptionExpr;
    protected SQLDefinerOptionExpr definerOptionExpr;
    protected SQLSecurityType securityType;
    protected boolean ifExists;
    protected ISQLName name;
    protected ISQLExpr action;

    protected final List<ISQLExpr> columns = new ArrayList<>();
    protected ISQLSelectQuery subQuery;
    protected ISQLSubQueryRestrictionClause subQueryRestriction;


    public SQLAlterViewStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, action);
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, subQuery);
            this.acceptChild(visitor, subQueryRestriction);
        }
    }


    @Override
    public SQLAlterViewStatement clone() {
        SQLAlterViewStatement x = new SQLAlterViewStatement(this.dbType);
        return x;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {

        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }

        if (source == action) {
            setAction(target);
            return true;
        }

        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.VIEW_ALTER;
    }


    public SQLAlgorithmOptionExpr getAlgorithmOptionExpr() {
        return algorithmOptionExpr;
    }

    public void setAlgorithmOptionExpr(SQLAlgorithmOptionExpr algorithmOptionExpr) {
        setChildParent(algorithmOptionExpr);
        this.algorithmOptionExpr = algorithmOptionExpr;
    }

    public SQLDefinerOptionExpr getDefinerOptionExpr() {
        return definerOptionExpr;
    }

    public void setDefinerOptionExpr(SQLDefinerOptionExpr definerOptionExpr) {
        setChildParent(definerOptionExpr);
        this.definerOptionExpr = definerOptionExpr;
    }

    public SQLSecurityType getSecurityType() {
        return securityType;
    }

    public void setSecurityType(SQLSecurityType securityType) {
        this.securityType = securityType;
    }

    public boolean isIfExists() {
        return ifExists;
    }

    public void setIfExists(boolean ifExists) {
        this.ifExists = ifExists;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public List<ISQLExpr> getColumns() {
        return columns;
    }

    public void addColumn(ISQLExpr column) {
        if (column == null) {
            return;
        }
        setChildParent(column);
        this.columns.add(column);
    }

    public ISQLSelectQuery getSubQuery() {
        return subQuery;
    }

    public void setSubQuery(ISQLSelectQuery subQuery) {
        setChildParent(subQuery);
        this.subQuery = subQuery;
    }

    public ISQLSubQueryRestrictionClause getSubQueryRestriction() {
        return subQueryRestriction;
    }

    public void setSubQueryRestriction(ISQLSubQueryRestrictionClause subQueryRestriction) {
        setChildParent(subQueryRestriction);
        this.subQueryRestriction = subQueryRestriction;
    }

    public ISQLExpr getAction() {
        return action;
    }

    public void setAction(ISQLExpr action) {
        setChildParent(action);
        this.action = action;
    }
}
