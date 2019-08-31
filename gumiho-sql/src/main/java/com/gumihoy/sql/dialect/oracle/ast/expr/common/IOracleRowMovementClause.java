package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.IOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * { ENABLE | DISABLE } ROW MOVEMENT
 *
 * row_movement_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 * @author kent on 2018/7/10.
 */
public interface IOracleRowMovementClause extends IOracleExpr {

    @Override
    IOracleRowMovementClause clone();

    public static class OracleEnableRowMovementClause extends AbstractOracleExpr implements IOracleRowMovementClause {
        @Override
        public void accept0(IOracleASTVisitor visitor) {

        }

        @Override
        public OracleEnableRowMovementClause clone() {
            return null;
        }
    }

    public static class OracleDisableRowMovementClause extends AbstractOracleExpr implements IOracleRowMovementClause {
        @Override
        public void accept0(IOracleASTVisitor visitor) {

        }

        @Override
        public OracleEnableRowMovementClause clone() {
            return null;
        }
    }
}
