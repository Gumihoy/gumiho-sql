package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeAddAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeDropAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * [ inheritance_clauses ] { subprogram_spec | constructor_spec | map_order_function_spec }... [, restrict_references_pragma ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/element-specification.html#GUID-20D95D8A-5C17-4C89-9AAB-1852CDB57CE2
 *
 * @author kent on 2018/4/26.
 */
public class SQLElementSpec extends AbstractSQLExpr {

    protected final List<SQLInheritanceType> inheritances = new ArrayList<>();

    protected final List<ISQLExpr> items = new ArrayList<>();

    protected ISQLPragma pragma;

    @Override
    public void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, items);
//            this.acceptChild(visitor, restrictReferencesPragma);
//        }
    }

    public List<SQLInheritanceType> getInheritances() {
        return inheritances;
    }

    public void addInheritance(SQLInheritanceType inheritance) {
        if (inheritance == null) {
            return;
        }
        this.inheritances.add(inheritance);
    }

    public List<ISQLExpr> getItems() {
        return items;
    }

    public void addItem(ISQLExpr item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        items.add(item);
    }

    public ISQLPragma getPragma() {
        return pragma;
    }

    public void setPragma(ISQLPragma pragma) {
        setChildParent(parent);
        this.pragma = pragma;
    }

    /**
     * EXTERNAL { VARIABLE NAME 'java_static_field_name' | NAME 'java_method_sig'}
     * <p>
     * sqlj_object_type_sig
     * https://docs.oracle.com/database/121/LNPLS/create_type.htm#LNPLS01375
     */
    public interface ISQLExternalNameClause extends ISQLExpr {
        @Override
        ISQLExternalNameClause clone();
    }

    /**
     * EXTERNAL { VARIABLE NAME 'java_static_field_name' | NAME 'java_method_sig'}
     * sqlj_object_type_sig
     * https://docs.oracle.com/database/121/LNPLS/create_type.htm#LNPLS01375
     */
    public static abstract class AbstractSQLExternalNameClause extends AbstractSQLExpr implements ISQLExternalNameClause {
        protected ISQLExpr name;

        @Override
        public AbstractSQLExternalNameClause clone() {
            throw new UnsupportedOperationException(getClass().getName());
        }

        public void cloneTo(AbstractSQLExternalNameClause x) {
            super.cloneTo(x);
            ISQLExpr nameClone = this.name.clone();
            x.setName(nameClone);
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
     * EXTERNAL NAME 'java_method_sig' { VARIABLE NAME 'java_static_field_name'
     */
    public static class SQLExternalNameClause extends AbstractSQLExternalNameClause {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, name);
//            }
        }

        @Override
        public SQLExternalNameClause clone() {
            SQLExternalNameClause x = new SQLExternalNameClause();
            this.cloneTo(x);
            return x;
        }

    }


    /**
     * EXTERNAL VARIABLE NAME 'java_static_field_name'
     */
    public static class SQLExternalVariableNameClause extends AbstractSQLExternalNameClause {
        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, name);
//            }
        }

        @Override
        public SQLExternalVariableNameClause clone() {
            SQLExternalVariableNameClause x = new SQLExternalVariableNameClause();
            this.cloneTo(x);
            return x;
        }

    }

    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/element-specification.html#GUID-20D95D8A-5C17-4C89-9AAB-1852CDB57CE2
     */
    public enum SQLInheritanceType implements ISQLASTEnum {

        OVERRIDING("overriding", "OVERRIDING"),
        FINAL("final", "FINAL"),
        INSTANTIABLE("instantiable", "INSTANTIABLE"),
        NOT_OVERRIDING("not overriding", "NOT OVERRIDING"),
        NOT_FINAL("not final", "NOT FINAL"),
        NOT_INSTANTIABLE("not instantiable", "NOT INSTANTIABLE"),
        ;;

        public final String lower;
        public final String upper;


        SQLInheritanceType(String lower, String upper) {
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


    public static class SQLSubProgramSpec extends AbstractSQLExpr implements SQLAlterTypeAddAction.ISQLAlterTypeAddActionExpr, SQLAlterTypeDropAction.ISQLAlterTypeDropActionExpr {
        protected SQLSubProgramSpecType type;
        protected ISQLExpr spec;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLSubProgramSpec clone() {
            return null;
        }

        public SQLSubProgramSpecType getType() {
            return type;
        }

        public void setType(SQLSubProgramSpecType type) {
            this.type = type;
        }

        public ISQLExpr getSpec() {
            return spec;
        }

        public void setSpec(ISQLExpr spec) {
            setChildParent(spec);
            this.spec = spec;
        }
    }

    public enum SQLSubProgramSpecType implements ISQLASTEnum {
        MEMBER("member", "MEMBER"),
        STATIC("static", "STATIC");

        public final String lower;
        public final String upper;


        SQLSubProgramSpecType(String lower, String upper) {
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


    public static class SQLProcedureSpec extends AbstractSQLExpr {
        protected ISQLName name;
        protected final List<SQLParameterDeclaration> parameters = new ArrayList<>();
        protected SQLASType as = SQLASType.AS;
        private ISQLCallSpec callSpec;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            setChildParent(name);
            this.name = name;
        }

        public List<SQLParameterDeclaration> getParameters() {
            return parameters;
        }

        public void addParameter(SQLParameterDeclaration parameter) {
            if (parameter == null) {
                return;
            }
            setChildParent(parameter);
            this.parameters.add(parameter);
        }

        public void addAllParameter(List<SQLParameterDeclaration> parameters) {
            if (parameters == null
                    || parameters.size() == 0) {
                return;
            }
            for (SQLParameterDeclaration parameter : parameters) {
                setChildParent(parameter);
            }
            this.parameters.addAll(parameters);
        }

        public SQLASType getAs() {
            return as;
        }

        public void setAs(SQLASType as) {
            this.as = as;
        }

        public ISQLCallSpec getCallSpec() {
            return callSpec;
        }

        public void setCallSpec(ISQLCallSpec callSpec) {
            setChildParent(callSpec);
            this.callSpec = callSpec;
        }
    }

    public static class SQLFunctionSpec extends AbstractSQLExpr {
        protected ISQLName name;
        protected final List<SQLParameterDeclaration> parameters = new ArrayList<>();
        protected ISQLDataType returnDataType;
        protected SQLASType as = SQLASType.AS;
        private ISQLCallSpec callSpec;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            setChildParent(name);
            this.name = name;
        }

        public List<SQLParameterDeclaration> getParameters() {
            return parameters;
        }

        public void addParameter(SQLParameterDeclaration parameter) {
            if (parameter == null) {
                return;
            }
            setChildParent(parameter);
            this.parameters.add(parameter);
        }

        public void addAllParameter(List<SQLParameterDeclaration> parameters) {
            if (parameters == null
                    || parameters.size() == 0) {
                return;
            }
            for (SQLParameterDeclaration parameter : parameters) {
                setChildParent(parameter);
            }
            this.parameters.addAll(parameters);
        }

        public ISQLDataType getReturnDataType() {
            return returnDataType;
        }

        public void setReturnDataType(ISQLDataType returnDataType) {
            setChildParent(returnDataType);
            this.returnDataType = returnDataType;
        }

        public SQLASType getAs() {
            return as;
        }

        public void setAs(SQLASType as) {
            this.as = as;
        }

        public ISQLCallSpec getCallSpec() {
            return callSpec;
        }

        public void setCallSpec(ISQLCallSpec callSpec) {
            setChildParent(callSpec);
            this.callSpec = callSpec;
        }
    }

    public static class SQLConstructorSpec extends AbstractSQLExpr {
        protected boolean final_;
        protected boolean instantiable;
        protected ISQLDataType dataType;
        protected ISQLDataType selfInOutDataType;
        protected final List<SQLParameterDeclaration> parameters = new ArrayList<>();
        protected SQLASType as;
        protected ISQLCallSpec callSpec;


        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        public boolean isFinal_() {
            return final_;
        }

        public void setFinal_(boolean final_) {
            this.final_ = final_;
        }

        public boolean isInstantiable() {
            return instantiable;
        }

        public void setInstantiable(boolean instantiable) {
            this.instantiable = instantiable;
        }

        public ISQLDataType getDataType() {
            return dataType;
        }

        public void setDataType(ISQLDataType dataType) {
            this.dataType = dataType;
        }

        public ISQLDataType getSelfInOutDataType() {
            return selfInOutDataType;
        }

        public void setSelfInOutDataType(ISQLDataType selfInOutDataType) {
            this.selfInOutDataType = selfInOutDataType;
        }

        public List<SQLParameterDeclaration> getParameters() {
            return parameters;
        }

        public void addParameter(SQLParameterDeclaration parameter) {
            if (parameter == null) {
                return;
            }
            setChildParent(parameter);
            this.parameters.add(parameter);
        }

        public void addAllParameter(List<SQLParameterDeclaration> parameters) {
            if (parameters == null
                    || parameters.size() == 0) {
                return;
            }
            for (SQLParameterDeclaration parameter : parameters) {
                setChildParent(parameter);
            }
            this.parameters.addAll(parameters);
        }

        public SQLASType getAs() {
            return as;
        }

        public void setAs(SQLASType as) {
            this.as = as;
        }

        public ISQLCallSpec getCallSpec() {
            return callSpec;
        }

        public void setCallSpec(ISQLCallSpec callSpec) {
            this.callSpec = callSpec;
        }


    }


    public static class SQLMapOrderFunctionSpec extends AbstractSQLExpr implements SQLAlterTypeAddAction.ISQLAlterTypeAddActionExpr, SQLAlterTypeDropAction.ISQLAlterTypeDropActionExpr {

        protected SQLMapOrderFunctionSpecType type;
        protected SQLFunctionSpec functionSpec;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLMapOrderFunctionSpec clone() {
            throw new UnsupportedOperationException();
        }

        public SQLMapOrderFunctionSpecType getType() {
            return type;
        }

        public void setType(SQLMapOrderFunctionSpecType type) {
            this.type = type;
        }

        public SQLFunctionSpec getFunctionSpec() {
            return functionSpec;
        }

        public void setFunctionSpec(SQLFunctionSpec functionSpec) {
            setChildParent(functionSpec);
            this.functionSpec = functionSpec;
        }
    }


    public static enum SQLMapOrderFunctionSpecType implements ISQLASTEnum {

        MAP("map", "MAP"),
        ORDER("order", "ORDER");

        public final String lower;
        public final String upper;


        SQLMapOrderFunctionSpecType(String lower, String upper) {
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
