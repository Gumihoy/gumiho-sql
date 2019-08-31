package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * FETCH [[NEXT] FROM] cursor_name INTO var_name [, var_name] ...
 * <p>
 * https://dev.mysql.com/doc/refman/8.0/en/fetch.html
 * <p>
 * FETCH [ direction [ FROM | IN ] ] cursor_name
 * https://www.postgresql.org/docs/devel/static/sql-fetch.html
 * <p>
 * FETCH { cursor | cursor_variable | :host_cursor_variable } { [bulk collect] into expr <, expr>...  [ LIMIT limitExpr ] } ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/FETCH-statement.html#GUID-75BC6E63-841A-4103-9B96-8AC97F5C28BB
 *
 * @author kent on 2018/6/13.
 */
public class SQLFetchStatement extends AbstractSQLStatement {

    protected ISQLExpr direction;

    protected SQLFromType fromType;

    protected ISQLExpr name;

    protected boolean bulkCollect;

    protected final List<ISQLExpr> intoItems = new ArrayList<>();

    protected ISQLExpr limitExpr;


    public SQLFetchStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, direction);
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, intoItems);
            this.acceptChild(visitor, limitExpr);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == direction) {
            setDirection(target);
            return true;
        }

        if (source == name) {
            setName(target);
            return true;
        }

        boolean replace = replaceInList(intoItems, source, target, this);
        if (replace) {
            return true;
        }

        if (source == limitExpr) {
            setLimitExpr(target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.FETCH;
    }


    public ISQLExpr getDirection() {
        return direction;
    }

    public void setDirection(ISQLExpr direction) {
        setChildParent(direction);
        this.direction = direction;
    }

    public SQLFromType getFromType() {
        return fromType;
    }

    public void setFromType(SQLFromType fromType) {
        this.fromType = fromType;
    }

    public ISQLExpr getName() {
        return name;
    }

    public String getCursorName() {
        if (name instanceof ISQLName) {
            return ((ISQLName) name).getFullName();
        }
        return null;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }

    public boolean isBulkCollect() {
        return bulkCollect;
    }

    public void setBulkCollect(boolean bulkCollect) {
        this.bulkCollect = bulkCollect;
    }

    public List<ISQLExpr> getIntoItems() {
        return intoItems;
    }

    public void addIntoItem(ISQLExpr intoItem) {
        if (intoItem == null) {
            return;
        }
        setChildParent(intoItem);
        this.intoItems.add(intoItem);
    }


    public ISQLExpr getLimitExpr() {
        return limitExpr;
    }

    public void setLimitExpr(ISQLExpr limitExpr) {
        setChildParent(limitExpr);
        this.limitExpr = limitExpr;
    }


    public interface SQLFetchDirection extends ISQLExpr {

    }

    /**
     * ABSOLUTE count
     */
    public static class SQLFetchAbsoluteDirection extends AbstractSQLExpr {
        protected ISQLExpr count;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }
    }


    /**
     * RELATIVE count
     */
    public static class SQLFetchRelativeDirection extends AbstractSQLExpr {
        protected ISQLExpr count;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }
    }

    /**
     * BACKWARD count
     */
    public static class SQLFetchBackwardDirection extends AbstractSQLExpr {
        protected ISQLExpr count;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, count);
//            }
        }
    }


    public enum SQLFromType implements ISQLASTEnum {
//        FROM(SQLReserved.FROM),
//        NEXT_FROM(SQLReserved.NEXT_FROM),
//        IN(SQLReserved.IN),
        ;
        public final String lower;
        public final String upper;


        SQLFromType(String lower, String upper) {
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
