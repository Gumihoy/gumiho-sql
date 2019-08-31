package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * FORALL index IN bounds_clause [ SAVE EXCEPTIONS ] dml_statement;
 * <p>
 * FORALL index IN bounds_clause dml_statement [ SAVE EXCEPTIONS ] ;
 * <p>
 * https://docs.oracle.com/cd/B28359_01/appdev.111/b28370/forall_statement.htm#LNPLS01321
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/FORALL-statement.html#GUID-C45B8241-F9DF-4C93-8577-C840A25963DB
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLForAllStatement extends AbstractSQLStatement {

    protected ISQLName name;

    protected ISQLBoundsClause boundsClause;

    protected boolean beforeSaveExceptions;

    protected ISQLStatement statement;

    protected boolean afterSaveExceptions;

    public SQLForAllStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, boundsClause);
            this.acceptChild(visitor, statement);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.FOR_ALL;
    }


    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLBoundsClause getBoundsClause() {
        return boundsClause;
    }

    public void setBoundsClause(ISQLBoundsClause boundsClause) {
        setChildParent(boundsClause);
        this.boundsClause = boundsClause;
    }


    public boolean isBeforeSaveExceptions() {
        return beforeSaveExceptions;
    }

    public void setBeforeSaveExceptions(boolean beforeSaveExceptions) {
        this.beforeSaveExceptions = beforeSaveExceptions;
    }

    public ISQLStatement getStatement() {
        return statement;
    }

    public void setStatement(ISQLStatement statement) {
        setChildParent(statement);
        this.statement = statement;
    }

    public boolean isAfterSaveExceptions() {
        return afterSaveExceptions;
    }

    public void setAfterSaveExceptions(boolean afterSaveExceptions) {
        this.afterSaveExceptions = afterSaveExceptions;
    }


    public interface ISQLBoundsClause extends ISQLExpr {
        @Override
        ISQLBoundsClause clone();
    }

    /**
     * lower_bound .. upper_bound
     */
    public static class SQLBoundsRangeClause extends AbstractSQLExpr implements ISQLBoundsClause {
        protected ISQLExpr lower;
        protected ISQLExpr upper;

        public SQLBoundsRangeClause(ISQLExpr lower, ISQLExpr upper) {
            setLower(lower);
            setUpper(upper);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, lower);
                this.acceptChild(visitor, upper);
            }
        }

        @Override
        public SQLBoundsRangeClause clone() {
            return null;
        }

        public ISQLExpr getLower() {
            return lower;
        }

        public void setLower(ISQLExpr lower) {
            setChildParent(lower);
            this.lower = lower;
        }

        public ISQLExpr getUpper() {
            return upper;
        }

        public void setUpper(ISQLExpr upper) {
            setChildParent(upper);
            this.upper = upper;
        }
    }


    /**
     * INDICES OF collection [ BETWEEN lower_bound AND upper_bound
     */
    public static class SQLBoundsIndicesOfClause extends AbstractSQLExpr implements ISQLBoundsClause {

        protected ISQLExpr expr;
        protected ISQLExpr between;
        protected ISQLExpr and;

        public SQLBoundsIndicesOfClause(ISQLExpr expr) {
            setExpr(expr);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, expr);
                this.acceptChild(visitor, between);
                this.acceptChild(visitor, and);
            }
        }

        @Override
        public SQLBoundsRangeClause clone() {
            return null;
        }

        public ISQLExpr getExpr() {
            return expr;
        }

        public void setExpr(ISQLExpr expr) {
            this.expr = expr;
        }

        public ISQLExpr getBetween() {
            return between;
        }

        public void setBetween(ISQLExpr between) {
            this.between = between;
        }

        public ISQLExpr getAnd() {
            return and;
        }

        public void setAnd(ISQLExpr and) {
            this.and = and;
        }
    }


    /**
     * VALUES OF index_collection
     */
    public static class SQLBoundsValueOfClause extends AbstractSQLExpr implements ISQLBoundsClause {
        protected ISQLExpr expr;

        public SQLBoundsValueOfClause(ISQLExpr expr) {
            setExpr(expr);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, expr);
            }
        }

        @Override
        public SQLBoundsRangeClause clone() {
            return null;
        }

        public ISQLExpr getExpr() {
            return expr;
        }

        public void setExpr(ISQLExpr expr) {
            setChildParent(expr);
            this.expr = expr;
        }
    }

}
