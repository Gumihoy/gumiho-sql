package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.IOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * FLASHBACK ARCHIVE [flashback_archive] | NO FLASHBACK ARCHIVE
 *
 *
 * flashback_archive_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 * @author kent on 2018/7/10.
 */
public interface IOracleFlashbackArchiveClause extends IOracleExpr {

    @Override
    IOracleFlashbackArchiveClause clone();

    /**
     * FLASHBACK ARCHIVE [flashback_archive] | NO FLASHBACK ARCHIVE
     *
     *
     * flashback_archive_clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     * @author kent on 2018/7/10.
     */
    public class SQLFlashbackArchiveClause extends AbstractOracleExpr implements IOracleFlashbackArchiveClause {

        protected ISQLName name;

        public SQLFlashbackArchiveClause() {
        }

        public SQLFlashbackArchiveClause(ISQLName name) {
            setName(name);
        }

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLFlashbackArchiveClause clone() {
            SQLFlashbackArchiveClause x = new SQLFlashbackArchiveClause();

            if (this.name != null) {
                ISQLName nameClone = this.name.clone();
                x.setName(nameClone);
            }

            return x;
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            if (source == name
                    && target instanceof ISQLName) {
                setName((ISQLName)target);
                return true;
            }
            return false;
        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            setChildParent(name);
            this.name = name;
        }
    }

    /**
     * NO FLASHBACK ARCHIVE
     *
     *
     * flashback_archive_clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     * @author kent on 2018/7/10.
     */
    public class SQLNoFlashbackArchiveClause extends AbstractOracleExpr implements IOracleFlashbackArchiveClause {

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLNoFlashbackArchiveClause clone() {
            SQLNoFlashbackArchiveClause x = new SQLNoFlashbackArchiveClause();
            return x;
        }


    }
}
