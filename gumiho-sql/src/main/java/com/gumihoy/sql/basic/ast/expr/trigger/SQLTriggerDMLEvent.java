package com.gumihoy.sql.basic.ast.expr.trigger;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * event [of column,column...]
 *
 * @author kent on 2018/6/15.
 */
public class SQLTriggerDMLEvent extends AbstractSQLExpr implements SQLTriggerEvent.ISQLTriggerDMLEvent, Comparable<SQLTriggerDMLEvent> {

    protected SQLTriggerDMLEventType type;

    protected final List<ISQLExpr> ofColumns = new ArrayList<>();

    public SQLTriggerDMLEvent(SQLTriggerDMLEventType type) {
        this.type = type;
    }



    public static SQLTriggerDMLEvent of(SQLTriggerDMLEventType type) {
        return new SQLTriggerDMLEvent(type);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, ofColumns);
        }
    }

    @Override
    public SQLTriggerDMLEvent clone() {
        SQLTriggerDMLEvent x = new SQLTriggerDMLEvent(this.type);
        this.cloneTo(x);
        return null;

    }

    public void cloneTo(SQLTriggerDMLEvent x) {
        super.cloneTo(x);

        for (ISQLExpr ofColumn : ofColumns) {
            ISQLExpr ofColumnClone = ofColumn.clone();
            x.addOfColumn(ofColumnClone);
        }
    }

    @Override
    public int compareTo(SQLTriggerDMLEvent o) {
        return 0;
    }

    @Override
    public String getEvent() {
        return null;//type.getEvent();
    }

    public SQLTriggerDMLEventType getType() {
        return type;
    }

    public void setType(SQLTriggerDMLEventType type) {
        this.type = type;
    }

    public List<ISQLExpr> getOfColumns() {
        return ofColumns;
    }

    public void addOfColumn(ISQLExpr ofColumn) {
        if (ofColumn == null) {
            return;
        }
        setChildParent(ofColumn);
        this.ofColumns.add(ofColumn);
    }


    public enum SQLTriggerDMLEventType implements ISQLTriggerEventType {

        DELETE("delete", "DELETE"),
        INSERT("insert", "INSERT"),
        UPDATE("update", "UPDATE"),;

        public final String lower;
        public final String upper;


        SQLTriggerDMLEventType(String lower, String upper) {
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
