package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * [ FINAL ] [ INSTANTIABLE ] CONSTRUCTOR FUNCTION datatype
 * [ [ SELF IN OUT datatype, ]
 * parameter datatype [, parameter datatype ]...
 * ]
 * RETURN SELF AS RESULT
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/element-specification.html#GUID-20D95D8A-5C17-4C89-9AAB-1852CDB57CE2
 *
 * @author kent on 2018/6/17.
 */
public class SQLConstructorDeclaration extends SQLConstructorHeading {

    @Override
    public void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, dataType);
//            this.acceptChild(visitor, selfInOutDataType);
//            this.acceptChild(visitor, parameters);
//        }
    }

    @Override
    public SQLConstructorDeclaration clone() {
        SQLConstructorDeclaration x = new SQLConstructorDeclaration();
        this.cloneTo(x);
        return x;
    }
}
