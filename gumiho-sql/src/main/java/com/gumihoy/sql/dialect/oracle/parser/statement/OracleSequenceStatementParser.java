package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.statement.SQLSequenceStatementParser;

/**
 * @author kent on 2019-06-25.
 */
public class OracleSequenceStatementParser extends SQLSequenceStatementParser {

    public OracleSequenceStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


}
