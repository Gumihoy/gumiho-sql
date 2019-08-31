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
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.List;

/**
 * function_heading [ DETERMINISTIC | PIPELINED | PARALLEL_ENABLE | RESULT_CACHE ]... ;
 * <p>
 * https://docs.oracle.com/database/121/LNPLS/function.htm#GUID-4E19FB09-46B5-4CE5-8A5B-CD815C29DA1C__CJADHAHB
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/function-declaration-and-definition.html#GUID-4E19FB09-46B5-4CE5-8A5B-CD815C29DA1C
 *
 * @author kent onCondition 2018/3/18.
 */
public class SQLFunctionDeclaration extends SQLFunctionHeading {

//    protected OracleSQLElementSpec.ISQLExternalNameClause externalClause;

    public SQLFunctionDeclaration() {
        setAfterSemi(true);
    }

    public SQLFunctionDeclaration(ISQLName name, List<SQLParameterDeclaration> parameters, ISQLDataType returnDataType, List<ISQLExpr> properties) {
        super(name, parameters, returnDataType, properties);
    }

    @Override
    public void accept0(IOracleASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, parameters);
            this.acceptChild(visitor, returnDataType);
//            this.acceptChild(visitor, externalClause);
            this.acceptChild(visitor, properties);
        }
    }

    @Override
    public SQLFunctionDeclaration clone() {
        SQLFunctionDeclaration x = new SQLFunctionDeclaration();

        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLFunctionDeclaration x) {
        super.cloneTo(x);

        for (ISQLExpr property : this.properties) {
            ISQLExpr propertyClone = property.clone();
            x.addProperty(propertyClone);
        }
    }

//    public OracleSQLElementSpec.ISQLExternalNameClause getExternalClause() {
//        return externalClause;
//    }
//
//    public void setExternalClause(OracleSQLElementSpec.ISQLExternalNameClause externalClause) {
//        setChildParent(externalClause);
//        this.externalClause = externalClause;
//    }


}
