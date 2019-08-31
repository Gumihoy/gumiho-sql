package com.gumihoy.sql.basic.ast.expr.trigger;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * @author kent on 2018/6/15.
 */
public class SQLTriggerDDLEvent extends AbstractSQLExpr implements SQLTriggerEvent.ISQLTriggerDDLEvent {

    protected SQLTriggerDDLEventType type;

    public SQLTriggerDDLEvent(SQLTriggerDDLEventType type) {
        this.type = type;
    }

    public static SQLTriggerDDLEvent of(SQLTriggerDDLEventType type) {
        return new SQLTriggerDDLEvent(type);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLTriggerDDLEvent clone() {
        SQLTriggerDDLEvent x = new SQLTriggerDDLEvent(this.type);
        return null;
    }

    @Override
    public String getEvent() {
        return null;//type.getEvent();
    }

    public SQLTriggerDDLEventType getType() {
        return type;
    }

    public void setType(SQLTriggerDDLEventType type) {
        this.type = type;
    }

    public enum SQLTriggerDDLEventType implements ISQLTriggerEventType {
        ALTER("ALTER", "ALTER"),
        ANALYZE("ANALYZE", "ANALYZE"),
        ASSOCIATE_STATISTICS("ASSOCIATE_STATISTICS", "ASSOCIATE_STATISTICS"),
        AUDIT("AUDIT", "AUDIT"),
        COMMENT("COMMENT", "COMMENT"),
        CREATE("CREATE", "CREATE"),
        DISASSOCIATE_STATISTICS("DISASSOCIATE_STATISTICS", "DISASSOCIATE_STATISTICS"),
        DROP("DROP", "DROP"),
        GRANT("GRANT", "GRANT"),
        NOAUDIT("NOAUDIT", "NOAUDIT"),
        RENAME("RENAME", "RENAME"),
        REVOKE("REVOKE", "REVOKE"),
        TRUNCATE("TRUNCATE", "TRUNCATE"),
        DDL("DDL","DDL"),;

        public final String lower;
        public final String upper;


        SQLTriggerDDLEventType(String lower, String upper) {
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
