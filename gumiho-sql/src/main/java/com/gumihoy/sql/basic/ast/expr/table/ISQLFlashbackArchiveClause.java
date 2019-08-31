package com.gumihoy.sql.basic.ast.expr.table;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * FLASHBACK ARCHIVE [flashback_archive] | NO FLASHBACK ARCHIVE
 *
 *
 * flashback_archive_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 * @author kent on 2018/7/10.
 */
public interface ISQLFlashbackArchiveClause extends ISQLExpr {

    @Override
    ISQLFlashbackArchiveClause clone();
}
