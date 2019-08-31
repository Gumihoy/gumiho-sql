package com.gumihoy.sql.basic.ast.expr.datatype.reference;

import com.gumihoy.sql.basic.ast.expr.datatype.AbstractSQLDataType;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * REF CURSOR
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/cursor-variable-declaration.html#GUID-CE884B31-07F0-46AA-8067-EBAF73821F3D
 *
 * @author kent on 2018/4/19.
 */
public class SQLRefCursorDataType extends AbstractSQLDataType implements ISQLRefDataType {


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
