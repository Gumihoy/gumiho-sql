package com.gumihoy.sql.dialect.mariadb.parser.statement;

import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.statement.SQLSelectStatementParser;
import com.gumihoy.sql.dialect.mariadb.parser.MariaDBExprParser;

/**
 * @author kent on 2019-06-17.
 */
public class MariaDBSelectStatementParser extends SQLSelectStatementParser {

    public MariaDBSelectStatementParser(MariaDBExprParser exprParser) {
        super(exprParser);
    }


}
