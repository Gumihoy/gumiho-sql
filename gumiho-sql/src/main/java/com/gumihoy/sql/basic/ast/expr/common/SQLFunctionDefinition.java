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
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * function_heading [ DETERMINISTIC | PIPELINED | PARALLEL_ENABLE | RESULT_CACHE [ relies_on_clause ] ]...
 * { IS | AS }
 * { [ declare_section ] body | call_spec }
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/function-declaration-and-definition.html#GUID-4E19FB09-46B5-4CE5-8A5B-CD815C29DA1C
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/function-declaration-and-definition.html#GUID-4E19FB09-46B5-4CE5-8A5B-CD815C29DA1C
 *
 * @author kent onCondition 2018/3/22.
 */
public class SQLFunctionDefinition extends SQLFunctionHeading {

    private SQLASType as = SQLASType.AS;

    protected final List<ISQLExpr> declareSections = new ArrayList<>();

    private ISQLExpr asExpr;

    public SQLFunctionDefinition() {
    }

    public SQLFunctionDefinition(ISQLName name, List<SQLParameterDeclaration> parameters, ISQLDataType returnDataType, List<ISQLExpr> properties, SQLASType as, List<ISQLExpr> declareSections, ISQLExpr asExpr) {
        super(name, parameters, returnDataType, properties);
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
            this.acceptChild(visitor, returnDataType);
            this.acceptChild(visitor, properties);
            this.acceptChild(visitor, declareSections);
            this.acceptChild(visitor, asExpr);
        }
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

    public void addAllDeclareSection(List<ISQLExpr> declareSections) {
        if (declareSections == null
                || declareSections.size() == 0) {
            return;
        }
        for (ISQLExpr declareSection : declareSections) {
            setChildParent(declareSection);
        }
        this.declareSections.addAll(declareSections);
    }

    public ISQLExpr getAsExpr() {
        return asExpr;
    }

    public void setAsExpr(ISQLExpr asExpr) {
        setChildParent(asExpr);
        this.asExpr = asExpr;
    }
}
