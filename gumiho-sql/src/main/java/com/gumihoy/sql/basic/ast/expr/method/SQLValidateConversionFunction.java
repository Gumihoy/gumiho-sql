package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * VALIDATE_CONVERSION (expr AS type_name [, fmt [, 'nlsparam' ] ])
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/VALIDATE_CONVERSION.html#GUID-DC485EEB-CB6D-42EF-97AA-4487884CB2CD
 *
 * @author kent on 2018/5/21.
 */
public class SQLValidateConversionFunction extends AbstractSQLFunction {

    public SQLValidateConversionFunction() {
//        super(SQLReserved.VALIDATE_CONVERSION.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

}
