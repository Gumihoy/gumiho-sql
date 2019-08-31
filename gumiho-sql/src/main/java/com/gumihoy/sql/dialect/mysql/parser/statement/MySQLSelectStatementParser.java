package com.gumihoy.sql.dialect.mysql.parser.statement;

import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.statement.SQLSelectStatementParser;

/**
 * @author kent on 2019-06-17.
 */
public class MySQLSelectStatementParser extends SQLSelectStatementParser {

    public MySQLSelectStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


}
