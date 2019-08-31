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
package com.gumihoy.sql.basic.ast.statement.ddl.typebody;


import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.enums.SQLEditionAbleType;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATE [ OR REPLACE ] [ EDITIONABLE | NONEDITIONABLE ] TYPE BODY
 * <p>
 * [ schema. ] type_name  { IS | AS }
 * { subprog_decl_in_type
 * | map_order_func_declaration
 * }
 * [, { subprog_decl_in_type
 * | map_order_func_declaration
 * }
 * ]...
 * END;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-TYPE-BODY-statement.html#GUID-B468D6FB-75ED-436B-80E4-8460E4551AE0
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreateTypeBodyStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean orReplace = false;

    protected SQLEditionAbleType editionAbleType;

    protected ISQLName name;

    protected SQLASType as;

    protected final List<SQLCreateBodyItem> items = new ArrayList<>();


    public SQLCreateTypeBodyStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, items);
        }
    }

    @Override
    public SQLCreateTypeBodyStatement clone() {
        SQLCreateTypeBodyStatement x = new SQLCreateTypeBodyStatement(this.dbType);
        cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCreateTypeBodyStatement x) {
        super.cloneTo(x);

        x.orReplace = this.orReplace;

    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            this.setName((ISQLName) target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.TYPE_BODY_CREATE;
    }


    public boolean isOrReplace() {
        return orReplace;
    }

    public void setOrReplace(boolean orReplace) {
        this.orReplace = orReplace;
    }

    public SQLEditionAbleType getEditionAbleType() {
        return editionAbleType;
    }

    public void setEditionAbleType(SQLEditionAbleType editionAbleType) {
        this.editionAbleType = editionAbleType;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public SQLASType getAs() {
        return as;
    }

    public void setAs(SQLASType as) {
        this.as = as;
    }

    public List<SQLCreateBodyItem> getItems() {
        return items;
    }

    public void addItem(ISQLExpr item) {
        if (item == null) {
            return;
        }
        if (item instanceof SQLCreateBodyItem) {
            setChildParent(item);
            this.items.add((SQLCreateBodyItem) item);
        } else {
            SQLCreateBodyItem createBodyItem = SQLCreateBodyItem.of(item);
            setChildParent(createBodyItem);
            this.items.add(createBodyItem);
        }

    }

    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-TYPE-BODY-statement.html#GUID-B468D6FB-75ED-436B-80E4-8460E4551AE0
     */
    public static class SQLCreateBodyItem extends AbstractSQLExpr {

        protected ISQLObject action;

        public SQLCreateBodyItem() {
        }

        public SQLCreateBodyItem(ISQLObject action) {
            setAction(action);
        }

        public static SQLCreateBodyItem of(ISQLObject action) {
            return new SQLCreateBodyItem(action);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, action);
//            }
        }

        @Override
        public SQLCreateBodyItem clone() {
            SQLCreateBodyItem x = new SQLCreateBodyItem();
            this.cloneTo(x);

            ISQLObject actionClone = this.action.clone();
            x.setAction(actionClone);
            return x;
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            if (source == this.action) {
                this.setAction(target);
                return true;
            }
            return false;
        }

        public ISQLObject getAction() {
            return action;
        }

        public void setAction(ISQLObject action) {
            setChildParent(action);
            this.action = action;
        }
    }

}
