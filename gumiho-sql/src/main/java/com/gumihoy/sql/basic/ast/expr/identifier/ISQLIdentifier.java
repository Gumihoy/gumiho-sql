package com.gumihoy.sql.basic.ast.expr.identifier;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#identifier
 *
 * @author kent on 2019-06-14.
 */
public interface ISQLIdentifier extends ISQLName {

    String getName();

    @Override
    ISQLIdentifier clone();
}
