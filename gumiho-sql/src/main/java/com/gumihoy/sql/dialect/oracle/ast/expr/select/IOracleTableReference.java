package com.gumihoy.sql.dialect.oracle.ast.expr.select;

import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.dialect.oracle.ast.expr.IOracleExpr;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 *
 * @author kent on 2018/5/18.
 */
public interface IOracleTableReference extends IOracleExpr, ISQLTableReference {

}
