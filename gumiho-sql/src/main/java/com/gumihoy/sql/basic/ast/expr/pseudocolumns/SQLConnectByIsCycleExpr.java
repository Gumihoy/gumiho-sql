package com.gumihoy.sql.basic.ast.expr.pseudocolumns;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * CONNECT_BY_ISCYCLE
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Hierarchical-Query-Pseudocolumns.html#GUID-2F2FBA6F-2FD1-47D6-A74F-DB4B31E4D400
 *
 * @author kent on 2018/5/16.
 */
public class SQLConnectByIsCycleExpr extends AbstractSQLExpr implements ISQLPseudoColumn {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLConnectByIsCycleExpr clone() {
        return new SQLConnectByIsCycleExpr();
    }
}
