package com.gumihoy.sql.basic.ast.expr.table;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLUsingIndexClause;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLExceptionsClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * { ENABLE | DISABLE } [ VALIDATE | NOVALIDATE ] { UNIQUE (column [, column ]...) | PRIMARY KEY | CONSTRAINT constraint_name } [ using_index_clause ]  [ exceptions_clause ] [ CASCADE ] [ { KEEP | DROP } INDEX ]
 * <p>
 * enable_disable_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/7/10.
 */
public interface ISQLEnableDisableClause extends ISQLAlterTableAction {
    @Override
    ISQLEnableDisableClause clone();

    SQLValidateType getValidate();

    public void setValidate(SQLValidateType validate);

    public ISQLUsingIndexClause getUsingIndexClause();

    public void setUsingIndexClause(ISQLUsingIndexClause usingIndexClause);

    public SQLExceptionsClause getExceptionsClause();

    public void setExceptionsClause(SQLExceptionsClause exceptionsClause);

    public boolean isCascade();

    public void setCascade(boolean cascade);

    public SQLIndexOperator getIndexOperator();

    public void setIndexOperator(SQLIndexOperator indexOperator);




    public enum SQLValidateType implements ISQLASTEnum {

        VALIDATE("validate", "VALIDATE"),
        NOVALIDATE("novalidate", "NOVALIDATE");

        public final String lower;
        public final String upper;


        SQLValidateType(String lower, String upper) {
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

    public enum SQLIndexOperator implements ISQLASTEnum {

        KEEP_INDEX("keep index", "KEEP INDEX"),
        DROP_INDEX("drop index", "DROP INDEX");

        public final String lower;
        public final String upper;


        SQLIndexOperator(String lower, String upper) {
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

    abstract class AbstractSQLEnableDisableClause extends AbstractSQLExpr implements ISQLEnableDisableClause {
        protected SQLValidateType validate;
        protected ISQLUsingIndexClause usingIndexClause;
        protected SQLExceptionsClause exceptionsClause;
        protected boolean cascade;
        protected SQLIndexOperator indexOperator;

        @Override
        public AbstractSQLEnableDisableClause clone() {
            throw new UnsupportedOperationException(getClass().getName());
        }

        //        @Override
//        public void cloneTo(ISQLObject x) {
//            super.cloneTo(x);
//        }
//
        public SQLValidateType getValidate() {
            return validate;
        }

        public void setValidate(SQLValidateType validate) {
            this.validate = validate;
        }

        public ISQLUsingIndexClause getUsingIndexClause() {
            return usingIndexClause;
        }

        public void setUsingIndexClause(ISQLUsingIndexClause usingIndexClause) {
            setChildParent(usingIndexClause);
            this.usingIndexClause = usingIndexClause;
        }

        public SQLExceptionsClause getExceptionsClause() {
            return exceptionsClause;
        }

        public void setExceptionsClause(SQLExceptionsClause exceptionsClause) {
            this.exceptionsClause = exceptionsClause;
        }

        public boolean isCascade() {
            return cascade;
        }

        public void setCascade(boolean cascade) {
            this.cascade = cascade;
        }

        public SQLIndexOperator getIndexOperator() {
            return indexOperator;
        }

        public void setIndexOperator(SQLIndexOperator indexOperator) {
            this.indexOperator = indexOperator;
        }
    }


    /**
     * ENABLE validateType? UNIQUE LEFT_PAREN expr (COMMA expr)* RIGHT_PAREN
     */
    class SQLEnableUniqueClause extends AbstractSQLEnableDisableClause {
        protected final List<ISQLExpr> columns = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, columns);
//                this.acceptChild(visitor, usingIndexClause);
//                this.acceptChild(visitor, exceptionsClause);
//            }
        }

        @Override
        public AbstractSQLEnableDisableClause clone() {
            return super.clone();
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            boolean replace = replaceInList(columns, source, target, this);
            if (replace) {
                return true;
            }
            return false;
        }

        public List<ISQLExpr> getColumns() {
            return columns;
        }

        public void addColumn(ISQLExpr column) {
            if (column == null) {
                return;
            }
            setChildParent(column);
            this.columns.add(column);
        }
    }


    /**
     * ENABLE validateType? PRIMARY KEY
     */
    class SQLEnablePrimaryKeyClause extends AbstractSQLEnableDisableClause {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, usingIndexClause);
//                this.acceptChild(visitor, exceptionsClause);
//            }
        }

        @Override
        public AbstractSQLEnableDisableClause clone() {
            return super.clone();
        }
    }


    /**
     * ENABLE validateType? CONSTRAINT nameIdentifier [ using_index_clause ]  [ exceptions_clause ] [ CASCADE ] [ { KEEP | DROP } INDEX ]
     */
    class SQLEnableConstraintClause extends AbstractSQLEnableDisableClause {
        protected ISQLExpr name;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, name);
//                this.acceptChild(visitor, usingIndexClause);
//                this.acceptChild(visitor, exceptionsClause);
//            }
        }

        @Override
        public AbstractSQLEnableDisableClause clone() {
            return super.clone();
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {

            if (source == name) {
                setName(target);
                return true;
            }
            return false;
        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            setChildParent(name);
            this.name = name;
        }
    }


    /**
     * DISABLE validateType? UNIQUE LEFT_PAREN expr (COMMA expr)* RIGHT_PAREN
     */
    class SQLDisableUniqueClause extends AbstractSQLEnableDisableClause {
        protected final List<ISQLExpr> columns = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, columns);
//                this.acceptChild(visitor, usingIndexClause);
//                this.acceptChild(visitor, exceptionsClause);
//            }
        }

        @Override
        public AbstractSQLEnableDisableClause clone() {
            return super.clone();
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            boolean replace = replaceInList(columns, source, target, this);
            if (replace) {
                return true;
            }
            return false;
        }

        public List<ISQLExpr> getColumns() {
            return columns;
        }

        public void addColumn(ISQLExpr column) {
            if (column == null) {
                return;
            }
            setChildParent(column);
            this.columns.add(column);
        }
    }


    /**
     * DISABLE validateType? PRIMARY KEY
     */
    class SQLDisablePrimaryKeyClause extends AbstractSQLEnableDisableClause {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, usingIndexClause);
//                this.acceptChild(visitor, exceptionsClause);
//            }
        }

        @Override
        public AbstractSQLEnableDisableClause clone() {
            return super.clone();
        }
    }


    /**
     * DISABLE validateType? CONSTRAINT nameIdentifier
     */
    class SQLDisableConstraintClause extends AbstractSQLEnableDisableClause {
        protected ISQLExpr name;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, name);
//                this.acceptChild(visitor, usingIndexClause);
//                this.acceptChild(visitor, exceptionsClause);
//            }
        }

        @Override
        public AbstractSQLEnableDisableClause clone() {
            return super.clone();
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {

            if (source == name) {
                setName(target);
                return true;
            }
            return false;
        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            setChildParent(name);
            this.name = name;
        }
    }


}
