package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * CURSOR cursor [ ( cursor_parameter_dec [, cursor_parameter_dec ]... )] [ RETURN rowtype] IS select_statement ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/explicit-cursor-declaration-and-definition.html#GUID-38C5DBA3-9DEC-4AF2-9B5E-7B721D11A77C
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/explicit-cursor-declaration-and-definition.html#GUID-38C5DBA3-9DEC-4AF2-9B5E-7B721D11A77C
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLCursorDefinition extends AbstractSQLExpr {

    protected ISQLName name;

    protected final List<SQLParameterDeclaration> parameters = new ArrayList<>();

    protected ISQLDataType returnDataType;

    protected ISQLStatement selectStatement;

    public SQLCursorDefinition() {
        setAfterSemi(true);
    }

    public SQLCursorDefinition(ISQLName name, List<SQLParameterDeclaration> parameters, ISQLDataType returnDataType, ISQLStatement selectStatement) {
        setName(name);
        if (parameters != null) {
            for (SQLParameterDeclaration parameter : parameters) {
                addParameter(parameter);
            }
        }
        setReturnDataType(returnDataType);
        setSelectStatement(selectStatement);
        setAfterSemi(true);
    }

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, parameters);
            this.acceptChild(visitor, returnDataType);
            this.acceptChild(visitor, selectStatement);
        }
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

    public ISQLDataType getReturnDataType() {
        return returnDataType;
    }

    public void setReturnDataType(ISQLDataType returnDataType) {
        setChildParent(returnDataType);
        this.returnDataType = returnDataType;
    }


    public ISQLStatement getSelectStatement() {
        return selectStatement;
    }

    public void setSelectStatement(ISQLStatement selectStatement) {
        setChildParent(selectStatement);
        this.selectStatement = selectStatement;
    }
}
