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

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.util.SQLUtils;

/**
 * [DEFAULT] name [=] value
 *
 * @author kent onCondition 2018/3/27.
 */
public class SQLSetOptionExpr extends AbstractSQLExpr {

    protected boolean default_ = false;

    protected ISQLExpr name;

    protected boolean eq = false;

    protected ISQLExpr value;


    public SQLSetOptionExpr() {
    }

    public SQLSetOptionExpr(ISQLExpr name) {
        setName(name);
    }

    public SQLSetOptionExpr(ISQLExpr name, ISQLExpr value) {
        setName(name);
        setValue(value);
    }

    public SQLSetOptionExpr(ISQLExpr name, boolean eq, ISQLExpr value) {
        this(false, name, eq, value);
    }

    public SQLSetOptionExpr(boolean default_, ISQLExpr name, boolean equalSign, ISQLExpr value) {
        setDefault_(default_);
        setName(name);
        this.eq = eq;
        setValue(value);
    }

    public static SQLSetOptionExpr of(ISQLExpr name, ISQLExpr value) {
        return new SQLSetOptionExpr(name, value);
    }

    public static SQLSetOptionExpr of(ISQLExpr name, boolean equalSign, ISQLExpr value) {
        return new SQLSetOptionExpr(name, equalSign, value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLSetOptionExpr clone() {
        SQLSetOptionExpr x = new SQLSetOptionExpr();
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLSetOptionExpr x) {
        super.cloneTo(x);

        x.default_ = this.default_;
        x.eq = this.eq;

        ISQLExpr nameClone = this.name.clone();
        ISQLExpr valueClone = this.value.clone();
        x.setName(nameClone);
        x.setValue(valueClone);
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            setName(target);
            return true;
        }
        if (source == value) {
            setValue(target);
            return true;
        }
        return false;
    }

    public boolean isDefault_() {
        return default_;
    }

    public void setDefault_(boolean default_) {
        this.default_ = default_;
    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }

    public boolean isEq() {
        return eq;
    }

    public void setEq(boolean eq) {
        this.eq = eq;
    }

    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
        if (value == null) {
            SQLUtils.replaceInParent(this, null);
        }
    }
}
