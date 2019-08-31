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
package com.gumihoy.sql.basic.ast.statement.ddl.trigger;


import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.enums.SQLEditionAbleType;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLCollateOptionExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLDefinerOptionExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerOrderingClause;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerReferencingClause;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;
import com.gumihoy.sql.util.SQLUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * create trigger action event (or evnent) referencingOptions?  on nestedTableClauseExpr? onExpr
 * orderingClause? enableType?  (WHEN whenCondition) triggerBody
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#trigger%20definition
 * <p>
 * CREATE [DEFINER = { user | CURRENT_USER }] TRIGGER trigger_name trigger_time trigger_event ON tbl_name FOR EACH ROW [trigger_order] trigger_body
 * trigger_time: { BEFORE | AFTER }
 * trigger_event: { INSERT | UPDATE | DELETE }
 * trigger_order: { FOLLOWS | PRECEDES } other_trigger_name
 * https://dev.mysql.com/doc/refman/8.0/en/create-trigger.html
 * <p>
 * https://www.postgresql.org/docs/10/static/sql-createtrigger.html
 * <p>
 * <p>
 * CREATE orReplace? editionableType? TRIGGER triggerName=nameIdentifier collationExpr?
 * createTriggerActionTime (createTriggerEvent (OR createTriggerEvent)*)  (ON (NESTED TABLE nestedTable=nameIdentifier OF)? createTriggerOnExpr)?
 * (REFERENCING referencingOption+)? forEachRow? triggerEditionClause? triggerOrderingClause? (ENABLE | DISABLE)? (WHEN LEFT_PAREN whenCondition=expr RIGHT_PAREN)? triggerBody
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-TRIGGER-statement.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreateTriggerStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean orReplace;

    protected SQLEditionAbleType editionAbleType;

    protected SQLDefinerOptionExpr definerExpr;

    protected ISQLName name;

    protected SQLSharingClause sharingClause;
    protected SQLCollateOptionExpr collationExpr;

    protected SQLTriggerActionTime actionTime;

    protected final List<SQLTriggerEvent> events = new ArrayList<>();


    protected ISQLExpr nestedTableColumn;

    protected boolean pluggable;
    protected ISQLExpr onExpr;


    protected SQLTriggerReferencingClause referencingClause;

    protected SQLTriggerForEachType forEachType;

    protected SQLTriggerEditionType editionType;

    protected SQLTriggerOrderingClause orderingClause;

//    protected SQLEnableType enableType;

    protected ISQLExpr whenCondition;

    protected ISQLObject triggerBody;

    public SQLCreateTriggerStatement(DBType dbType) {
        super(dbType);
        if (dbType == DBType.MySQL) {
            setForEachType(SQLTriggerForEachType.FOR_EACH_ROW);
        }
    }

    public static SQLCreateTriggerStatement of(DBType dbType) {
        return new SQLCreateTriggerStatement(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, definerExpr);
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, collationExpr);
            this.acceptChild(visitor, events);
            this.acceptChild(visitor, nestedTableColumn);
            this.acceptChild(visitor, onExpr);
            this.acceptChild(visitor, referencingClause);
            this.acceptChild(visitor, orderingClause);
            this.acceptChild(visitor, whenCondition);
            this.acceptChild(visitor, triggerBody);
        }
    }


    @Override
    public SQLCreateTriggerStatement clone() {

        SQLCreateTriggerStatement x = new SQLCreateTriggerStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLCreateTriggerStatement x) {
        super.cloneTo(x);

        x.orReplace = this.orReplace;
        x.editionAbleType = this.editionAbleType;

        if (this.definerExpr != null) {
            SQLDefinerOptionExpr definerExprClone = this.definerExpr.clone();
            x.setDefinerExpr(definerExprClone);
        }

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        if (this.collationExpr != null) {
            SQLCollateOptionExpr collationExprClone = this.collationExpr.clone();
            x.setCollationExpr(collationExprClone);
        }

        x.actionTime = this.actionTime;
        for (SQLTriggerEvent event : x.events) {
            SQLTriggerEvent eventClone = event.clone();
            x.addEvent(eventClone);
        }

        if (this.nestedTableColumn != null) {
            ISQLExpr nestedTableColumnClone = this.nestedTableColumn.clone();
            x.setNestedTableColumn(nestedTableColumnClone);
        }

        ISQLExpr onExprClone = this.onExpr.clone();
        x.setOnExpr(onExprClone);

        if (this.referencingClause != null) {
            SQLTriggerReferencingClause referencingClauseClone = referencingClause.clone();
            x.setReferencingClause(referencingClauseClone);
        }


        x.forEachType = this.forEachType;

        x.editionType = this.editionType;

        if (this.orderingClause != null) {
            SQLTriggerOrderingClause orderingClauseClone = this.orderingClause.clone();
            x.setOrderingClause(orderingClauseClone);
        }

//        x.enableType = this.enableType;

        if (this.whenCondition != null) {
            ISQLExpr whenConditionClone = this.whenCondition.clone();
            x.setWhenCondition(whenConditionClone);
        }

        if (this.triggerBody != null) {
            ISQLObject triggerBodyClone = this.triggerBody.clone();
            x.setTriggerBody(triggerBodyClone);
        }

    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            this.setName((ISQLName) target);
            return true;
        }

        if (source == nestedTableColumn) {
            this.setNestedTableColumn(target);
            return true;
        }

        if (source == onExpr) {
            this.setOnExpr(target);
            return true;
        }

        if (source == whenCondition) {
            this.setWhenCondition(target);
            return true;
        }
        return false;
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.TRIGGER_CREATE;
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
//
    public SQLDefinerOptionExpr getDefinerExpr() {
        return definerExpr;
    }

    public SQLCreateTriggerStatement setDefinerExpr(SQLDefinerOptionExpr definerExpr) {
        setChildParent(definerExpr);
        this.definerExpr = definerExpr;
        return this;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public void setName(String name) {
        setName(SQLUtils.ofName(name));
    }

    public SQLSharingClause getSharingClause() {
        return sharingClause;
    }

    public void setSharingClause(SQLSharingClause sharingClause) {
        setChildParent(sharingClause);
        this.sharingClause = sharingClause;
    }

    public SQLCollateOptionExpr getCollationExpr() {
        return collationExpr;
    }

    public void setCollationExpr(SQLCollateOptionExpr collationExpr) {
        setChildParent(collationExpr);
        this.collationExpr = collationExpr;
    }

    public SQLTriggerActionTime getActionTime() {
        return actionTime;
    }

    public void setActionTime(SQLTriggerActionTime actionTime) {
        this.actionTime = actionTime;
    }

    public List<SQLTriggerEvent> getEvents() {
        return events;
    }

    public void addEvent(SQLTriggerEvent event) {
        if (event == null) {
            return;
        }
        setChildParent(event);
        this.events.add(event);
    }

    public ISQLExpr getNestedTableColumn() {
        return nestedTableColumn;
    }

    public void setNestedTableColumn(ISQLExpr nestedTableColumn) {
        setChildParent(nestedTableColumn);
        this.nestedTableColumn = nestedTableColumn;
    }

    public boolean isPluggable() {
        return pluggable;
    }

    public void setPluggable(boolean pluggable) {
        this.pluggable = pluggable;
    }

    public ISQLExpr getOnExpr() {
        return onExpr;
    }

    public void setOnExpr(ISQLExpr onExpr) {
        setChildParent(onExpr);
        this.onExpr = onExpr;
    }

    public void setOnExpr(String name) {
        setOnExpr(SQLUtils.ofName(name));
    }

    public SQLTriggerReferencingClause getReferencingClause() {
        return referencingClause;
    }

    public void setReferencingClause(SQLTriggerReferencingClause referencingClause) {
        this.referencingClause = referencingClause;
    }

    public SQLTriggerForEachType getForEachType() {
        return forEachType;
    }

    public void setForEachType(SQLTriggerForEachType forEachType) {
        this.forEachType = forEachType;
    }

    public SQLTriggerEditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(SQLTriggerEditionType editionType) {
        this.editionType = editionType;
    }

    public SQLTriggerOrderingClause getOrderingClause() {
        return orderingClause;
    }

    public void setOrderingClause(SQLTriggerOrderingClause orderingClause) {
        setChildParent(orderingClause);
        this.orderingClause = orderingClause;
    }

//    public SQLEnableType getEnableType() {
//        return enableType;
//    }
//
//    public void setEnableType(SQLEnableType enableType) {
//        this.enableType = enableType;
//    }

    public ISQLExpr getWhenCondition() {
        return whenCondition;
    }

    public void setWhenCondition(ISQLExpr whenCondition) {
        setChildParent(whenCondition);
        this.whenCondition = whenCondition;
    }

    public ISQLObject getTriggerBody() {
        return triggerBody;
    }

    public void setTriggerBody(ISQLObject triggerBody) {
        setChildParent(triggerBody);
        this.triggerBody = triggerBody;
    }


    private Set<String> TRIGGER_EVENT = new HashSet<>();

    /**
     * [owner.]SCHEMA
     */
    public static class SQLOnSchemaExpr extends AbstractSQLExpr {

        protected ISQLName owner;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, owner);
//            }
        }

        public ISQLName getOwner() {
            return owner;
        }

        public void setOwner(ISQLName owner) {
            setChildParent(owner);
            this.owner = owner;
        }
    }

    public static class SQLOnDatabaseExpr extends AbstractSQLExpr {

        protected boolean pluggable;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        public boolean isPluggable() {
            return pluggable;
        }

        public void setPluggable(boolean pluggable) {
            this.pluggable = pluggable;
        }
    }


    /**
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#trigger%20action%20time
     */
    public enum SQLTriggerActionTime implements ISQLASTEnum {
        BEFORE("BEFORE"),
        AFTER("AFTER"),
        INSTEAD_OF("INSTEAD OF"),
        FOR("FOR"),
        ;

        public final String upper;

        SQLTriggerActionTime(String upper) {
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return null;
        }

        @Override
        public String upper() {
            return upper;
        }
    }

    /**
     * FOR [ EACH ] { ROW | STATEMENT }
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#triggered%20action
     * https://www.postgresql.org/docs/devel/static/sql-createtrigger.html
     */
    public enum SQLTriggerForEachType implements ISQLASTEnum {

        FOR_ROW("for row", "FOR ROW"),
        FOR_EACH_ROW("for each row", "FOR EACH ROW"),

        FOR_STATEMENT("for statement", "FOR STATEMENT"),
        FOR_EACH_STATEMENT("for each statement", "FOR EACH STATEMENT"),
        ;

        public final String lower;
        public final String upper;

        SQLTriggerForEachType(String lower, String upper) {
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

    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-TRIGGER-statement.html#GUID-AF9E33F1-64D1-4382-A6A4-EC33C36F237B
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/img_text/trigger_edition_clause.html
     */
    public enum SQLTriggerEditionType implements ISQLASTEnum {

        CROSSEDITION("CROSSEDITION"),
        FORWARD_CROSSEDITION("FORWARD CROSSEDITION"),
        REVERSE_CROSSEDITION("REVERSE CROSSEDITION"),
        ;

        public final String upper;

        SQLTriggerEditionType(String upper) {
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }

        @Override
        public String lower() {
            return null;
        }

        @Override
        public String upper() {
            return upper;
        }
    }
}
