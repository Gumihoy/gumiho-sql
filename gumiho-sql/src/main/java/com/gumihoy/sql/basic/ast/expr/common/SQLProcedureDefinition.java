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
package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/procedure-declaration-and-definition.html#GUID-9A48D7CE-3720-46A4-B5CA-C2250CA86AF2
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/procedure-declaration-and-definition.html#GUID-9A48D7CE-3720-46A4-B5CA-C2250CA86AF2
 *
 * @author kent onCondition 2018/3/22.
 */
public class SQLProcedureDefinition extends SQLProcedureHeading {

    protected SQLASType as = SQLASType.AS;

    private final List<ISQLExpr> declareSections = new ArrayList<>();

    private ISQLExpr asExpr;

    public SQLProcedureDefinition() {
    }

    public SQLProcedureDefinition(ISQLName name, List<SQLParameterDeclaration> parameters, List<ISQLExpr> properties, SQLASType as, List<ISQLExpr> declareSections, ISQLExpr asExpr) {
        super(name, parameters, properties);
        this.as = as;
        if (declareSections != null) {
            for (ISQLExpr declareSection : declareSections) {
                addDeclareSection(declareSection);
            }
        }
        setAsExpr(asExpr);
    }

    @Override
    public void accept0(IOracleASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, parameters);
            this.acceptChild(visitor, properties);
            this.acceptChild(visitor, declareSections);
            this.acceptChild(visitor, asExpr);
        }
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
