package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ref_cursor_type_definition
 * <p>
 * TYPE type IS REF CURSOR [ RETURN { {db_table_or_view | cursor | cursor_variable}%ROWTYPE | record%TYPE | record_type | ref_cursor_type }] ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/cursor-variable-declaration.html#GUID-CE884B31-07F0-46AA-8067-EBAF73821F3D
 *
 * @author kent on 2018/4/25.
 */
public class SQLRefCursorTypeDefinition extends AbstractSQLExpr implements ISQLTypeDefinition {

    protected ISQLName name;

    protected ISQLDataType returnDataType;

    public SQLRefCursorTypeDefinition(ISQLName name) {
        this(name, null);
    }

    public SQLRefCursorTypeDefinition(ISQLName name, ISQLDataType returnDataType) {
        setName(name);
        setReturnDataType(returnDataType);
        setAfterSemi(true);
    }

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, returnDataType);
        }
    }

    @Override
    public SQLRefCursorTypeDefinition clone() {
        return null;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        this.name = name;
    }

    public ISQLDataType getReturnDataType() {
        return returnDataType;
    }

    public void setReturnDataType(ISQLDataType returnDataType) {
        this.returnDataType = returnDataType;
    }
}
