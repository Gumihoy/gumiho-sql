package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.common.ISQLLobParameter;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.IOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * DEDUPLICATE/KEEP_DUPLICATES
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/25.
 */
public interface IOracleLobDeduplicateClause extends IOracleExpr, ISQLLobParameter {
    @Override
    IOracleLobDeduplicateClause clone();


    /**
     * DEDUPLICATE
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/25.
     */
    public class OracleLobDeduplicateClause extends AbstractOracleExpr implements IOracleLobDeduplicateClause {

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public OracleLobDeduplicateClause clone() {
            return new OracleLobDeduplicateClause();
        }
    }


    /**
     * KEEP_DUPLICATES
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/25.
     */
    public class OracleLobKeepDuplicatesClause extends AbstractOracleExpr implements IOracleLobDeduplicateClause {

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public OracleLobKeepDuplicatesClause clone() {
            return new OracleLobKeepDuplicatesClause();
        }
    }
}
