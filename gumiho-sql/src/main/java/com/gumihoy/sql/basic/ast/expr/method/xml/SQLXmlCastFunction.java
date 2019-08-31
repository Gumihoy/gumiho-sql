package com.gumihoy.sql.basic.ast.expr.method.xml;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.method.AbstractSQLFunction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * XMLCAST ( value_expression AS datatype )
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/XMLCAST.html#GUID-06563B93-1247-4F0C-B6BE-42DB3B1DB069
 *
 * @author kent on 2018/5/24.
 */
public class SQLXmlCastFunction extends AbstractSQLFunction {

    public SQLXmlCastFunction() {
//        super(SQLReserved.XMLCAST.ofExpr());
    }

    public SQLXmlCastFunction(ISQLExpr argument) {
//        super(SQLReserved.XMLCAST.ofExpr());
        this.addArgument(argument);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

}
