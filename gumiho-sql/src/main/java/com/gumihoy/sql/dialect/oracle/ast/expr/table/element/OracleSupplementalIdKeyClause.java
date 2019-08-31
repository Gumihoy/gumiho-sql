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
package com.gumihoy.sql.dialect.oracle.ast.expr.table.element;

import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * SUPPLEMENTAL LOG DATA ( { ALL | PRIMARY KEY | UNIQUE | FOREIGN KEY } [, { ALL | PRIMARY KEY | UNIQUE | FOREIGN KEY } ]... ) COLUMNS
 * <p>
 * supplemental_id_key_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/3/28.
 */
public class OracleSupplementalIdKeyClause extends AbstractOracleExpr implements IOracleSupplementLoggingProp {

//    protected final List<IdKeyItem> items = new ArrayList<>();

    @Override
    public void accept0(IOracleASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public OracleSupplementalIdKeyClause clone() {
        OracleSupplementalIdKeyClause x = new OracleSupplementalIdKeyClause();
//        for (IdKeyItem item : items) {
//            x.addItem(item);
//        }

        return x;
    }


//    public List<IdKeyItem> getItems() {
//        return items;
//    }
//
//    public void addItem(IdKeyItem item) {
//        if (item == null) {
//            return;
//        }
//        this.items.add(item);
//    }

//    public enum IdKeyItem implements ISQLEnum {
//        ALL(SQLReserved.ALL),
//        PRIMARY_KEY(SQLReserved.PRIMARY_KEY),
//        UNIQUE(SQLReserved.UNIQUE),
//        FOREIGN_KEY(SQLReserved.FOREIGN_KEY);
//
//        public final SQLReserved name;
//
//        IdKeyItem(SQLReserved name) {
//            this.name = name;
//        }
//
//        public static IdKeyItem of(String name) {
//            long lowerHashCode64 = FNVHash.fnv1a_64_lower(name);
//            return IdKeyItemHolder.MAP.get(lowerHashCode64);
//        }
//
//        @Override
//        public String toString() {
//            return name.upper;
//        }
//
//        @Override
//        public SQLReserved getName() {
//            return name;
//        }
//
//    }
}
