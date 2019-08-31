package com.gumihoy.sql.dialect.gpdb.parser.statement;

import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.statement.SQLSelectStatementParser;

/**
 * @author kent on 2019-06-17.
 */
public class GPDBSelectStatementParser extends SQLSelectStatementParser {

    public GPDBSelectStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


}
