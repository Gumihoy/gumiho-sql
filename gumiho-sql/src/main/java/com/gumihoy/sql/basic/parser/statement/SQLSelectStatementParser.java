package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.statement.dml.SQLSelectStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#select%20statement:%20single%20row
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#dynamic%20single%20row%20select%20statement
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#dynamic%20select%20statement
 *
 * @author kent on 2019-06-17.
 */
public class SQLSelectStatementParser extends AbstractSQLStatementParser {

    public SQLSelectStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }

    public SQLSelectStatement parser() {
        ISQLSelectQuery query = exprParser.parseSelectQuery();
        return new SQLSelectStatement(query, this.dbType);
    }


}
