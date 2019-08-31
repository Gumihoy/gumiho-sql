package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.statement.SQLSynonymStatementParser;

/**
 * @author kent on 2019-06-25.
 */
public class OracleSynonymStatementParser extends SQLSynonymStatementParser {


    public OracleSynonymStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


}
