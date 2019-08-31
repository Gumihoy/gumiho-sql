package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * PROCEDURE procedure [ ( parameter_declaration [, parameter_declaration ]... ) ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/scalar-variable-declaration.html#GUID-03124315-0E1E-4154-8EBE-12034CA6AD55
 *
 * @author kent on 2018/5/31.
 */
public class SQLProcedureHeading extends AbstractOracleExpr {

    protected ISQLName name;
    protected final List<SQLParameterDeclaration> parameters = new ArrayList<>();
    protected final List<ISQLExpr> properties = new ArrayList<>();

    public SQLProcedureHeading() {
    }

    public SQLProcedureHeading(ISQLName name, List<SQLParameterDeclaration> parameters, List<ISQLExpr> properties) {
        setName(name);
        if (parameters != null) {
            for (SQLParameterDeclaration parameter : parameters) {
                addParameter(parameter);
            }
        }
        if (properties != null) {
            for (ISQLExpr property : properties) {
                addProperty(property);
            }
        }
    }

    @Override
    public void accept0(IOracleASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, parameters);
//        }
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
        }
        return false;
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

    public List<ISQLExpr> getProperties() {
        return properties;
    }

    public void addProperty(ISQLExpr property) {
        if (property == null) {
            return;
        }
        setChildParent(property);
        this.properties.add(property);
    }
}
