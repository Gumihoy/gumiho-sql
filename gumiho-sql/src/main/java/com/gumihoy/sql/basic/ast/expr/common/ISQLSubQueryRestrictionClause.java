package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * [ WITH [ <levels clause> ] CHECK OPTION ]
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#view%20definition
 * <p>
 * WITH { READ ONLY | CHECK OPTION } [ CONSTRAINT constraint ]
 * subquery_restriction_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-VIEW.html#GUID-61D2D2B4-DACC-4C7C-89EB-7E50D9594D30
 *
 * @author kent on 2018/7/13.
 */
public interface ISQLSubQueryRestrictionClause extends ISQLExpr {

    ISQLName getConstraint();

    void setConstraint(ISQLName constraint);

    abstract class AbstractSQLSubQueryRestrictionClause extends AbstractSQLExpr implements ISQLSubQueryRestrictionClause {
        protected ISQLName constraint;

        @Override
        public AbstractSQLSubQueryRestrictionClause clone() {
            throw new UnsupportedOperationException();
        }

        public void cloneTo(AbstractSQLSubQueryRestrictionClause x) {
            super.cloneTo(x);
        }

        public ISQLName getConstraint() {
            return constraint;
        }

        public void setConstraint(ISQLName constraint) {
            setChildParent(constraint);
            this.constraint = constraint;
        }
    }


    /**
     * WITH [ <levels clause> ] CHECK OPTION [ CONSTRAINT constraint ]
     */
    class SQLWithCheckOption extends AbstractSQLSubQueryRestrictionClause {

        protected SQLWithCheckOption.SQLLevels levels;

        public SQLWithCheckOption() {
        }

        public SQLWithCheckOption(SQLLevels levels) {
            this.levels = levels;
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, constraint);
            }
        }

        @Override
        public SQLWithCheckOption clone() {
            SQLWithCheckOption x = new SQLWithCheckOption();
            this.cloneTo(x);
            return x;
        }


        public SQLLevels getLevels() {
            return levels;
        }

        public void setLevels(SQLLevels levels) {
            this.levels = levels;
        }

    }


    /**
     * WITH READ ONLY [ CONSTRAINT constraint ]
     */
    class SQLWithReadOnly extends AbstractSQLSubQueryRestrictionClause{

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, constraint);
            }
        }

        @Override
        public SQLWithReadOnly clone() {
            SQLWithReadOnly x = new SQLWithReadOnly();
            this.cloneTo(x);
            return x;
        }


    }

    enum SQLLevels implements ISQLASTEnum {

        CASCADED("cascaded", "CASCADED"),
        LOCAL("local", "LOCAL");

        public final String lower;
        public final String upper;

        SQLLevels(String lower, String upper) {
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
