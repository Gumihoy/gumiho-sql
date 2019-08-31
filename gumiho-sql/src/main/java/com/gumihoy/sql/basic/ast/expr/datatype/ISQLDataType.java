package com.gumihoy.sql.basic.ast.expr.datatype;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;

import java.util.List;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#data%20type
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/datatype-attribute.html#GUID-B4A364AB-7CC2-4B3F-AF52-09A752029C8E
 *
 * @author kent on 2019-06-14.
 */
public interface ISQLDataType extends ISQLExpr {

    String getSimpleName();

    String getFullName();


    long simpleNameHash();

    long simpleNameLowerHash();


    ISQLName getName();

    boolean isParen();

    void setParen(boolean paren);

    List<ISQLExpr> getArguments();

    void addArgument(ISQLExpr argument);

    @Override
    ISQLDataType clone();
}
