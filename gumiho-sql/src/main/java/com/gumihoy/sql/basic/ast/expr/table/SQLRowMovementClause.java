package com.gumihoy.sql.basic.ast.expr.table;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * { ENABLE | DISABLE } ROW MOVEMENT
 *
 * row_movement_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 * @author kent on 2018/7/10.
 */
public class SQLRowMovementClause extends AbstractSQLExpr {

//    protected SQLEnableType enable;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public SQLRowMovementClause clone() {
        SQLRowMovementClause x = new SQLRowMovementClause();
//        x.enable = this.enable;
        return x;
    }

//    public SQLEnableType getEnable() {
//        return enable;
//    }
//
//    public void setEnable(SQLEnableType enable) {
//        this.enable = enable;
//    }
}
