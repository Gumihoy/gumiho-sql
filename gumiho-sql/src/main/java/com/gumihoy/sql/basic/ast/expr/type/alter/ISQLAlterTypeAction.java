package com.gumihoy.sql.basic.ast.expr.type.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLParameterDeclaration;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kent on 2018/9/11.
 */
public interface ISQLAlterTypeAction extends ISQLExpr {
    @Override
    ISQLAlterTypeAction clone();


    public static class SQLAlterTypeEditionAbleAction extends AbstractSQLExpr implements ISQLAlterTypeAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterTypeEditionAbleAction clone() {
            return new SQLAlterTypeEditionAbleAction();
        }
    }

    public static class SQLAlterTypeNoneEditionAbleAction extends AbstractSQLExpr implements ISQLAlterTypeAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterTypeNoneEditionAbleAction clone() {
            return new SQLAlterTypeNoneEditionAbleAction();
        }
    }

    public static class SQLAlterTypeResetAction extends AbstractSQLExpr implements ISQLAlterTypeAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterTypeResetAction clone() {
            return new SQLAlterTypeResetAction();
        }
    }

    public static class SQLAlterTypeInstantiableAction extends AbstractSQLExpr implements ISQLAlterTypeAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterTypeResetAction clone() {
            return new SQLAlterTypeResetAction();
        }
    }

    public static class SQLAlterTypeNotInstantiableAction extends AbstractSQLExpr implements ISQLAlterTypeAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterTypeNotInstantiableAction clone() {
            return new SQLAlterTypeNotInstantiableAction();
        }
    }

    public static class SQLAlterTypeFinalAction extends AbstractSQLExpr implements ISQLAlterTypeAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterTypeResetAction clone() {
            return new SQLAlterTypeResetAction();
        }
    }

    public static class SQLAlterTypeNotFinalAction extends AbstractSQLExpr implements ISQLAlterTypeAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterTypeNotFinalAction clone() {
            return new SQLAlterTypeNotFinalAction();
        }
    }


    public static class SQLAttributeClause extends AbstractSQLExpr implements SQLAlterTypeAddAction.ISQLAlterTypeAddActionExpr, SQLAlterTypeDropAction.ISQLAlterTypeDropActionExpr, SQLAlterTypeModifyAction.ISQLAlterTypeModifyActionExpr {
        protected boolean paren = true;
        protected final List<SQLParameterDeclaration> parameters = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, parameters);
            }
        }

        @Override
        public SQLAttributeClause clone() {
            return new SQLAttributeClause();
        }

        public boolean isParen() {
            return paren;
        }

        public void setParen(boolean paren) {
            this.paren = paren;
        }

        public List<SQLParameterDeclaration> getParameters() {
            return parameters;
        }

        public void addParameter(SQLParameterDeclaration parameter) {
            if (parameter == null) {
                return;
            }
            if (!paren && parameters.size() >= 1) {
                paren = true;
            }
            setChildParent(parameter);
            parameters.add(parameter);
        }
    }

    public static class SQLLimitClause extends AbstractSQLExpr implements SQLAlterTypeModifyAction.ISQLAlterTypeModifyActionExpr {

        protected ISQLExpr value;

        public SQLLimitClause(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        @Override
        public SQLLimitClause clone() {
            ISQLExpr valueClone = this.value.clone();
            return new SQLLimitClause(valueClone);
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }

    public static class SQLElementTypeClause extends AbstractSQLExpr implements SQLAlterTypeModifyAction.ISQLAlterTypeModifyActionExpr {
        protected ISQLDataType dataType;

        public SQLElementTypeClause(ISQLDataType dataType) {
            setDataType(dataType);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, dataType);
            }
        }

        @Override
        public SQLElementTypeClause clone() {
            ISQLDataType dataTypeClone = this.dataType.clone();
            return new SQLElementTypeClause(dataTypeClone);
        }

        public ISQLDataType getDataType() {
            return dataType;
        }

        public void setDataType(ISQLDataType dataType) {
            setChildParent(dataType);
            this.dataType = dataType;
        }
    }
}
