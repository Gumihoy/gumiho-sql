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
package com.gumihoy.sql.basic.ast.statement.ddl.function;


import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.enums.SQLEditionAbleType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLParameterDeclaration;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATE [ OR REPLACE ] FUNCTION  name
 * [ (parameter declaration...) ]  [ dispatch ]
 * <p>
 * <p>
 * dispatch:  STATIC DISPATCH
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#schema%20function
 * <p>
 * CREATE [DEFINER = { user | CURRENT_USER }] FUNCTION sp_name ([func_parameter[,...]])
 * RETURNS type
 * [characteristic ...] routine_body
 * https://dev.mysql.com/doc/refman/8.0/en/create-procedure.html
 * <p>
 * CREATE [ OR REPLACE ] [ EDITIONABLE | NONEDITIONABLE ] FUNCTION [ schema. ] function_name
 * [ ( parameter_declaration [, parameter_declaration]... ) ] RETURN datatype
 * [ sharing_clause ] [ { invoker_rights_clause | accessible_by_clause | default_collation_clause | deterministic_clause | parallel_enable_clause | result_cache_clause | aggragate_clause | pipelined_clause }... ] { IS | AS }
 * { [ declare_section ] body | call_spec } ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-FUNCTION-statement.html#GUID-B71BC5BD-B87C-4054-AAA5-213E856651F2
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreateFunctionStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean orReplace;

    protected SQLEditionAbleType editionAbleType;

    protected boolean aggregate;

//    protected SQLDefinerOptionExpr definerOptionExpr;

    protected ISQLName name;

    protected final List<SQLParameterDeclaration> parameters = new ArrayList<>();

    protected ISQLDataType returnDataType;

    protected SQLSharingClause sharingClause;

    protected final List<ISQLExpr> options = new ArrayList<>();

    protected boolean dispatch;

    protected SQLASType as;

    protected final List<ISQLExpr> declareSections = new ArrayList<>();

    protected ISQLExpr asExpr;


    public SQLCreateFunctionStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, parameters);
            this.acceptChild(visitor, returnDataType);
            this.acceptChild(visitor, sharingClause);
            this.acceptChild(visitor, options);
            this.acceptChild(visitor, declareSections);
            this.acceptChild(visitor, asExpr);
        }
    }

    @Override
    public SQLCreateFunctionStatement clone() {
        SQLCreateFunctionStatement x = new SQLCreateFunctionStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLCreateFunctionStatement x) {
        super.cloneTo(x);

        x.orReplace = this.orReplace;

        x.editionAbleType = this.editionAbleType;

        x.aggregate = this.aggregate;

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        for (SQLParameterDeclaration parameter : parameters) {
            SQLParameterDeclaration parameterClone = parameter.clone();
            x.addParameter(parameterClone);
        }

        if (this.returnDataType != null) {
            ISQLDataType returnDataTypeClone = this.returnDataType.clone();
            x.setReturnDataType(returnDataTypeClone);
        }

        if (this.sharingClause != null) {
            SQLSharingClause sharingClauseClone = this.sharingClause.clone();
            x.setSharingClause(sharingClauseClone);
        }

        for (ISQLExpr option : this.options) {
            ISQLExpr optionClone = option.clone();
            x.addOption(optionClone);
        }

        x.as = this.as;

        for (ISQLExpr declareSection : this.declareSections) {
            ISQLExpr declareSectionClone = declareSection.clone();
            x.addDeclareSection(declareSectionClone);
        }
        if (this.asExpr != null) {
            ISQLExpr asExprClone = this.asExpr.clone();
            x.setAsExpr(asExprClone);
        }

    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            this.setName((ISQLName) target);
            return true;
        }

        if (source == returnDataType
                && target instanceof ISQLDataType) {
            this.setReturnDataType((ISQLDataType) target);
            return true;
        }
        return false;
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.FUNCTION_CREATE;
    }


    public boolean isOrReplace() {
        return orReplace;
    }

    public void setOrReplace(boolean orReplace) {
        this.orReplace = orReplace;
    }

    public SQLEditionAbleType getEditionAbleType() {
        return editionAbleType;
    }

    public void setEditionAbleType(SQLEditionAbleType editionAbleType) {
        this.editionAbleType = editionAbleType;
    }

    public boolean isAggregate() {
        return aggregate;
    }

    public void setAggregate(boolean aggregate) {
        this.aggregate = aggregate;
    }


//    public SQLDefinerOptionExpr getDefinerOptionExpr() {
//        return definerOptionExpr;
//    }
//
//    public SQLCreateFunctionStatement setDefinerOptionExpr(SQLDefinerOptionExpr definerOptionExpr) {
//        this.definerOptionExpr = definerOptionExpr;
//        return this;
//    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public List<SQLParameterDeclaration> getParameters() {
        return parameters;
    }

    public void addParameter(SQLParameterDeclaration parameter) {
        if (parameter == null) {
            return;
        }
        parameter.setParent(this);
        this.parameters.add(parameter);
    }

    public ISQLDataType getReturnDataType() {
        return returnDataType;
    }

    public void setReturnDataType(ISQLDataType returnDataType) {
        setChildParent(returnDataType);
        this.returnDataType = returnDataType;
    }

    public SQLSharingClause getSharingClause() {
        return sharingClause;
    }

    public void setSharingClause(SQLSharingClause sharingClause) {
        setChildParent(sharingClause);
        this.sharingClause = sharingClause;
    }

    public List<ISQLExpr> getOptions() {
        return options;
    }

    public void addOption(ISQLExpr option) {
        if (option == null) {
            return;
        }
        setChildParent(option);
        this.options.add(option);
    }

    public boolean isDispatch() {
        return dispatch;
    }

    public void setDispatch(boolean dispatch) {
        this.dispatch = dispatch;
    }

    public SQLASType getAs() {
        return as;
    }

    public void setAs(SQLASType as) {
        this.as = as;
    }

    public List<ISQLExpr> getDeclareSections() {
        return declareSections;
    }

    public void addDeclareSection(ISQLExpr declareSection) {
        if (declareSection == null) {
            return;
        }
        setChildParent(declareSection);
        this.declareSections.add(declareSection);
    }

    public ISQLExpr getAsExpr() {
        return asExpr;
    }

    public void setAsExpr(ISQLExpr asExpr) {
        setChildParent(asExpr);
        this.asExpr = asExpr;
    }
}
