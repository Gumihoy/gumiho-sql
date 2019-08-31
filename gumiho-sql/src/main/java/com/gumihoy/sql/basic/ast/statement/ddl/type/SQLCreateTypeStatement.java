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
package com.gumihoy.sql.basic.ast.statement.ddl.type;


import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.enums.SQLEditionAbleType;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLCollateOptionExpr;
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
 * CREATE TYPE name [ <subtype clause> ] [AS dataType]
 * <p>
 * [options]
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#user-defined%20type%20definition
 * <p>
 * <p>
 * CREATE [OR REPLACE] [ EDITIONABLE | NONEDITIONABLE ] TYPE
 * [ schema. ] type_name [ FORCE ] [ OID 'object_identifier' ]
 * [ sharing_clause ] [ default_collation_clause ] { [ invoker_rights_clause ] |  [ accessible_by_clause ] }...
 * { object_base_type_def | object_subtype_def } ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-TYPE-statement.html#GUID-389D603D-FBD0-452A-8414-240BBBC57034
 * <p>
 * https://www.postgresql.org/docs/10/static/sql-createtype.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreateTypeStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean orReplace = false;

    protected SQLEditionAbleType editionAbleType;

    protected ISQLName name;

    protected boolean force;

    protected ISQLExpr oidLiteral;

    protected SQLSharingClause sharingClause;

    protected SQLCollateOptionExpr collationExpr;

    protected final List<ISQLExpr> properties = new ArrayList<>();

//    protected SQLObjectSubDataType objectSubDataType;

    protected SQLASType as;

    protected ISQLDataType asDataType;

    protected SQLExternalNameClause externalNameClause;

    protected final List<ISQLExpr> attributeDefinitions = new ArrayList<>();

    protected final List<ISQLExpr> options = new ArrayList<>();


    public SQLCreateTypeStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, oidLiteral);
            this.acceptChild(visitor, sharingClause);
            this.acceptChild(visitor, collationExpr);
            this.acceptChild(visitor, properties);
//            this.acceptChild(visitor, objectSubDataType);
            this.acceptChild(visitor, asDataType);
            this.acceptChild(visitor, attributeDefinitions);
            this.acceptChild(visitor, options);
        }
    }

    @Override
    public SQLCreateTypeStatement clone() {
        SQLCreateTypeStatement x = new SQLCreateTypeStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCreateTypeStatement x) {
        super.cloneTo(x);

        x.orReplace = this.orReplace;
//        x.editionAbleType = this.editionAbleType;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            this.setName((ISQLName) target);
            return true;
        }

        if (source == oidLiteral) {
            this.setOidLiteral(target);
            return true;
        }

        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.TYPE_CREATE;
    }


    public boolean isOrReplace() {
        return orReplace;
    }

    public void setOrReplace(boolean orReplace) {
        this.orReplace = orReplace;
    }

//    public SQLEditionAbleType getEditionAbleType() {
//        return editionAbleType;
//    }
//
//    public void setEditionAbleType(SQLEditionAbleType editionAbleType) {
//        this.editionAbleType = editionAbleType;
//    }

    public ISQLName getName() {
        return name;
    }

    public String getTypeName() {
        return name.getSimpleName();
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public ISQLExpr getOidLiteral() {
        return oidLiteral;
    }

    public void setOidLiteral(ISQLExpr oidLiteral) {
        setChildParent(oidLiteral);
        this.oidLiteral = oidLiteral;
    }

    public SQLSharingClause getSharingClause() {
        return sharingClause;
    }

    public void setSharingClause(SQLSharingClause sharingClause) {
        setChildParent(sharingClause);
        this.sharingClause = sharingClause;
    }

    public SQLCollateOptionExpr getCollationExpr() {
        return collationExpr;
    }

    public void setCollationExpr(SQLCollateOptionExpr collationExpr) {
        setChildParent(collationExpr);
        this.collationExpr = collationExpr;
    }

    public List<ISQLExpr> getProperties() {
        return properties;
    }

    public void addProperty(ISQLExpr property) {
        if (property == null) {
            return;
        }
        setChildParent(property);
        this.properties.add(property);
    }

//    public SQLObjectSubDataType getObjectSubDataType() {
//        return objectSubDataType;
//    }
//
//    public void setObjectSubDataType(SQLObjectSubDataType objectSubDataType) {
//        this.objectSubDataType = objectSubDataType;
//    }

    public SQLASType getAs() {
        return as;
    }

    public void setAs(SQLASType as) {
        this.as = as;
    }

    public ISQLDataType getAsDataType() {
        return asDataType;
    }

    public void setAsDataType(ISQLDataType asDataType) {
        this.asDataType = asDataType;
    }

    public SQLExternalNameClause getExternalNameClause() {
        return externalNameClause;
    }

    public void setExternalNameClause(SQLExternalNameClause externalNameClause) {
        setChildParent(externalNameClause);
        this.externalNameClause = externalNameClause;
    }

    public List<ISQLExpr> getAttributeDefinitions() {
        return attributeDefinitions;
    }

    public void addAttributeDefinition(ISQLExpr attributeDefinition) {
        if (attributeDefinition == null) {
            return;
        }
        setChildParent(attributeDefinition);
        this.attributeDefinitions.add(attributeDefinition);
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


    /**
     * EXTERNAL NAME java_ext_name LANGUAGE JAVA USING (SQLData | CustomDatum | OraData)
     *
     * sqlj_object_type
     * https://docs.oracle.com/cd/B28359_01/appdev.111/b28370/create_type.htm#LNPLS01375
     */
    public static class SQLExternalNameClause extends AbstractSQLExpr {
        protected ISQLExpr name;
        protected SQLLanguageJavaUsingType usingType;
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, name);
//            }
        }

        @Override
        public SQLExternalNameClause clone() {
            SQLExternalNameClause x = new SQLExternalNameClause();
            return x;
        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            setChildParent(name);
            this.name = name;
        }

        public SQLLanguageJavaUsingType getUsingType() {
            return usingType;
        }

        public void setUsingType(SQLLanguageJavaUsingType usingType) {
            this.usingType = usingType;
        }
    }


    public enum SQLLanguageJavaUsingType implements ISQLASTEnum {
        SQLData("SQLData"),
        CustomDatum("CustomDatum"),
        OraData("OraData"),;

        public final String upper;

        SQLLanguageJavaUsingType(String upper) {
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
}
