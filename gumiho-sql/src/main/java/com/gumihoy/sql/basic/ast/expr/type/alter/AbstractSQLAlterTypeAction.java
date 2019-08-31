package com.gumihoy.sql.basic.ast.expr.type.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLExceptionsClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * @author kent on 2019-07-31.
 */
public abstract class AbstractSQLAlterTypeAction extends AbstractSQLExpr implements ISQLAlterTypeAction {

    protected ISQLDependentHandlingClause dependentHandlingClause;

    @Override
    public AbstractSQLAlterTypeAction clone() {
        throw new UnsupportedOperationException();
    }


    public void cloneTo(AbstractSQLAlterTypeAction x) {
        super.cloneTo(x);

    }

    public ISQLDependentHandlingClause getDependentHandlingClause() {
        return dependentHandlingClause;
    }

    public void setDependentHandlingClause(ISQLDependentHandlingClause dependentHandlingClause) {
        setChildParent(dependentHandlingClause);
        this.dependentHandlingClause = dependentHandlingClause;
    }


    public static interface ISQLDependentHandlingClause extends ISQLExpr {
        @Override
        ISQLDependentHandlingClause clone();
    }

    /**
     * INVALIDATE
     */
    public static class SQLDependentHandlingInvalidateClause extends AbstractSQLExpr implements ISQLDependentHandlingClause {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLDependentHandlingInvalidateClause clone() {
            return new SQLDependentHandlingInvalidateClause();
        }
    }

    /**
     * CASCADE [ { [ NOT ] INCLUDING TABLE DATA| CONVERT TO SUBSTITUTABLE}]
     * [ [FORCE ] exceptions_clause ]
     */
    public static class SQLDependentHandlingCascadeClause extends AbstractSQLExpr implements ISQLDependentHandlingClause {
        protected SQLCascadeOptionType optionType;
        protected boolean force;
        protected SQLExceptionsClause exceptionsClause;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, exceptionsClause);
            }
        }

        @Override
        public SQLDependentHandlingCascadeClause clone() {
            return null;
        }

        public SQLCascadeOptionType getOptionType() {
            return optionType;
        }

        public void setOptionType(SQLCascadeOptionType optionType) {
            this.optionType = optionType;
        }

        public boolean isForce() {
            return force;
        }

        public void setForce(boolean force) {
            this.force = force;
        }

        public SQLExceptionsClause getExceptionsClause() {
            return exceptionsClause;
        }

        public void setExceptionsClause(SQLExceptionsClause exceptionsClause) {
            setChildParent(exceptionsClause);
            this.exceptionsClause = exceptionsClause;
        }
    }

    public enum SQLCascadeOptionType implements ISQLASTEnum {

        INCLUDING_TABLE_DATA("including table data", "INCLUDING TABLE DATA"),
        NOT_INCLUDING_TABLE_DATA("not including table data", "NOT INCLUDING TABLE DATA"),
        CONVERT_TO_SUBSTITUTABLE("convert to substitutable", "CONVERT TO SUBSTITUTABLE"),
        ;

        public final String lower;
        public final String upper;


        SQLCascadeOptionType(String lower, String upper) {
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
