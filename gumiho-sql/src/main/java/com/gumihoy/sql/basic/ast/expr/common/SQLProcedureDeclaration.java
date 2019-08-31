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
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.List;

/**
 * procedure_heading [ procedure_properties ] [ ; ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/procedure-declaration-and-definition.html#GUID-9A48D7CE-3720-46A4-B5CA-C2250CA86AF2
 *
 * @author kent onCondition 2018/3/22.
 */
public class SQLProcedureDeclaration extends SQLProcedureHeading {


    public SQLProcedureDeclaration() {
    }

    public SQLProcedureDeclaration(ISQLName name, List<SQLParameterDeclaration> parameters, List<ISQLExpr> properties) {
        super(name, parameters, properties);

    }

    @Override
    public void accept0(IOracleASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, parameters);
            this.acceptChild(visitor, properties);
        }
    }


}
