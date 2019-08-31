package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.IOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * { MAPPING TABLE | NOMAPPING }
 * <p>
 * mapping_table_clauses
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/27.
 */
public interface IOracleMappingTableClause extends IOracleExpr {
    @Override
    IOracleMappingTableClause clone();

    /**
     * { MAPPING TABLE | NOMAPPING }
     * <p>
     * mapping_table_clauses
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/27.
     */
    public class OracleSQLMappingTableClause extends AbstractOracleExpr implements IOracleMappingTableClause {

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public OracleSQLMappingTableClause clone() {
            return new OracleSQLMappingTableClause();
        }
    }


    /**
     * { MAPPING TABLE | NOMAPPING }
     * <p>
     * mapping_table_clauses
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/27.
     */
    public class OracleNoMappingTableClause extends AbstractOracleExpr implements IOracleMappingTableClause {
        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public OracleNoMappingTableClause clone() {
            return new OracleNoMappingTableClause();
        }
    }
    
}
