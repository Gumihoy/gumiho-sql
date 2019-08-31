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


import com.gumihoy.sql.basic.ast.enums.SQLEditionAbleType;
import com.gumihoy.sql.basic.ast.enums.SQLSecurityType;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLSubQueryRestrictionClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLAlgorithmOptionExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLCollateOptionExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLDefinerOptionExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLXmlSchemaSpec;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.table.element.ISQLTableElement;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;
import com.gumihoy.sql.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATE [ RECURSIVE ] VIEW <table name> <view specification> AS <query expression> [ WITH [ <levels clause> ] CHECK OPTION ]
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#view%20definition
 * <p>
 * <p>
 * CREATE [ OR REPLACE ] [ TEMP | TEMPORARY ] [ RECURSIVE ] VIEW name [ ( column_name [, ...] ) ] [ WITH ( view_option_name [= view_option_value] [, ... ] ) ] AS query
 * https://www.postgresql.org/docs/9.3/static/sql-createview.html
 * <p>
 * <p>
 * CREATE
 * [OR REPLACE]
 * [ALGORITHM = {UNDEFINED | MERGE | TEMPTABLE}]
 * [DEFINER = { user | CURRENT_USER }]
 * [SQL SECURITY { DEFINER | INVOKER }]
 * VIEW view_name [(column_list)]
 * AS select_statement
 * [WITH [CASCADED | LOCAL] CHECK OPTION]
 * https://dev.mysql.com/doc/refman/8.0/en/create-view.html
 * <p>
 * <p>
 * CREATE [OR REPLACE]
 * [[NO] FORCE]
 * [ EDITIONING | EDITIONABLE [ EDITIONING ] | NONEDITIONABLE ]
 * VIEW [schema.] view
 * [ SHARING = { METADATA | DATA | EXTENDED DATA | NONE } ]
 * [ ( { alias [ VISIBLE | INVISIBLE ] [ inline_constraint... ]
 * | out_of_line_constraint
 * }
 * [, { alias [ VISIBLE | INVISIBLE ] [ inline_constraint...]
 * | out_of_line_constraint
 * }
 * ]
 * )
 * | object_view_clause
 * | XMLType_view_clause
 * ]
 * [ DEFAULT COLLATION collation_name ]
 * [ BEQUEATH { CURRENT_USER | DEFINER } ]
 * AS subquery [ subquery_restriction_clause ]
 * [ CONTAINER_MAP | CONTAINERS_DEFAULT ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-VIEW.html#GUID-61D2D2B4-DACC-4C7C-89EB-7E50D9594D30
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreateViewStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean orReplace;

    protected SQLForceType force;

//    protected SQLTableScope scope;

    protected boolean recursive;

    protected SQLEditionAbleType editionAble;

    // mysql
    protected SQLAlgorithmOptionExpr algorithmSetOptionExpr;
    protected SQLDefinerOptionExpr definerSetOptionExpr;
    protected SQLSecurityType securityType;


    protected ISQLName name;

    protected SQLSharingClause sharingClause;

    protected ISQLDataType ofDataType;
    protected SQLXmlSchemaSpec xmlSchemaSpec;
    protected ISQLExpr subView;

    protected final List<ISQLTableElement> columns = new ArrayList<>();

    protected SQLCollateOptionExpr collationExpr;
    protected SQLBequeathType bequeath;

    protected ISQLSelectQuery subQuery;

    protected ISQLSubQueryRestrictionClause subQueryRestriction;
    protected SQLContainerType container;

    public SQLCreateViewStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, sharingClause);
            this.acceptChild(visitor, ofDataType);
//            this.acceptChild(visitor, xmlSchemaSpec);
            this.acceptChild(visitor, subView);
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, subQuery);
            this.acceptChild(visitor, subQueryRestriction);
        }
    }


    @Override
    public SQLCreateViewStatement clone() {
        SQLCreateViewStatement x = new SQLCreateViewStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCreateViewStatement x) {
        super.cloneTo(x);
        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {

//        if (target == null) {
//            if (source == subqueryRestriction) {
//                setSubqueryRestriction(null);
//                return true;
//            }
//        }


        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }

//        if (source == subqueryRestriction
//                && target instanceof ISQLSubqueryRestrictionClause) {
//            setSubqueryRestriction((ISQLSubqueryRestrictionClause) target);
//            return true;
//        }


        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.VIEW_CREATE;
    }


    public boolean isOrReplace() {
        return orReplace;
    }

    public void setOrReplace(boolean orReplace) {
        this.orReplace = orReplace;
    }

    public SQLForceType getForce() {
        return force;
    }

    public void setForce(SQLForceType force) {
        this.force = force;
    }
//
//    public SQLTableScope getScope() {
//        return scope;
//    }
//
//    public void setScope(SQLTableScope scope) {
//        this.scope = scope;
//    }

    public boolean isRecursive() {
        return recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public SQLEditionAbleType getEditionAble() {
        return editionAble;
    }

    public void setEditionAble(SQLEditionAbleType editionAble) {
        this.editionAble = editionAble;
    }

    public SQLAlgorithmOptionExpr getAlgorithmSetOptionExpr() {
        return algorithmSetOptionExpr;
    }

    public SQLCreateViewStatement setAlgorithmSetOptionExpr(SQLAlgorithmOptionExpr algorithmSetOptionExpr) {
        setChildParent(algorithmSetOptionExpr);
        this.algorithmSetOptionExpr = algorithmSetOptionExpr;
        return this;
    }

    public SQLDefinerOptionExpr getDefinerSetOptionExpr() {
        return definerSetOptionExpr;
    }

    public SQLCreateViewStatement setDefinerSetOptionExpr(SQLDefinerOptionExpr definerSetOptionExpr) {
        setChildParent(algorithmSetOptionExpr);
        this.definerSetOptionExpr = definerSetOptionExpr;
        return this;
    }

    public SQLSecurityType getSecurityType() {
        return securityType;
    }

    public SQLCreateViewStatement setSecurityType(SQLSecurityType securityType) {
        this.securityType = securityType;
        return this;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public void setName(String name) {
        if (name == null) {
            return;
        }
        setName(SQLUtils.ofName(name));
    }

    public void setName(String owner, String viewName) {
        if (viewName == null) {
            return;
        }
        if (owner == null) {
            setName(SQLUtils.ofName(viewName));
        } else {
            SQLPropertyExpr name = new SQLPropertyExpr(owner, viewName);
            setName(name);
        }

    }

    public String getOwner() {
        if (name instanceof SQLPropertyExpr
                && ((SQLPropertyExpr) name).getOwner() instanceof ISQLName) {
            return ((ISQLName) ((SQLPropertyExpr) name).getOwner()).getSimpleName();
        }
        return null;
    }

    public void setOwner(String ownerName) {
        if (ownerName == null) {
            return;
        }
        ISQLName name = getName();
        if (name == null) {
            return;
        }
        SQLPropertyExpr newName;
        if (name instanceof SQLPropertyExpr) {
            newName = (SQLPropertyExpr) name;
            newName.setOwner(ownerName);
        } else {
            newName = new SQLPropertyExpr(ownerName, (ISQLIdentifier) name);
        }
        setName(newName);
    }

    public String getViewName() {
        if (name != null) {
            return name.getSimpleName();
        }
        return null;
    }


    public SQLSharingClause getSharingClause() {
        return sharingClause;
    }

    public void setSharingClause(SQLSharingClause sharingClause) {
        setChildParent(sharingClause);
        this.sharingClause = sharingClause;
    }

    public ISQLDataType getOfDataType() {
        return ofDataType;
    }

    public void setOfDataType(ISQLDataType ofDataType) {
        setChildParent(ofDataType);
        this.ofDataType = ofDataType;
    }

    public SQLXmlSchemaSpec getXmlSchemaSpec() {
        return xmlSchemaSpec;
    }

    public void setXmlSchemaSpec(SQLXmlSchemaSpec xmlSchemaSpec) {
        setChildParent(xmlSchemaSpec);
        this.xmlSchemaSpec = xmlSchemaSpec;
    }

    public ISQLExpr getSubView() {
        return subView;
    }

    public void setSubView(ISQLExpr subView) {
        setChildParent(subView);
        this.subView = subView;
    }

    public List<ISQLTableElement> getColumns() {
        return columns;
    }

    public void addColumn(ISQLTableElement column) {
        if (column == null) {
            return;
        }
        setChildParent(column);
        this.columns.add(column);
    }

//    public SQLCollationExpr getCollationExpr() {
//        return collationExpr;
//    }
//
//    public void setCollationExpr(SQLCollationExpr collationExpr) {
//        setChildParent(collationExpr);
//        this.collationExpr = collationExpr;
//    }

    public SQLBequeathType getBequeath() {
        return bequeath;
    }

    public void setBequeath(SQLBequeathType bequeath) {
        this.bequeath = bequeath;
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

    public SQLContainerType getContainer() {
        return container;
    }

    public void setContainer(SQLContainerType container) {
        this.container = container;
    }


    public enum SQLForceType implements ISQLASTEnum {
        FORCE("FORCE", "FORCE"),
        NO_FORCE("no force", "NO FORCE"),
        ;

        public final String lower;
        public final String upper;

        SQLForceType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }
    }



    /**
     * BEQUEATH { CURRENT_USER | DEFINER}
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-VIEW.html#GUID-61D2D2B4-DACC-4C7C-89EB-7E50D9594D30
     */
    public enum SQLBequeathType implements ISQLASTEnum {
        BEQUEATH_CURRENT_USER("BEQUEATH CURRENT_USER"),
        BEQUEATH_DEFINER("BEQUEATH DEFINER");

        public final String upper;

        SQLBequeathType(String upper) {
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return null;
        }

        @Override
        public String upper() {
            return upper;
        }
    }

    /**
     * CONTAINER_MAP | CONTAINERS_DEFAULT
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-VIEW.html#GUID-61D2D2B4-DACC-4C7C-89EB-7E50D9594D30
     */
    public enum SQLContainerType implements ISQLASTEnum {
        CONTAINER_MAP("CONTAINER_MAP"),
        CONTAINERS_DEFAULT("CONTAINERS_DEFAULT");

        public final String upper;

        SQLContainerType(String upper) {
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return upper;
        }

        @Override
        public String upper() {
            return upper;
        }
    }


}
