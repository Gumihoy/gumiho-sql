package com.gumihoy.sql.basic.ast.expr.trigger;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * @author kent on 2018/6/15.
 */
public class SQLTriggerDatabaseEvent extends AbstractSQLExpr implements SQLTriggerEvent.ISQLTriggerDatabaseEvent {

    protected SQLTriggerDatabaseEventType type;

    public SQLTriggerDatabaseEvent(SQLTriggerDatabaseEventType type) {
        this.type = type;
    }

    public static SQLTriggerDatabaseEvent of(SQLTriggerDatabaseEventType type) {
        return new SQLTriggerDatabaseEvent(type);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLTriggerDatabaseEvent clone() {
        SQLTriggerDatabaseEvent x = new SQLTriggerDatabaseEvent(this.type);
        return null;
    }

    @Override
    public String getEvent() {
        return null;//type.getEvent();
    }

    public SQLTriggerDatabaseEventType getType() {
        return type;
    }

    public void setType(SQLTriggerDatabaseEventType type) {
        this.type = type;
    }


    public enum SQLTriggerDatabaseEventType implements ISQLTriggerEventType {
        STARTUP("STARTUP", "STARTUP"),
        SERVERERROR("SERVERERROR", "SERVERERROR"),
        LOGON("LOGON", "LOGON"),
        SUSPEND("SUSPEND", "SUSPEND"),
        DB_ROLE_CHANGE("DB_ROLE_CHANGE", "DB_ROLE_CHANGE"),
        CLONE("CLONE", "CLONE"),
        SET_CONTAINER("SET_CONTAINER", "SET_CONTAINER"),

        SHUTDOWN("SHUTDOWN", "SHUTDOWN"),
        LOGOFF("LOGOFF", "LOGOFF"),
        UNPLUG("UNPLUG", "UNPLUG"),;

        public final String lower;
        public final String upper;


        SQLTriggerDatabaseEventType(String lower, String upper) {
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


        @Override
        public String getEvent() {
            return null;
        }
    }

}
