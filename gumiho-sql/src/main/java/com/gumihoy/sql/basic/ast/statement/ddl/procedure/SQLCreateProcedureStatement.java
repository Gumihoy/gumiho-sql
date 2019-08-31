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
package com.gumihoy.sql.basic.ast.statement.ddl.procedure;


import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.enums.SQLEditionAbleType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLParameterDeclaration;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATE   PROCEDURE <schema qualified routine name>
 * <left paren> [ <SQL parameter declaration> [ { <comma> <SQL parameter declaration> }... ] ] <right paren>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#schema%20procedure
 * <p>
 * CREATE [DEFINER = { user | CURRENT_USER }] PROCEDURE sp_name ([proc_parameter[,...]])
 * [characteristic ...] routine_body
 * https://dev.mysql.com/doc/refman/8.0/en/create-procedure.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-PROCEDURE-statement.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreateProcedureStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean orReplace;

    protected SQLEditionAbleType editionAbleType;

//    protected SQLDefinerOptionExpr definerOptionExpr;

    protected ISQLName name;

    protected final List<SQLParameterDeclaration> parameters = new ArrayList<>();

    protected SQLSharingClause sharingClause;

    protected final List<ISQLExpr> options = new ArrayList<>();

    protected SQLASType as;

    protected final List<ISQLExpr> declareSections = new ArrayList<>();

    protected ISQLExpr asExpr;


    public SQLCreateProcedureStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, parameters);
            this.acceptChild(visitor, sharingClause);
            this.acceptChild(visitor, options);
            this.acceptChild(visitor, declareSections);
            this.acceptChild(visitor, asExpr);
        }
    }

    @Override
    public SQLCreateProcedureStatement clone() {
        SQLCreateProcedureStatement x = new SQLCreateProcedureStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCreateProcedureStatement x) {
        super.cloneTo(x);

        x.orReplace = this.orReplace;
        x.editionAbleType = this.editionAbleType;
//
//        if (this.definerOptionExpr != null) {
//
//        }

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        for (SQLParameterDeclaration parameter : this.parameters) {
            SQLParameterDeclaration parameterClone = parameter.clone();
            x.addParameter(parameterClone);
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
//
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
        if (source == name) {
            this.setName((ISQLName) target);
            return true;
        }

        return false;
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.PROCEDURE_CREATE;
    }

    public boolean isOrReplace() {
        return orReplace;
    }

    public void setOrReplace(boolean orReplace) {
        this.orReplace = orReplace;
    }


    //
    public SQLEditionAbleType getEditionAbleType() {
        return editionAbleType;
    }

    public void setEditionAbleType(SQLEditionAbleType editionAbleType) {
        this.editionAbleType = editionAbleType;
    }
//
//    public SQLDefinerOptionExpr getDefinerOptionExpr() {
//        return definerOptionExpr;
//    }

//    public SQLCreateProcedureStatement setDefinerOptionExpr(SQLDefinerOptionExpr definerOptionExpr) {
//        setChildParent(definerOptionExpr);
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
