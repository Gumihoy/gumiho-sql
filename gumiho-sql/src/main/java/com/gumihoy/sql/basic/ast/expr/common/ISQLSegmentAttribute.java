package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * { physical_attributes_clause
 * | { TABLESPACE tablespace | TABLESPACE SET tablespace_set }
 * | logging_clause
 * }...
 * <p>
 * segment_attributes_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/segment_attributes_clause.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2019-07-16.
 */
public interface ISQLSegmentAttribute extends ISQLExpr, ISQLPhysicalProperty, ISQLIndexOrgOverflowClause {
    @Override
    ISQLSegmentAttribute clone();

}
