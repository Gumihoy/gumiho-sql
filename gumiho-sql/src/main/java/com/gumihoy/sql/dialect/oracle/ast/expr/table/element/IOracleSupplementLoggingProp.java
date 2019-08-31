package com.gumihoy.sql.dialect.oracle.ast.expr.table.element;

import com.gumihoy.sql.basic.ast.expr.table.element.ISQLTableElement;
import com.gumihoy.sql.dialect.oracle.ast.expr.IOracleExpr;

/**
 * SUPPLEMENTAL LOG GROUP log_group (column [ NO LOG ] [, column [ NO LOG ] ]...) [ ALWAYS ]
 * <p>
 * SUPPLEMENTAL LOG DATA ( { ALL | PRIMARY KEY | UNIQUE | FOREIGN KEY } [, { ALL | PRIMARY KEY | UNIQUE | FOREIGN KEY } ]... ) COLUMNS
 * <p>
 * supplemental_logging_props
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/7/16.
 */
public interface IOracleSupplementLoggingProp extends ISQLTableElement, IOracleExpr {
    @Override
    IOracleSupplementLoggingProp clone();


}
