package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.function.alter.ISQLAlterFunctionAction;
import com.gumihoy.sql.basic.ast.expr.package_.alter.ISQLAlterPackageAction;
import com.gumihoy.sql.basic.ast.expr.procedure.alter.ISQLAlterProcedureAction;
import com.gumihoy.sql.basic.ast.expr.trigger.alter.ISQLAlterTriggerAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.ISQLAlterTypeAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * COMPILE [ DEBUG ] [ PACKAGE | SPECIFICATION | BODY ] [ compiler_parameters_clause ... ] [ REUSE SETTINGS ]
 * <p>
 * type_compile_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ALTER-TYPE-statement.html#GUID-A8B449E7-E3A8-48F4-A4C6-5BB87B1841CD
 *
 * @author kent on 2018/6/13.
 */
public class SQLCompileClause extends AbstractSQLExpr implements ISQLAlterFunctionAction, ISQLAlterPackageAction, ISQLAlterProcedureAction, ISQLAlterTriggerAction, ISQLAlterTypeAction {

    protected boolean debug;
    protected SQLCompiler compiler;
    protected final List<SQLParameter> parameters = new ArrayList<>();
    protected boolean reuseSettings;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, parameters);
        }
    }

    @Override
    public SQLCompileClause clone() {
        SQLCompileClause x = new SQLCompileClause();
        return x;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public SQLCompiler getCompiler() {
        return compiler;
    }

    public void setCompiler(SQLCompiler compiler) {
        this.compiler = compiler;
    }

    public List<SQLParameter> getParameters() {
        return parameters;
    }

    public void addParameter(SQLParameter parameter) {
        if (parameter == null) {
            return;
        }
        setChildParent(parameter);
        this.parameters.add(parameter);
    }

    public boolean isReuseSettings() {
        return reuseSettings;
    }

    public void setReuseSettings(boolean reuseSettings) {
        this.reuseSettings = reuseSettings;
    }


    public enum SQLCompiler implements ISQLASTEnum {

        PACKAGE("package", "PACKAGE"),
        SPECIFICATION("specification", "SPECIFICATION"),
        BODY("body", "BODY"),
        ;

        public final String lower;
        public final String upper;


        SQLCompiler(String lower, String upper) {
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


    public static class SQLParameter extends AbstractSQLExpr {
        protected ISQLExpr name;
        protected ISQLExpr value;

        public SQLParameter(ISQLExpr name, ISQLExpr value) {
            setName(name);
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            this.name = name;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            this.value = value;
        }
    }

}
