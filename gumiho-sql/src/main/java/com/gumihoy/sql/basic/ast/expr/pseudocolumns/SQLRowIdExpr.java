package com.gumihoy.sql.basic.ast.expr.pseudocolumns;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ROWID
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Simple-Expressions.html#GUID-0E033897-60FB-40D7-A5F3-498B0FCC31B0
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ROWID-Pseudocolumn.html#GUID-F6E0FBD2-983C-495D-9856-5E113A17FAF1
 *
 * @author kent on 2018/5/16.
 */
public class SQLRowIdExpr extends AbstractSQLExpr implements ISQLPseudoColumn {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLRowIdExpr clone() {
        return new SQLRowIdExpr();
    }
}
