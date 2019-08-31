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
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * accessible_by_clause
 * <p>
 * https://docs.oracle.com/database/121/LNPLS/create_function.htm#GUID-B71BC5BD-B87C-4054-AAA5-213E856651F2__CIHFDACE
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/ACCESSIBLE-BY-clause.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ACCESSIBLE-BY-clause.html#GUID-9720619C-9862-4123-96E7-3E85F240FF36
 *
 * @author kent onCondition 2018/3/20.
 */
public class SQLAccessibleByClause extends AbstractSQLExpr {

    private final List<SQLAccessor> accessors = new ArrayList<>();

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, accessors);
        }
    }


    public List<SQLAccessor> getAccessors() {
        return accessors;
    }

    public void addAccessor(SQLAccessor accessor) {
        if (accessor == null) {
            return;
        }
        setChildParent(accessor);
        this.accessors.add(accessor);
    }


    public static class SQLAccessor extends AbstractSQLExpr {

        protected SQLUnitKind unitKind;
        protected ISQLName name;

        public SQLAccessor(ISQLName name) {
            this(null, name);
        }

        public SQLAccessor(SQLUnitKind unitKind, ISQLName name) {
            this.unitKind = unitKind;
            setName(name);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
            }
        }

        public SQLUnitKind getUnitKind() {
            return unitKind;
        }

        public void setUnitKind(SQLUnitKind unitKind) {
            this.unitKind = unitKind;
        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            this.name = name;
        }
    }


    public static enum SQLUnitKind implements ISQLASTEnum {

        FUNCTION("function", "FUNCTION"),
        PROCEDURE("procedure", "PROCEDURE"),
        PACKAGE("package", "PACKAGE"),
        TRIGGER("trigger", "TRIGGER"),
        TYPE("type", "TYPE");

        public final String lower;
        public final String upper;


        SQLUnitKind(String lower, String upper) {
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
}
