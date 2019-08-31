package com.gumihoy.sql.dialect.gpdb.parser;

import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLStatementParser;
import com.gumihoy.sql.config.SQLParseConfig;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-13.
 */
public class GPDBStatementParser extends SQLStatementParser {

    public GPDBStatementParser(String sql, SQLParseConfig config) {
        super(new GPDBExprParser(sql, config), config);
    }

    public GPDBStatementParser(SQLLexer lexer) {
        super(new GPDBExprParser(lexer));
    }

    {
        dbType = DBType.GPDB;
    }


}
