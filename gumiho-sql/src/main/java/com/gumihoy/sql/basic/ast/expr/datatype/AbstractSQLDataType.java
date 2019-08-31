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
package com.gumihoy.sql.basic.ast.expr.datatype;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSQLDataType extends AbstractSQLExpr implements ISQLDataType {

    protected ISQLName name;

    protected String simpleName;
    protected String fullName;
    protected boolean paren = false;
    protected final List<ISQLExpr> arguments = new ArrayList<ISQLExpr>();


    public AbstractSQLDataType() {
    }

    public AbstractSQLDataType(String name) {
        setName(name);
    }

    public AbstractSQLDataType(ISQLExpr... arguments) {
        for (ISQLExpr argument : arguments) {
            addArgument(argument);
        }
    }

    public AbstractSQLDataType(String name, ISQLExpr... arguments) {
        setName(name);
        for (ISQLExpr argument : arguments) {
            addArgument(argument);
        }
    }

    public AbstractSQLDataType(ISQLName name) {
        setName(name);
    }

    @Override
    public AbstractSQLDataType clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLDataType x) {
        super.cloneTo(x);

        ISQLName nameClone = name.clone();
        x.setName(nameClone);

        x.setParen(this.paren);

        for (ISQLExpr argument : this.arguments) {
            ISQLExpr argumentClone = argument.clone();
            x.addArgument(argumentClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }
        boolean replace = replaceInList(arguments, source, target, this);
        if (replace) {
            return true;
        }
        return false;
    }


    @Override
    public String getSimpleName() {
        if (simpleName == null) {
            simpleName = name.getFullName();
        }
        return simpleName;
    }

    @Override
    public long simpleNameHash() {
        return name.hash();
    }

    @Override
    public long simpleNameLowerHash() {
        return name.lowerHash();
    }

    @Override
    public String getFullName() {
        return null;
    }

    @Override
    public ISQLName getName() {
        return name;
    }

    public void setName(String name) {
        setName(SQLUtils.ofName(name));
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.simpleName = null;
        this.name = name;
    }


    @Override
    public boolean isParen() {
        return paren;
    }

    @Override
    public void setParen(boolean paren) {
        this.paren = paren;
    }

    @Override
    public List<ISQLExpr> getArguments() {
        return arguments;
    }

    @Override
    public void addArgument(ISQLExpr argument) {
        if (argument == null) {
            return;
        }
        if (!this.paren) {
            setParen(true);
        }
        argument.setParent(this);
        this.arguments.add(argument);
    }

}
