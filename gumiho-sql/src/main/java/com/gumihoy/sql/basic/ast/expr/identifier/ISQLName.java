package com.gumihoy.sql.basic.ast.expr.identifier;

import com.gumihoy.sql.basic.ast.ISQLReplaceable;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#identifier
 *
 * @author kent on 2019-06-14.
 */
public interface ISQLName extends ISQLExpr, ISQLReplaceable {

    @Override
    ISQLName clone();

    String getSimpleName();

    String getFullName();

    long hash();

    long lowerHash();

    long fullNameHash();

    long fullNameLowerHash();

}