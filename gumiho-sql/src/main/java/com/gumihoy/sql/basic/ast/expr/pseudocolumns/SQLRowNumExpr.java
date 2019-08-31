package com.gumihoy.sql.basic.ast.expr.pseudocolumns;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ROWNUM
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Simple-Expressions.html#GUID-0E033897-60FB-40D7-A5F3-498B0FCC31B0
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ROWNUM-Pseudocolumn.html#GUID-2E40EC12-3FCF-4A4F-B5F2-6BC669021726
 *
 * @author kent on 2018/5/10.
 */
public class SQLRowNumExpr extends AbstractSQLExpr implements ISQLPseudoColumn {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLRowNumExpr clone() {
        return new SQLRowNumExpr();
    }
}
