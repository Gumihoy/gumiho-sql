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

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * SUPPLEMENTAL LOG GROUP log_group (column [ NO LOG ] [, column [ NO LOG ] ]...) [ ALWAYS ]
 * <p>
 * supplemental_log_grp_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/3/28.
 */
public class OracleSQLSupplementalLogGrpClause extends AbstractOracleExpr implements IOracleSupplementLoggingProp {

    protected ISQLName name;

    protected final List<GroupItem> items = new ArrayList<>();

    protected boolean always;

    @Override
    public void accept0(IOracleASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, items);
//        }
    }

    @Override
    public OracleSQLSupplementalLogGrpClause clone() {
        OracleSQLSupplementalLogGrpClause x = new OracleSQLSupplementalLogGrpClause();

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        for (GroupItem item : this.items) {
            GroupItem itemClone = item.clone();
            x.addItem(itemClone);
        }

        x.always = this.always;
        return x;
    }


    public static class GroupItem extends AbstractOracleExpr {
        protected ISQLName column;
        protected boolean notLog;

        public GroupItem(ISQLName column) {
            this(column, false);
        }

        public GroupItem(ISQLName column, boolean notLog) {
            setColumn(column);
            this.notLog = notLog;
        }

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, column);
//            }
        }

        @Override
        public GroupItem clone() {
            ISQLName columnClone = this.column.clone();
            GroupItem x = new GroupItem(column, this.notLog);
            return x;
        }

        public ISQLName getColumn() {
            return column;
        }

        public void setColumn(ISQLName column) {
            setChildParent(column);
            this.column = column;
        }

        public boolean isNotLog() {
            return notLog;
        }

        public void setNotLog(boolean notLog) {
            this.notLog = notLog;
        }
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public List<GroupItem> getItems() {
        return items;
    }

    public void addItem(GroupItem item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }

    public boolean isAlways() {
        return always;
    }

    public void setAlways(boolean always) {
        this.always = always;
    }
}
