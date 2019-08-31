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

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * FUNCTION function_name [ ( parameter_declaration [, parameter_declaration ] ) ] RETURN datatype
 * <p>
 * https://docs.oracle.com/database/121/LNPLS/function.htm#GUID-4E19FB09-46B5-4CE5-8A5B-CD815C29DA1C__CJADHAHB
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/function-declaration-and-definition.html#GUID-4E19FB09-46B5-4CE5-8A5B-CD815C29DA1C
 *
 * @author kent on 2018/3/18.
 */
public class SQLFunctionHeading extends AbstractOracleExpr {

    protected ISQLName name;

    protected final List<SQLParameterDeclaration> parameters = new ArrayList<>();

    protected ISQLDataType returnDataType;

    protected final List<ISQLExpr> properties = new ArrayList<>();

    public SQLFunctionHeading() {
    }

    public SQLFunctionHeading(ISQLName name, List<SQLParameterDeclaration> parameters, ISQLDataType returnDataType, List<ISQLExpr> properties) {
        setName(name);
        if (parameters != null) {
            for (SQLParameterDeclaration parameter : parameters) {
                addParameter(parameter);
            }
        }
        setReturnDataType(returnDataType);
        if (properties != null) {
            for (ISQLExpr property : properties) {
                addProperty(property);
            }
        }
    }

    @Override
    public void accept0(IOracleASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, parameters);
//            this.acceptChild(visitor, returnDataType);
//        }
    }

    public void cloneTo(SQLFunctionHeading x) {
        super.cloneTo(x);

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        for (SQLParameterDeclaration parameter : parameters) {
            SQLParameterDeclaration parameterClone = parameter.clone();
            x.addParameter(parameterClone);
        }

        ISQLDataType returnDataTypeClone = this.returnDataType.clone();
        x.setReturnDataType(returnDataTypeClone);
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }

        if (source == returnDataType
                && target instanceof ISQLDataType) {
            setReturnDataType((ISQLDataType) target);
            return true;
        }
        return false;
    }

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
        setChildParent(parameter);
        this.parameters.add(parameter);
    }

    public void addAllParameter(List<SQLParameterDeclaration> parameters) {
        if (parameters == null
                || parameters.size() == 0) {
            return;
        }
        for (SQLParameterDeclaration parameter : parameters) {
            setChildParent(parameter);
        }
        this.parameters.addAll(parameters);
    }

    public ISQLDataType getReturnDataType() {
        return returnDataType;
    }

    public void setReturnDataType(ISQLDataType returnDataType) {
        setChildParent(returnDataType);
        this.returnDataType = returnDataType;
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
}
