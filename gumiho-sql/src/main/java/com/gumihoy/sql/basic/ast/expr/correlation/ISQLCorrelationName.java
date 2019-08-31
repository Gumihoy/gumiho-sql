package com.gumihoy.sql.basic.ast.expr.correlation;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/plsql-triggers.html#GUID-4CF74E99-8859-4AB1-96E7-07898A2ABB9E
 *
 * 9.4 Correlation Names and Pseudorecords
 *
 * @author kent on 2019-08-22.
 */
public interface ISQLCorrelationName extends ISQLExpr {

    @Override
    ISQLCorrelationName clone();
}
