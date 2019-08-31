package com.gumihoy.sql.basic.ast.expr.method.xml;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.method.AbstractSQLFunction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * XMLROOT( value_expr, VERSION { value_expr | NO VALUE } [, STANDALONE { YES | NO | NO VALUE } ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/XMLCAST.html#GUID-06563B93-1247-4F0C-B6BE-42DB3B1DB069
 *
 * @author kent on 2018/5/24.
 */
public class SQLXmlRootFunction extends AbstractSQLFunction {

    public SQLXmlRootFunction() {
//        super(SQLReserved.XMLROOT.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    /**
     * VERSION { value_expr | NO VALUE }
     */
    public static class SQLVersionArgument extends AbstractSQLExpr {

        protected ISQLExpr value;

        public SQLVersionArgument(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }

}
