package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DUPLICATE | DUPLICATE ALL | NO DUPLICATE
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/25.
 */
public interface ISQLInMemoryDuplicateClause extends ISQLExpr, ISQLInMemoryAttribute {
    @Override
    ISQLInMemoryDuplicateClause clone();

    /**
     * DUPLICATE [all]
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/25.
     */
    public class SQLInMemoryDuplicateClause extends AbstractSQLExpr implements ISQLInMemoryDuplicateClause {

        protected boolean all;

        public SQLInMemoryDuplicateClause() {
        }

        public SQLInMemoryDuplicateClause(boolean all) {
            this.all = all;
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLInMemoryDuplicateClause clone() {
            return new SQLInMemoryDuplicateClause();
        }

        public boolean isAll() {
            return all;
        }

        public void setAll(boolean all) {
            this.all = all;
        }
    }


    /**
     * NO DUPLICATE
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/25.
     */
    public class SQLInMemoryNoDuplicateClause extends AbstractSQLExpr implements ISQLInMemoryDuplicateClause {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLInMemoryNoDuplicateClause clone() {
            return new SQLInMemoryNoDuplicateClause();
        }
    }

}
