package com.gumihoy.sql.basic.ast.expr.method.xml;

import com.gumihoy.sql.basic.ast.expr.method.AbstractSQLFunction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * XMLPI ( { [ NAME ] identifier | EVALNAME value_expr } [, value_expr ])
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/XMLPI.html#GUID-142604E3-7999-4803-9DF5-28BDC0701571
 *
 * @author kent on 2018/5/24.
 */
public class SQLXmlPiFunction extends AbstractSQLFunction {

//    protected SQLReserved evalName;

    public SQLXmlPiFunction() {
//        super(SQLReserved.XMLPI.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }


//    public SQLReserved getEvalName() {
//        return evalName;
//    }
//
//    public void setEvalName(SQLReserved evalName) {
//        this.evalName = evalName;
//    }
}
