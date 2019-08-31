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
package com.gumihoy.sql.basic.ast.expr.table.element.constraint;

import com.gumihoy.sql.basic.ast.enums.SQLOrderingSpecification;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.List;

/**
 * https://dev.mysql.com/doc/refman/5.6/en/create-table.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
 *
 * @author kent onCondition 2018/3/13.
 */
public interface ISQLConstraint extends ISQLExpr {

    @Override
    ISQLConstraint clone();


    ISQLName getName();

    String getConstraintName();

    void setName(ISQLName name);

    List<ISQLExpr> getOptions();

    void addOption(ISQLExpr option);


    /**
     * col_name [(length)] [ASC | DESC]
     * <p>
     * https://dev.mysql.com/doc/refman/5.6/en/create-table.html
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
     */
    class SQLColumn extends AbstractSQLExpr {

        protected ISQLExpr name;
        protected ISQLExpr length;
        protected SQLOrderingSpecification ordering;

        public SQLColumn(ISQLExpr name) {
            setName(name);
        }

        public static SQLColumn of(ISQLExpr name) {
            return new SQLColumn(name);
        }


        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, length);
            }
        }

        @Override
        public SQLColumn clone() {
            ISQLExpr nameClone = this.name.clone();
            SQLColumn x = new SQLColumn(nameClone);

            ISQLExpr lengthClone = this.length.clone();
            x.setLength(lengthClone);

            x.ordering = this.ordering;
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

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            setChildParent(name);
            this.name = name;
        }

        public ISQLExpr getLength() {
            return length;
        }

        public void setLength(ISQLExpr length) {
            setChildParent(length);
            this.length = length;
        }

        public SQLOrderingSpecification getOrdering() {
            return ordering;
        }

        public void setOrdering(SQLOrderingSpecification ordering) {
            this.ordering = ordering;
        }
    }
}
