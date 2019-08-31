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
package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * OPEN cursor [ ( cursor_parameter [ [,] actual_cursor_parameter ]... ) ] ;
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#open%20statement
 * <p>
 * <p>
 * OPEN cursor [ ( cursor_parameter [ [,] actual_cursor_parameter ]... ) ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/OPEN-statement.html
 *
 * @author kent onCondition 2018/3/14.
 */
public class SQLOpenStatement extends AbstractSQLStatement {

    protected ISQLExpr name;

    protected final List<ISQLExpr> parameters = new ArrayList<>();


    public SQLOpenStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, parameters);
        }
    }


    @Override
    public SQLOpenStatement clone() {
        SQLOpenStatement x = new SQLOpenStatement(this.dbType);
        this.cloneTo(x);

        ISQLExpr nameClone = this.name.clone();
        x.setName(nameClone);

        for (ISQLExpr parameter : this.parameters) {
            ISQLExpr parameterClone = parameter.clone();
            x.addParameter(parameterClone);
        }
        return x;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            setName(target);
            return true;
        }

        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.OPEN;
    }


    public ISQLExpr getName() {
        return name;
    }

    public String getCursorName() {
        if (name instanceof ISQLName) {
            return ((ISQLName) name).getFullName();
        }
        return null;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }


    public List<ISQLExpr> getParameters() {
        return parameters;
    }

    public void addParameter(ISQLExpr parameter ) {
        if (parameter == null) {
            return;
        }
        setChildParent(parameter);
        this.parameters.add(parameter);
    }
}
